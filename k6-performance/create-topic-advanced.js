import http from 'k6/http';
import { check, sleep } from 'k6';
import { Trend, Rate } from 'k6/metrics';

// Métricas customizadas
const latency = new Trend('latency');
const successRate = new Rate('success_rate');

// Configuração do teste - CARGA EXTREMA
export const options = {
  // CENÁRIO EXTREMO
  stages: [
    { duration: '15s', target: 100 },     // 100 usuários
    { duration: '30s', target: 500 },     // 500 usuários  
    { duration: '1m', target: 1000 },     // 1000 usuários
    { duration: '45s', target: 2000 },    // 2000 usuários (PICO MÁXIMO)
    { duration: '30s', target: 1500 },    // Reduz para 1500
    { duration: '20s', target: 800 },     // Reduz para 800
    { duration: '15s', target: 300 },     // Reduz para 300
    { duration: '10s', target: 0 },       // Encerra
  ],
  
  thresholds: {
    latency: ['p(95)<1000'],    // 95% abaixo de 1 segundo (mais flexível)
    success_rate: ['rate>0.90'], // taxa de sucesso acima de 90%
    http_req_failed: ['rate<0.10'], // taxa de erro abaixo de 10%
    http_req_duration: ['avg<800'], // latência média abaixo de 800ms
  },
};

// Função principal executada por cada VU
export default function () {
  // Gerar um ID único usando o timestamp e o ID do VU
  const pautaId = `${__VU}-${Date.now()}`;

  // Corpo da requisição
  const payload = JSON.stringify({
    titulo: `Pauta Teste ${pautaId}`,
    descricao: 'Descrição da pauta de teste',
    categoria: 'GERAL',
  });

  // Cabeçalhos da requisição
  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  // Enviar requisição POST para criar pauta
  const res = http.post('http://localhost:8080/pautas', payload, params);

  // Validar resposta
  const checks = check(res, {
    'status é 201': (r) => r.status === 201,
    'tem campo id': (r) => r.json('id') !== undefined,
    'tempo de resposta < 1000ms': (r) => r.timings.duration < 1000,
  });

  // Registrar métricas
  latency.add(res.timings.duration);
  successRate.add(checks);

  // Pausa MÍNIMA (0.1 a 1 segundo) para máximo throughput
  sleep(Math.random() * 0.9 + 0.1);
}