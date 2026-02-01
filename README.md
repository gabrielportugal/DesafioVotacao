# Desafio de Votação

## 📚 Visão Geral

API REST robusta para gerenciamento de pautas, sessões de votação e votos de associados, desenvolvida com foco em arquitetura limpa, clareza de regras de negócio e alta manutenibilidade. O sistema permite abertura de pautas, criação de sessões de votação com duração configurável, registro de votos e consulta de resultados, atendendo requisitos típicos de ambientes cooperativos.

---

## 🚀 Diferenciais do Projeto

- **Arquitetura Clean e DDD Light**: Separação rigorosa entre camadas, facilitando evolução e manutenção.
- **Lazy Update (Atualização Sob Demanda)**: Sessões de votação são fechadas apenas quando acessadas, sem jobs, schedulers ou threads.
- **Configuração Centralizada**: Parâmetros como duração padrão de sessão são facilmente customizáveis.
- **Integração Fake Desacoplada**: Validação de CPF simulada, isolada e facilmente testável.
- **Cobertura de Testes Abrangente**: Testes de UseCases, regras de negócio e integrações.
- **Código Pronto para Escalar**: Estrutura preparada para novas features e versões de API.
- **Baixo Acoplamento e Alta Coesão**: Controllers sem lógica de negócio, regras centralizadas em UseCases.
- **Mappers Dedicados**: Conversão clara entre entidades, DTOs e persistência.

---

## 🏗️ Arquitetura e Padrões

O projeto adota **DDD Light** e **Clean Architecture**, com princípios **SOLID** e separação clara de responsabilidades:

- **Domain**: Entidades e regras de negócio puras, sem dependências técnicas.
- **Application (UseCases)**: Orquestração de operações e persistência, centralizando regras de fluxo.
- **Infrastructure**: Implementação de persistência (JPA, repositórios), integrações externas e configurações.
- **Interfaces**: Controllers REST, DTOs e mappers para adaptação entre camadas.

**Importante:**  
Toda lógica de negócio reside em UseCases e entidades de domínio. Controllers REST são finos, apenas adaptando requisições e respostas.

---

## 🗂️ Estrutura de Pastas

```text
src/main/java/com/sicradi/votacao
├── application
│   └── usecase
│       ├── topic
│       ├── vote
│       └── votingsession
│           └── VotingSessionUseCaseConfig.java
│   └── ValidateCpfUseCase.java
├── domain
│   ├── model
│   └── repository
├── exceptions
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java
│   ├── InvalidCpfException.java
│   ├── NotFoundException.java
│   ├── TechnicalException.java
│   ├── UnableToVoteException.java
│   └── ValidationException.java
├── infrastructure
│   ├── configuration
│   │   ├── ApiBasePath.java
│   │   └── ApiVersionProperties.java
│   ├── external
│   │   └── cpf
│   │       ├── CpfGenerator.java
│   │       ├── CpfValidationClient.java
│   │       ├── CpfValidationResponse.java
│   │       ├── CpfValidationStatus.java
│   │       └── FakeCpfValidationClient.java
│   └── persistence
│       ├── entity
│       ├── repository
│       └── mapper
├── interfaces
│   └── rest
│       ├── dto
│       ├── mapper
│       └── v1
│           ├── CpfValidationController.java
│           ├── TopicController.java
│           ├── VoteController.java
│           └── VotingSessionController.java
└── VotacaoApplication.java
```

---

## 🧠 Regras de Negócio

- **Abertura de Pautas:** Toda pauta inicia com status `OPEN`.
- **Sessões de Votação:** Sempre vinculadas a uma pauta existente.
- **Duração Configurável:** Definida na criação, com fallback para valor padrão centralizado.
- **Expiração de Sessão:** Determinada por `createdAt + duration` (minutos).
- **Validação de Expiração:** Método de domínio `isExpired()` apenas para validação, sem persistência.
- **Fechamento Centralizado:** Status e `closedAt` atualizados exclusivamente pelo caso de uso `CheckAndCloseVotingSessionUseCase`.
- **Lazy Update:** Não há schedulers, threads ou jobs em background; fechamento ocorre sob demanda.
- **Consistência Garantida:** Toda operação de leitura/uso de sessão passa por verificação e fechamento.
- **Remoção de closedBy:** Campo removido por não ser regra de negócio.
- **Uso de Mappers:** Conversão entre entidades, JPA e DTOs feita por mappers dedicados.

---

## 💤 Lazy Update (Atualização Sob Demanda)

### O que é?

A técnica de **Lazy Update** garante que sessões de votação sejam fechadas apenas quando acessadas, eliminando a necessidade de schedulers, jobs ou threads.

### Como funciona?

