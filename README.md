
# 🗳️ Desafio de Votação

## 📚 Visão Geral

API REST para gerenciamento de pautas, sessões de votação e votos, com arquitetura limpa, regras de negócio claras e alta manutenibilidade.

<img src="./docs/desafioVotacao.gif" alt="Fluxo Principal do sistema" width="80%"/>

---

## 🏗️ Arquitetura e Padrões
O projeto adota **DDD Light** e **Clean Architecture**, com princípios **SOLID** e separação clara de responsabilidades:

- **Domain:** Entidades e regras de negócio puras.
- **Application (UseCases):** Orquestração e persistência.
- **Infrastructure:** Persistência, integrações externas, configs.
- **Interfaces:** Controllers REST, DTOs, mappers.

> **Importante:** Toda lógica de negócio reside em UseCases e entidades de domínio. Controllers REST são finos, apenas adaptando requisições e respostas.

A imagem a seguir apresenta o diagrama de sequência que representa a relação entre as camadas do sistema.

<img src="./docs/diagramaSequenciaExemplo.png" alt="Exemplo de Diagrama de Sequência das Camadas" width="80%"/>

---

---

## 🗄️ Banco de Dados (PostgreSQL)
O projeto utiliza **PostgreSQL** como sistema de gerenciamento de banco de dados, adotando uma padronização rigorosa de nomenclatura para garantir clareza, legibilidade e manutenção facilitada das consultas SQL.

Todas as colunas das tabelas utilizam um prefixo identificador da tabela à qual pertencem. Essa abordagem traz benefícios importantes:
- Facilita a identificação imediata da origem da coluna em consultas complexas.
- Evita ambiguidades em JOINs entre múltiplas tabelas.
- Torna as queries mais legíveis e autoexplicativas.
- Reduz erros em manutenção e evolução do banco.

Exemplo: top_id, top_created_at, vot_associate_id.

Dessa forma, ao analisar uma query, é possível evidenciar rapidamente a qual tabela cada campo pertence, mesmo sem aliases explícitos. A imagem abaixo ilustra a Modelagem Entidade Relacionamento do banco de dados:

<img src="./docs/bancoDados.png" alt="Modelo Entidade Relacionamento" width="80%"/>

---


## 🚀 Diferenciais do Projeto
- **Arquitetura Clean com DDD Light**: Estrutura modular que separa claramente responsabilidades entre domínio, aplicação e infraestrutura
- **Lazy Update (Atualização Sob Demanda)**: Carregamento inteligente de dados que otimiza performance e consumo de recursos
- **Configuração Centralizada**: Gerenciamento unificado de propriedades e ambientes para fácil manutenção
- **Integração Fake Desacoplada**: Implementações simuladas de serviços externos para desenvolvimento e testes isolados
- **Cobertura de Testes Abrangente**: Suíte completa de testes que garante qualidade e confiabilidade do código
- **Pronto para Escalar**: Arquitetura projetada para crescimento com capacidade de expansão horizontal e vertical
- **Baixo Acoplamento e Alta Coesão**: Componentes independentes com responsabilidades bem definidas e interfaces claras
- **Mappers Dedicados**: Conversão estruturada de dados entre camadas, garantindo integridade e consistência
- **Git Hooks**: Executam testes automaticamente antes de cada push, incluindo testes unitários com Maven e validações de qualidade, impedindo push se os testes falharem.

#### 🎯 Pontos de Destaque
- **Arquitetura Limpa**: Separação clara entre domínio, aplicação e infraestrutura
- **SOLID Aplicado**: Cada classe tem responsabilidade única e bem definida
- **Testabilidade**: Cobertura abrangente com testes unitários e de integração
- **Performance Otimizada**: Resultados validados com k6 e monitoramento contínuo
- **Manutenibilidade**: Código limpo, documentado e fácil de estender
- **Resiliência**: Tratamento adequado de erros e fallbacks configuráveis
- **Escalabilidade**: Pronto para crescimento com monitoramento implementado

> **Nota**: O projeto foi estruturado para ser facilmente compreendido, mantido e estendido, seguindo as melhores práticas do mercado.

---

## 🧠 Regras de Negócio

- Toda pauta inicia com status `OPEN`
- Sessões sempre vinculadas a pauta existente
- Duração configurável, fallback para valor padrão
- Fechamento sob demanda (Lazy Update)
- Consistência garantida em toda leitura/uso de sessão

---
## 🗂️ Estrutura de Pastas

