import http from 'k6/http';
import { check, sleep } from 'k6';
import { Trend, Rate } from 'k6/metrics';

// Métricas customizadas
const latency = new Trend('latency');
const successRate = new Rate('success_rate');

// Configuração do teste
export const options = {
  vus: 10, // 10 usuários virtuais
  duration: '30s', // duração de 30 segundos
  thresholds: {
    latency: ['p(95)<800'], // 95% das requisições abaixo de 800ms
    success_rate: ['rate>0.95'], // taxa de sucesso acima de 95%
    http_req_failed: ['rate<0.05'], // taxa de erro abaixo de 5%
    http_req_duration: ['avg<500'], // latência média abaixo de 500ms
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
    'tempo de resposta < 500ms': (r) => r.timings.duration < 500,
  });

  // Registrar métricas
  latency.add(res.timings.duration);
  successRate.add(checks);

  // Pausa de 2 segundos entre cada criação de pauta
  sleep(2);
}