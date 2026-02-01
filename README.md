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
git clone https://github.com/<seu-usuario>/DesafioVotacao.git
cd DesafioVotacao
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

Acesse:  
`http://localhost:8080/api/v1`

---

## 👤 Autor

**Gabriel Portugal**  
💼 [Portfólio](https://gabrielportugal.web.app/)  
💻 [LinkedIn](https://www.linkedin.com/in/gabriel-portugal-b26a13188/)

---