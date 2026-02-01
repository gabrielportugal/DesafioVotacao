import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter, Rate, Trend } from 'k6/metrics';

// ======================
// MÉTRICAS PERSONALIZADAS
// ======================
let ErrorCount = new Counter('errors');
let SuccessRate = new Rate('successful_requests');
let VoteDuration = new Trend('vote_duration');
let CreateTopicDuration = new Trend('create_topic_duration');

// ======================
// CONFIGURAÇÃO DO TESTE
// ======================
export const options = {
  // Simula crescimento gradual
  stages: [
    // FASE 1: Aquecimento
    { duration: '30s', target: 20 },
    
    // FASE 2: Carga normal
    { duration: '1m', target: 100 },
    
    // FASE 3: Pico (cenário de votação em massa)
    { duration: '2m', target: 500 },
    
    // FASE 4: Recuperação
    { duration: '30s', target: 100 },
    { duration: '30s', target: 0 },
  ],
  
  // LIMITES DE PERFORMANCE
  thresholds: {
    // Latência máxima aceitável
    http_req_duration: ['p(95)<1000', 'p(99)<2000'],
    
    // Taxa de erro máxima
    http_req_failed: ['rate<0.01'], // Menos de 1% de erro
    
    // Métricas personalizadas
    'vote_duration': ['p(95)<800'],
    'errors': ['count<100'],
  },
  
  // CONFIGURAÇÕES ADICIONAIS
  discardResponseBodies: false, // Mantém response bodies para debug
  noConnectionReuse: false,     // Reusa conexões (como navegador real)
};

// ======================
// FUNÇÃO PRINCIPAL
// ======================
export default function () {
  const BASE_URL = 'http://localhost:8080';
  const userId = __VU; // Virtual User ID
  const iteration = __ITER; // Número da iteração
  
  // ===========================
  // 1. CRIAR UMA NOVA PAUTA
  // ===========================
  const topicData = {
    name: `Pauta Eleição ${userId}-${iteration}`,
    description: 'Votação para escolha de representante',
    category: 'Geral',
    durationMinutes: 5 // Para teste rápido
  };
  
  const createStart = Date.now();
  
  const createRes = http.post(
    `${BASE_URL}/api/v1/topic`,
    JSON.stringify(topicData),
    {
      headers: {
        'Content-Type': 'application/json',
        'User-Agent': `k6-test-user-${userId}`,
      },
      tags: { endpoint: 'create_topic' },
    }
  );
  
  const createTime = Date.now() - createStart;
  CreateTopicDuration.add(createTime);
  
  check(createRes, {
    '✅ Pauta criada com sucesso (201)': (r) => r.status === 201,
    '✅ Response tem ID': (r) => {
      try {
        const body = JSON.parse(r.body);
        return body.id !== undefined;
      } catch {
        return false;
      }
    },
  }) || ErrorCount.add(1);
  
  SuccessRate.add(createRes.status === 201);
  
  // Se falhou, não continua
  if (createRes.status !== 201) {
    sleep(1);
    return;
  }
  
  // Extrai ID da pauta criada
  let topicId;
  try {
    const body = JSON.parse(createRes.body);
    topicId = body.id || 1;
  } catch {
    topicId = 1;
  }
  
  sleep(0.5); // Pequena pausa
  
  // ===========================
  // 2. VOTAR NA PAUTA
  // ===========================
  // Gera CPF fictício para teste
  const cpf = generateCPF(userId);
  
  const voteData = {
    cpf: cpf,
    vote: getRandomVote(), // SIM ou NÃO aleatório
    topicId: topicId,
    userAgent: `k6-test/${userId}`,
  };
  
  const voteStart = Date.now();
  
  const voteRes = http.post(
    `${BASE_URL}/api/v1/vote`,
    JSON.stringify(voteData),
    {
      headers: {
        'Content-Type': 'application/json',
        'X-Request-ID': `req-${userId}-${iteration}-${Date.now()}`,
      },
      tags: { endpoint: 'vote' },
    }
  );
  
  const voteTime = Date.now() - voteStart;
  VoteDuration.add(voteTime);
  
  check(voteRes, {
    '✅ Voto registrado (200/201)': (r) => r.status === 200 || r.status === 201,
    '✅ Voto processado rapidamente': (r) => r.timings.duration < 1000,
  }) || ErrorCount.add(1);
  
  SuccessRate.add(voteRes.status === 200 || voteRes.status === 201);
  
  // ===========================
  // 3. CONSULTAR RESULTADO (opcional)
  // ===========================
  if (Math.random() < 0.3) { // 30% das vezes
    const resultRes = http.get(
      `${BASE_URL}/api/v1/topic/${topicId}/result`,
      { tags: { endpoint: 'get_result' } }
    );
    
    check(resultRes, {
      '✅ Resultado obtido (200)': (r) => r.status === 200,
    });
  }
  
  // ===========================
  // PAUSA ENTRE USUÁRIOS
  // ===========================
  // Simula tempo de "pensamento" do usuário
  sleep(Math.random() * 2 + 1); // 1-3 segundos
}

// ======================
// FUNÇÕES AUXILIARES
// ======================
function getRandomVote() {
  const votes = ['SIM', 'NÃO', 'YES', 'NO'];
  return votes[Math.floor(Math.random() * votes.length)];
}

function generateCPF(userId) {
  // Gera CPF válido para teste (números fictícios)
  const base = 10000000000 + (userId * 1000) + (__ITER * 1);
  return base.toString().padStart(11, '0');
}