- **Sem jobs ou schedulers:** Não há processos em background.
- **Fechamento sob demanda:** Sempre que uma sessão é lida ou utilizada, o caso de uso `CheckAndCloseVotingSessionUseCase` valida e fecha a sessão se necessário.
- **Estado sempre consistente:** O sistema garante que o status da sessão está correto no momento do acesso.

### Vantagens

- **Simplicidade operacional:** Menos complexidade e dependências.
- **Menor custo:** Sem consumo de recursos com jobs.
- **Consistência:** Estado sempre atualizado no momento do uso.

---

## ⚙️ Configuração de Duração de Sessão

- A duração da sessão pode ser informada na criação.
- Se não for informada, o sistema utiliza o valor padrão definido em `VotingSessionProperties`.
- Essa configuração é centralizada, customizável e pode ser alterada facilmente via arquivo de propriedades (`application.properties`):

```properties
votingsession.default-duration-minutes=5
```

---

## 🧾 Validação de CPF

- **Validador de CPF** implementado em `utils` e utilizado no `RegisterVoteUseCase`.
- **CPF inválido:** Bloqueia o voto imediatamente, lançando `InvalidCpfException`.
- **CPF válido:** Segue para validação externa (fake), simulando consulta a serviço externo.
- **Reutilização de exceptions:** O sistema utiliza exceções já existentes para padronizar respostas.

---

## 🏆 Integração FAKE de Validação de CPF

- **Rota exclusiva:** `/api/v1/cpf/validation` (POST) para testar o bônus.
- **Sem CPF no request:** O CPF é gerado aleatoriamente pelo sistema.
- **Comportamento aleatório:** Simula cenários reais de validação.
- **Possíveis retornos HTTP:**
  - `404` → CPF inválido
  - `200` → CPF válido e ABLE_TO_VOTE
  - `403` (ou `422`) → CPF válido e UNABLE_TO_VOTE
- **Isolamento:** Integração fake desacoplada, facilmente mockável em testes.

---

## 🧪 Estratégia de Testes

Os testes do projeto abrangem tanto testes unitários quanto testes de integração. Os UseCases e validadores de regras de negócio são validados por testes unitários, garantindo precisão e isolamento das regras centrais. Já os fluxos completos, incluindo controllers e endpoints REST, são cobertos por testes de integração, assegurando que os componentes interagem corretamente e que o sistema funciona de ponta a ponta.

- **Testes de UseCases:** Cobrem todos os fluxos principais e regras de negócio.
- **Testes do RegisterVoteUseCase:** Validam cenários de CPF válido e inválido.
- **Testes do bônus:** Mockam o client fake para garantir previsibilidade.
- **Cobertura de integração:** Controllers e endpoints principais testados.
- **Execução recomendada:**  
  ```bash
  mvn test
  ```

---

## 🔀 Fluxo Principal da Aplicação

1. Cadastro de pauta (`Topic`) com estado inicial `OPEN`.
2. Criação de sessão de votação (`VotingSession`) vinculada à pauta, com duração definida ou padrão.
3. Registro de votos por associados, com validação de CPF e unicidade por pauta.
4. Consulta de resultados e sessões, sempre garantindo consistência via lazy update.

---

## 🌱 Versionamento de API

- **Versionamento por URL:** Configurado centralizadamente.
- **Exemplo:** `/api/v1/topics`
- **Evolução:** Novas versões criadas em `interfaces.rest.v2`, sem hardcode de versão nos controllers.

---

## 🌿 Estratégia de Branches (Git)

- **main:** Produção estável.
- **develop:** Desenvolvimento contínuo.
- **release/x.x:** Estabilização de versões.
- **Git Flow:** Organização e controle de entregas.

---

## ⚙️ Tecnologias Utilizadas

- Java 17
- Spring Boot
- PostgreSQL
- Flyway
- Swagger/OpenAPI

---

## ▶️ Como Executar o Projeto

### Pré-requisitos

- JDK 17
- Maven
- PostgreSQL

### Passos

```bash
git clone https://github.com/gabrielportugal/DesafioVotacao.git
cd DesafioVotacao/desafio
```

### Configuração do banco de dados

Crie os bancos manualmente:

```sql
CREATE DATABASE votingdb;
CREATE DATABASE votingdb_test;
```

> ⚠️ O Spring não cria o banco automaticamente.  
> Os nomes devem coincidir com os arquivos de configuração.

### Executar

```bash
./mvnw spring-boot:run
```

---

### 🐳 Com Docker
```
git clone https://github.com/gabrielportugal/DesafioVotacao.git
cd DesafioVotacao/desafio
docker-compose up --build
```


## 🌐 Base URL

```
http://localhost:8080/api/v1
```

---

## ⚡ Quick Start