```text
desafio/src/main/java/com/sicredi/votacao
├── application
│   └── config
│   └── usecase
│       ├── topic
│       ├── vote
│       └── votingsession
│       └── ValidateCpfUseCase.java
│   └── utils
├── domain
│   ├── model
│   └── repository
├── exceptions
│   └── ...
├── infrastructure
│   └── configuration
│   └── external
│       └── cpf
│   └── persistence
├── interfaces
│   └── rest
│       └── dto
│       └── mapper
│       └── v1
│           ├── CpfValidationController.java
│           ├── TopicController.java
│           ├── VoteController.java
│           └── VotingSessionController.java
│       └── v2
└── VotacaoApplication.java
```
---

## ⚡ Instalação Rápida

### Pré-requisitos

- Java 17
- Maven
- PostgreSQL
- Docker (opcional)

### Passos

```bash
git clone https://github.com/gabrielportugal/DesafioVotacao.git
cd DesafioVotacao/desafio

# Crie os bancos manualmente
CREATE DATABASE votacao;
CREATE DATABASE votacao-teste;

# Altere as credenciais do application.properties e application-test.properties

# Execute
mvnw spring-boot:run
```

#### Com Docker

```bash
docker-compose up --build
```
---

## 🔌 Endpoints da API

#### 📝 Gerenciamento de Pautas
- POST ```/api/v1/topic``` → Cria uma nova pauta para discussão e votação na assembleia
- GET ```/api/v1/topic``` → Lista todas as pautas cadastradas no sistema com paginação
- GET ```/api/v1/topic/{id}``` → Consulta os detalhes de uma pauta específica por ID
- DELETE ```/api/v1/topic/{id}``` → Remove uma pauta através de exclusão lógica (soft delete)
- GET ```/api/v1/topic/result/{id}``` → Retorna o resultado consolidado da votação de uma pauta

#### 🗳️ Sessões e Votação

- POST ```/api/v1/voting-session``` → Abre uma nova sessão de votação para uma pauta existente
- POST ```/api/v1/votes``` → Registra um novo voto em uma sessão ativa (Nota 1)
- POST ```/api/v1/cpf/validation``` → Valida um CPF através de um serviço externo (mock/facade) que retorna aleatoriamente se é válido ou não (Nota 2)

> **Nota 1**: O campo ```associateId``` possui flexibilidade para aceitar múltiplos formatos de identificação, incluindo valores numéricos, strings, e CPFs com ou sem formatação (pontos e traços).

> **Nota 2**: A rota bônus possui lógica automatizada de geração e validação de CPFs. O sistema: Gera números de CPF aleatoriamente; Valida automaticamente se cada CPF gerado é válido; Retorna de forma randômica se o CPF está habilitado ou não para votação. Esta funcionalidade permite testar o fluxo completo sem necessidade de entrada manual de CPFs válidos.

---

## 📬 Testes via Postman

Importe os arquivos no Postman:

- Coleção: `postman/DesafioVotacao.postman_collection.json`

---

## 🧪 Testes e Performance

- Testes unitários e integração: `mvn test`
- Testes de performance: k6 (`k6 run k6-performance/create-topic-basic.js`)
- Monitoramento: Prometheus

### 📊 Resultados Recentes

| Métrica             | Valor   | Status  | Limite    |
|---------------------|---------|---------|-----------|
| Throughput máximo   | 968/s   | ✅      | >100/s    |
| Usuários simultâneos| 2000    | ✅      | >=2000    |
| Latência média      | 140ms   | ✅      | <800ms    |

### 📊 Teste unitário e integração

<img src="./docs/teste.png" alt="Teste unitário e integração" width="80%"/>

---

## ⚙️ Funcionalidades Técnicas

### 💤 Lazy Update (Atualização Sob Demanda)
A técnica de **Lazy Update** garante que sessões de votação sejam fechadas apenas quando acessadas, eliminando a necessidade de schedulers, jobs ou threads.

- Sem jobs ou schedulers: Elimina complexidade operacional
- Fechamento sob demanda: Sessões são validadas e fechadas apenas quando acessadas
- Consistência garantida: Estado sempre correto no momento do uso
- Menor custo: Reduz consumo de recursos

### 🧾 Validação de CPF 
- **Validador de CPF** implementado em `utils` e utilizado no `RegisterVoteUseCase`.
- **CPF inválido:** Bloqueia o voto imediatamente, lançando `InvalidCpfException`.
- **CPF válido:** Segue para validação externa (fake), simulando consulta a serviço externo.
- **Reutilização de exceptions:** O sistema utiliza exceções já existentes para padronizar respostas.

