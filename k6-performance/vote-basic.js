import http from 'k6/http';
import { check, sleep } from 'k6';

// Configuração SIMPLES - 3 fases de teste
export const options = {
  stages: [
    { duration: '10s', target: 10 },   // 10 usuários em 10 segundos
    { duration: '20s', target: 50 },   // Aumenta para 50 usuários
    { duration: '10s', target: 0 },    // Volta para 0
  ],
  thresholds: {
    // 95% das requisições devem completar em menos de 2 segundos
    http_req_duration: ['p(95)<2000'],
  },
};

export default function () {
  const BASE_URL = 'http://localhost:8080'; // Sua API rodando
  
  // 1. Testar endpoint de saúde (sempre disponível)
  let healthCheck = http.get(`${BASE_URL}/actuator/health`);
  
  check(healthCheck, {
    'Health check OK': (r) => r.status === 200,
  });
  
  // 2. Listar tópicos (GET)
  let getTopics = http.get(`${BASE_URL}/api/v1/topic`);
  
  check(getTopics, {
    'GET Topics status 200': (r) => r.status === 200,
    'GET Topics tem body': (r) => r.body.length > 0,
  });
  
  // 3. Criar um tópico (POST) - Apenas 30% das execuções
  if (Math.random() < 0.3) {
    const payload = JSON.stringify({
      name: `Pauta Teste ${__VU}`,
      description: 'Teste de performance com k6'
    });
    
    const params = {
      headers: {
        'Content-Type': 'application/json',
      },
    };
    
    let createTopic = http.post(`${BASE_URL}/api/v1/topic`, payload, params);
    
    check(createTopic, {
      'POST Topic status 201': (r) => r.status === 201,
    });
  }
  
  // Pausa entre iterações (simula usuário pensando)
  sleep(1);
}