```bash
# Suba o banco (PostgreSQL) e configure conforme instruções acima
./mvnw spring-boot:run
# Teste rápido: crie uma pauta
curl -X POST http://localhost:8080/api/v1/topics \
  -H "Content-Type: application/json" \
  -d '{"title": "Assembleia Orçamento 2026", "description": "Aprovação do orçamento anual do condomínio"}'
```

---

## 📖 Endpoints da API

### Módulos:
- [Pautas (Topics)](#pautas-topics)
- [Sessões (Sessions)](#sessoes-sessions)
- [Votações (Votes)](#votacoes-votes)

---

## 🗂️ Pautas (Topics)

### POST /api/v1/topics
#### 📍 Rota
`POST /api/v1/topics`
#### Descrição
Cria uma nova pauta para votação.
#### Autenticação
Não requer.
#### Headers
- Content-Type: application/json
#### Request Body
```json
{
  "title": "Assembleia Orçamento 2026",
  "description": "Aprovação do orçamento anual do condomínio"
}
```
#### Response 201
```json
{
  "id": 1,
  "title": "Assembleia Orçamento 2026",
  "description": "Aprovação do orçamento anual do condomínio",
  "status": "PENDING",
  "createdAt": "2026-02-01T10:00:00"
}
```
#### Response 400/404/409
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Título obrigatório",
  "path": "/api/v1/topics",
  "timestamp": "2026-02-01T10:00:00Z"
}
```
#### Exemplo cURL
```bash
curl -X POST http://localhost:8080/api/v1/topics \
  -H "Content-Type: application/json" \
  -d '{"title": "Assembleia Orçamento 2026", "description": "Aprovação do orçamento anual do condomínio"}'
```
#### Caminho Feliz
1. Crie uma pauta
2. Crie sessão para a pauta
3. Registre votos
4. Consulte resultado
#### Possíveis erros
- Título em branco
- Descrição muito longa

---

### GET /api/v1/topics
#### 📍 Rota
`GET /api/v1/topics`
#### Descrição
Lista todas as pautas cadastradas (paginado).
#### Autenticação
Não requer.
#### Headers
Opcional: parâmetros de paginação Spring (page, size, sort)
#### Response 200
```json
{
  "_embedded": {
    "topicResponseList": [
      {
        "id": 1,
        "title": "Assembleia Orçamento 2026",
        "description": "Aprovação do orçamento anual do condomínio",
        "status": "PENDING",
        "createdAt": "2026-02-01T10:00:00"
      }
    ]
  },
  "page": { "size": 20, "totalElements": 1, "totalPages": 1, "number": 0 }
}
```
#### Exemplo cURL
```bash
curl http://localhost:8080/api/v1/topics
```

---

### GET /api/v1/topics/{id}
#### 📍 Rota
`GET /api/v1/topics/{id}`
#### Descrição
Consulta uma pauta pelo ID.
#### Response 200
```json
{
  "id": 1,
  "title": "Assembleia Orçamento 2026",
  "description": "Aprovação do orçamento anual do condomínio",
  "status": "PENDING",
  "createdAt": "2026-02-01T10:00:00"
}
```
#### Response 404
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Pauta não encontrada",
  "path": "/api/v1/topics/99",
  "timestamp": "2026-02-01T10:00:00Z"
}
```
#### Exemplo cURL
```bash
curl http://localhost:8080/api/v1/topics/1
```

---

### DELETE /api/v1/topics/{id}
#### 📍 Rota
`DELETE /api/v1/topics/{id}`
#### Descrição
Remove uma pauta pelo ID.
#### Response 204
Sem corpo.
#### Response 404
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Pauta não encontrada",
  "path": "/api/v1/topics/99",
  "timestamp": "2026-02-01T10:00:00Z"
}
```
#### Exemplo cURL
```bash
curl -X DELETE http://localhost:8080/api/v1/topics/1
```

---

## 🗂️ Sessões (Sessions)

### POST /api/v1/topics/{topicId}/sessions
#### 📍 Rota
`POST /api/v1/topics/{topicId}/sessions`
#### Descrição
Abre uma sessão de votação para uma pauta.
#### Request Body (opcional)
```json
{
  "durationMinutes": 10
}
```
#### Response 201
```json
{
  "id": 1,
  "topicId": 1,
  "opensAt": "2026-02-01T10:05:00Z",
  "closesAt": "2026-02-01T10:15:00Z"
}
```
#### Exemplo cURL
```bash
curl -X POST http://localhost:8080/api/v1/topics/1/sessions \
  -H "Content-Type: application/json" \
  -d '{"durationMinutes": 10}'
```

---

## 🗂️ Votações (Votes)

### GET /api/v1/topics/{topicId}/sessions/check-open
#### 📍 Rota
`GET /api/v1/topics/{topicId}/sessions/check-open`
#### Descrição
Verifica se é possível abrir uma sessão para a pauta.
#### Response 200
```json
{
  "canOpen": true
}
```
#### Exemplo cURL
```bash
curl http://localhost:8080/api/v1/topics/1/sessions/check-open
```

---

### GET /api/v1/sessions/{sessionId}/open-now
#### 📍 Rota
`GET /api/v1/sessions/{sessionId}/open-now`
#### Descrição
Verifica se a sessão está aberta neste momento.
#### Response 200
```json
{
  "openNow": true
}
```
#### Exemplo cURL
```bash
curl http://localhost:8080/api/v1/sessions/1/open-now
```

---

### POST /api/v1/sessions/{sessionId}/votes
#### 📍 Rota
`POST /api/v1/sessions/{sessionId}/votes`
#### Descrição
Registra um voto em uma sessão.
#### Request Body
```json
{
  "choice": "SIM",
  "cpf": "12345678901"
}
```
#### Response 201
```json
{
  "id": 1,
  "sessionId": 1,
  "associateId": "12345678901",
  "choice": "SIM",
  "votedAt": "2026-02-01T10:10:00"
}
```
#### Response 400/404/409
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "CPF inválido ou voto duplicado",
  "path": "/api/v1/sessions/1/votes",
  "timestamp": "2026-02-01T10:10:00Z"
}
```
#### Exemplo cURL
```bash
curl -X POST http://localhost:8080/api/v1/sessions/1/votes \
  -H "Content-Type: application/json" \
  -d '{"choice": "SIM", "cpf": "12345678901"}'
```

---

### GET /api/v1/sessions/{sessionId}/votes/count
#### 📍 Rota
`GET /api/v1/sessions/{sessionId}/votes/count`
#### Descrição
Consulta a apuração dos votos de uma sessão.
#### Response 200
```json
{
  "sessionId": 1,
  "topicId": 1,
  "yes": 10,
  "no": 2,
  "result": "APPROVED"
}
```
#### Exemplo cURL
```bash
curl http://localhost:8080/api/v1/sessions/1/votes/count
```

---

## 🧪 Como Testar (Fluxo Completo)

1. **Criar pauta:**
   ```bash
   curl -X POST http://localhost:8080/api/v1/topics \
     -H "Content-Type: application/json" \
     -d '{"title": "Reforma da piscina", "description": "Votação para reforma da área de lazer"}'
   ```
2. **Abrir sessão:**
   ```bash
   curl -X POST http://localhost:8080/api/v1/topics/1/sessions \
     -H "Content-Type: application/json" \
     -d '{"durationMinutes": 15}'
   ```
3. **Registrar voto:**
   ```bash
   curl -X POST http://localhost:8080/api/v1/sessions/1/votes \
     -H "Content-Type: application/json" \
     -d '{"choice": "SIM", "cpf": "12345678901"}'
   ```
4. **Consultar apuração:**
   ```bash
   curl http://localhost:8080/api/v1/sessions/1/votes/count
   ```

---

## 🧰 Ferramentas de Teste Recomendadas

- [Postman](https://www.postman.com/) (coleção pronta em `postman/`)
- [Insomnia](https://insomnia.rest/)
- cURL (exemplos acima)

---

## 📊 Tabela de Status Codes

| Código | Significado         | Quando ocorre                                 |
|--------|---------------------|-----------------------------------------------|
| 200    | OK                  | Consulta/listagem bem-sucedida                |
| 201    | Created             | Recurso criado com sucesso                    |
| 204    | No Content          | Remoção bem-sucedida                          |
| 400    | Bad Request         | Dados inválidos, CPF mal formatado, etc.      |
| 404    | Not Found           | Recurso não encontrado                        |
| 409    | Conflict            | Voto duplicado, sessão já aberta, etc.        |
| 500    | Internal Server Error | Erro inesperado no servidor                 |

---

## 🛠️ Dicas de Troubleshooting

- **API não responde:** Verifique se o banco está criado e o serviço está rodando.
- **Erro 400/409:** Confira se o CPF está correto e se não está votando duas vezes.
- **Erro 404:** IDs informados existem? Pauta/sessão podem ter sido removidas.
- **Erro banco:** Veja logs do Spring Boot e conexão com PostgreSQL.
- **Swagger:** Acesse `/swagger-ui.html` para explorar e testar endpoints.

---

## 👤 Autor

**Gabriel Portugal**  
💼 [Portfólio](https://gabrielportugal.web.app/)  
💻 [LinkedIn](https://www.linkedin.com/in/gabriel-portugal-b26a13188/)

---