### ⚙️ Configuração Centralizada
- Duração padrão de sessão configurável em VotingSessionProperties
- Fallback automático para valores padrão

### 🏆 Integração Fake de CPF
- Endpoint dedicado: /api/v1/cpf/validation para testes do bônus
- Comportamento aleatório: Simula cenários reais (200, 403, 404)
- Totalmente desacoplada: Facilmente substituível por implementação real

---
## 🔀 Fluxo Principal

<img src="./docs/fluxoPrincipal.png" alt="Fluxo Principal do sistema" width="80%"/>

---

## 🌱 Versionamento de API

- **Versionamento por URL:** Configurado centralizadamente.
- **Exemplo:** `/api/v1/topic`
- **Evolução:** Novas versões criadas em `interfaces.rest.v2`, sem hardcode de versão nos controllers.
---
## 🌿 Estratégia de Branches (Git)

- **main:** Produção estável.
- **develop:** Desenvolvimento contínuo.
- **release/x.x:** Estabilização de versões.
- **Git Flow:** Organização e controle de entregas.

### 🔧 Configuração dos Git Hooks
Para garantir a qualidade do código, este projeto utiliza Git Hooks que executam testes automaticamente antes de cada push.
- Testes unitários com Maven
- Validação de qualidade do código
- Impede push se os testes falharem

```bash
# 1. Configure os hooks do Git
git config core.hooksPath .githooks

# 2. Teste o hook manualmente
./.githooks/pre-push

#3. Faça o push normalmente
git push origin <branch>

#3.1. Faça o push sem realização do teste
git push origin <branch> --no-verify

```

<img src="./docs/githook.png" alt="GitHook" width="80%"/>

<img src="./docs/githook_final.png" alt="GitHook Final" width="80%"/>

---
## 🚀 Performance e Monitoramento

Para garantir que a aplicação comporta-se adequadamente em cenários de centenas de milhares de votos, implementei uma solução completa de monitoramento usando:

### 🔍 Prometheus
Sistema de coleta de métricas que monitora em tempo real:
- Taxa de requisições por segundo
- Tempo médio de resposta
- Uso de recursos (CPU, memória)
- Health da aplicação e dependências

### ⚡ k6
Ferramenta de teste de carga que simula:
- Picos de acesso simultâneo
- Cenários realistas de votação
- Estresse progressivo da aplicação
- Validação de SLAs

### 📈 Métricas Monitoradas
- **Latência**: 95% das requisições < 500ms sob carga de 500 usuários
- **Throughput**: Até 1000 requisições/segundo
- **Disponibilidade**: 99.9% uptime
- **Escalabilidade**: Resposta linear ao aumento de carga

### Prints do Prometheus
- Uso da Memória Heap em MB

<img src="./docs/memoriaHeapUsadaEmMb.jpg" alt="Uso da Memória Heap em MB" width="25%"/>

- Percentual da Memória HEAP x Memória Máxima

<img src="./docs/memoriaHeapXMax_percentual.png" alt="Percentual da Memória HEAP x Memória Máxima" width="25%"/>

- Uso da CPU

<img src="./docs/usoCpu.png" alt="Uso da CPU" width="25%"/>

---
## 📡 Exemplos Práticos

### Criar Pautas
```bash
curl -X POST http://localhost:8080/api/v1/topic \
  -H "Content-Type: application/json" \
  -d '{"title": "Orçamento 2026", "description": "Aprovação anual"}'
  ```

  ### Criar Sessão
```bash
curl -X POST http://localhost:8080/api/v1/voting-session \
  -H "Content-Type: application/json" \
  -d '{"topicId": 1, "duration": 15}'
  ```

  ### Criar Voto
```bash
curl -X POST http://localhost:8080/api/v1/votes \
  -H "Content-Type: application/json" \
  -d '{"choice": "SIM", "topicId": 1, "associateId": "12345678909"}'
  ```
---
## 🛠️ Ferramentas de Desenvolvimento
### Testes
- **JUnit 5**: Testes unitários e integração
- **Mockito**: Mock de dependências
- **k6**: Testes de performance e carga
- **Postman**: Coleção pronta em postman/

### Monitoramento
- **Prometheus**: Coleta de métricas
- **Spring Boot Actuator**: Health checks e métricas
---

## 👤 Autor

**Gabriel Portugal**  
💼 [Portfólio](https://gabrielportugal.web.app/)  
💻 [LinkedIn](https://www.linkedin.com/in/gabriel-portugal-b26a13188/)

---
## 📝 Licença

MIT © Gabriel Portugal