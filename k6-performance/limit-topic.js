import http from 'k6/http';
import { check, sleep } from 'k6';
import { Trend, Rate, Counter } from 'k6/metrics';
import { randomIntBetween, uuidv4 } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';

// Métricas customizadas
const maxRpsTrend = new Trend('max_rps');
const errorRate = new Rate('error_rate');
const recoveryTimeTrend = new Trend('recovery_time');
const throughputTrend = new Trend('throughput');
const dataIntegrityErrors = new Counter('data_integrity_errors');
const memoryLeakAlerts = new Counter('memory_leak_alerts');
const poolExhaustionAlerts = new Counter('pool_exhaustion_alerts');

// Configuração dos cenários
export const options = {
  scenarios: {
    incremental: {
      executor: 'ramping-arrival-rate',
      startRate: 10,
      timeUnit: '1s',
      preAllocatedVUs: 100,
      maxVUs: 2000,
      stages: [
        // Incrementa 10% por minuto até 1000 req/s
        { target: 10, duration: '1m' },
        { target: 11, duration: '1m' },
        { target: 12, duration: '1m' },
        { target: 13, duration: '1m' },
        { target: 15, duration: '1m' },
        { target: 17, duration: '1m' },
        { target: 19, duration: '1m' },
        { target: 21, duration: '1m' },
        { target: 23, duration: '1m' },
        { target: 25, duration: '1m' },
        { target: 28, duration: '1m' },
        { target: 31, duration: '1m' },
        { target: 34, duration: '1m' },
        { target: 37, duration: '1m' },
        { target: 41, duration: '1m' },
        { target: 45, duration: '1m' },
        { target: 50, duration: '1m' },
        { target: 55, duration: '1m' },
        { target: 61, duration: '1m' },
        { target: 67, duration: '1m' },
        { target: 74, duration: '1m' },
        { target: 82, duration: '1m' },
        { target: 90, duration: '1m' },
        { target: 99, duration: '1m' },
        { target: 109, duration: '1m' },
        { target: 120, duration: '1m' },
        { target: 132, duration: '1m' },
        { target: 145, duration: '1m' },
        { target: 160, duration: '1m' },
        { target: 176, duration: '1m' },
        { target: 194, duration: '1m' },
        { target: 213, duration: '1m' },
        { target: 234, duration: '1m' },
        { target: 257, duration: '1m' },
        { target: 283, duration: '1m' },
        { target: 311, duration: '1m' },
        { target: 342, duration: '1m' },
        { target: 376, duration: '1m' },
        { target: 413, duration: '1m' },
        { target: 454, duration: '1m' },
        { target: 500, duration: '1m' },
        { target: 550, duration: '1m' },
        { target: 605, duration: '1m' },
        { target: 666, duration: '1m' },
        { target: 733, duration: '1m' },
        { target: 807, duration: '1m' },
        { target: 888, duration: '1m' },
        { target: 977, duration: '1m' },
        { target: 1000, duration: '1m' },
      ],
      exec: 'incrementalTest',
    },
    saturation: {
      executor: 'constant-arrival-rate',
      rate: 1000,
      timeUnit: '1s',
      duration: '15m',
      preAllocatedVUs: 1200,
      maxVUs: 2000,
      exec: 'saturationTest',
      startTime: '50m',
    },
    peak: {
      executor: 'constant-arrival-rate',
      rate: 3000,
      timeUnit: '1s',
      duration: '2m',
      preAllocatedVUs: 3500,
      maxVUs: 4000,
      exec: 'peakTest',
      startTime: '65m',
    },
  },
  thresholds: {
    'error_rate': ['rate<0.05'],
    'max_rps': ['p(99)>900'],
    'recovery_time': ['avg<10000'],
    'data_integrity_errors': ['count==0'],
    'memory_leak_alerts': ['count==0'],
    'pool_exhaustion_alerts': ['count==0'],
  },
  summaryExport: [
    { path: 'resultados-limites.json', format: 'json' },
    { path: 'resultados-limites.csv', format: 'csv' },
  ],
};

// Função para gerar payload variando tamanho (1KB a 1MB)
function gerarPayload(tamanhoKB) {
  const base = 'A'.repeat(1024); // 1KB
  let descricao = '';
  for (let i = 0; i < tamanhoKB; i++) descricao += base;
  return {
    titulo: `Pauta Stress ${uuidv4().substring(0, 8)}`,
    descricao,
    categoria: 'GERAL',
  };
}

// Função para validação de integridade dos dados
function validarIntegridade(res) {
  try {
    const json = res.json();
    return typeof json.id === 'string' && json.id.length > 0;
  } catch (e) {
    return false;
  }
}

// Função principal para cada cenário
function executarTeste(payloadSizeKB, tags) {
  const payload = JSON.stringify(gerarPayload(payloadSizeKB));
  const params = {
    headers: {
      'Content-Type': 'application/json',
      'Connection': 'keep-alive',
      'X-Request-ID': uuidv4(),
    },
    timeout: 2, // Timeout curto para detectar lentidão
    tags,
  };
  const res = http.post('http://localhost:8080/pautas', payload, params);

  // Métricas
  throughputTrend.add(1);
  errorRate.add(res.status !== 201);
  maxRpsTrend.add(res.timings.duration);

  // Validação de integridade
  if (!validarIntegridade(res)) dataIntegrityErrors.add(1);

  // Checks
  check(res, {
    'status 201': (r) => r.status === 201,
    'id válido': (r) => r.json('id') && typeof r.json('id') === 'string',
    'latência < 2s': (r) => r.timings.duration < 2000,
  });

  // Simulação de monitoramento externo (exemplo, integrar com Prometheus/Grafana)
  // Aqui, apenas loga para console, mas pode ser expandido
  if (res.status === 503 || res.body.includes('pool exhausted')) poolExhaustionAlerts.add(1);
  if (res.body.includes('memory leak')) memoryLeakAlerts.add(1);

  // Sleep mínimo para paralelismo
  sleep(0.01);
}

// Teste incremental
export function incrementalTest() {
  // Varia payload entre 1KB e 10KB para simular uso real
  const size = randomIntBetween(1, 10);
  executarTeste(size, { fase: 'incremental' });
}

// Teste de saturação
export function saturationTest() {
  // Payload maior para stress (10KB a 100KB)
  const size = randomIntBetween(10, 100);
  executarTeste(size, { fase: 'saturation' });
}

// Teste de pico
export function peakTest() {
  // Payload extremo (100KB a 1024KB)
  const size = randomIntBetween(100, 1024);
  executarTeste(size, { fase: 'peak' });
}

// Comentários:
// - Recomenda-se monitorar métricas de aplicação/infra com Prometheus/Grafana.
// - Após o teste, validar integridade dos dados, logs e métricas do banco.
// - Outputs em JSON/CSV para análise posterior.
// - Alertas automáticos são disparados via métricas customizadas.
// - Recomenda-se revisar bottlenecks e ajustar configuração conforme relatório.

// Para executar:
// k6 run k6-tests/teste-limites-pauta.js

// Para relatórios HTML, use k6-reporter após o teste.