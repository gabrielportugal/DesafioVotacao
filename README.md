
# Desafio de Votação

## 📌 Visão Geral do Projeto

API REST para gerenciamento de pautas, sessões de votação e votos de associados, desenvolvida com foco em arquitetura limpa, robustez e clareza de regras de negócio. O sistema permite a abertura de pautas, criação de sessões de votação com duração configurável, registro de votos e consulta de resultados, seguindo requisitos técnicos e de negócio típicos de ambientes cooperativos.

---

## 🏗️ Arquitetura e Organização

O projeto adota DDD Light, Clean Architecture e princípios SOLID, promovendo separação clara de responsabilidades, baixo acoplamento e alta coesão. As camadas são organizadas conforme abaixo:

- **Domain:** Entidades e regras de negócio puras, sem dependências técnicas.
- **Application:** Casos de uso que orquestram operações e persistência, sem lógica de domínio.
- **Infrastructure:** Implementação de persistência (JPA, repositórios), controllers REST e mapeamentos.
- **Interface:** Controllers REST, DTOs e mappers para adaptação entre camadas.

### 🧩 Estrutura de Pastas

```text
src/main/java/com/sicredi/votacao
├── application
│   └── usecase
├── domain
│   ├── model
│   └── repository
├── exceptions
├── infrastructure
│   ├── persistence
│   │   ├── entity
│   │   ├── repository
│   │   └── mapper
│   └── configuration
├── interface
│   └── rest
│       ├── controller
│       ├── dto
│       └── mapper
└── VotacaoApplication.java
```

---

## 🧠 Regras de Negócio

- **Abertura de Pautas:** Toda pauta é criada com estado inicial `OPEN`.
- **Criação de Sessões de Votação:** Sessões são sempre vinculadas a uma pauta existente.
- **Duração Configurável:** A duração da sessão é definida na criação, com valor padrão aplicado se não informado.
- **Expiração de Sessão:** Determinada por `createdAt + duration` (minutos).
- **Validação de Expiração:** O método de domínio `isExpired()` é usado apenas para validação, sem persistência.
- **Fechamento Centralizado:** O status (`OPEN` → `CLOSED`) e o campo `closedAt` são atualizados exclusivamente pelo caso de uso `CheckAndCloseVotingSessionUseCase`.
- **Fechamento Sob Demanda:** Não há schedulers, threads ou jobs em background; o fechamento ocorre sob demanda, sempre que a sessão é lida ou utilizada.
- **Consistência Garantida:** Toda operação de leitura ou uso de sessão passa pelo caso de uso de verificação e fechamento, garantindo estado consistente.
- **Remoção de closedBy:** O campo foi removido por não fazer parte da regra de negócio.
- **Uso de Mappers:** Conversão entre entidades de domínio, JPA e DTOs é feita por mappers dedicados.

---

## 🔀 Fluxo Principal da Aplicação

1. Cadastro de pauta (`Topic`) com estado inicial `OPEN`.
2. Criação de sessão de votação (`VotingSession`) vinculada à pauta, com duração definida.
3. Registro de votos por associados, respeitando unicidade por pauta.
4. Consulta de resultados e sessões, sempre garantindo consistência do estado via verificação sob demanda.

---

## 🔁 Estratégias de Atualização e Consistência de Dados

A aplicação utiliza uma estratégia de atualização sob demanda para o fechamento de sessões de votação. Em vez de empregar threads, schedulers ou jobs em background, toda operação de leitura ou uso de uma sessão (como consultas, votação ou obtenção de tópicos) passa por um caso de uso responsável por validar se a sessão expirou. Caso a expiração seja detectada, a sessão é automaticamente fechada e persistida antes de ser retornada.

Essa abordagem é semelhante à utilizada por grandes portais como [Portfólio](https://g1.globo.com/) e [Portal Multiplix](https://www.portalmultiplix.com/) , onde o estado mais recente é atualizado no momento do primeiro acesso, garantindo consistência, simplicidade operacional e menor custo de infraestrutura. Assim, o sistema permanece sempre atualizado sem a complexidade de processos assíncronos contínuos.

---

## 🧪 Testes

- Testes automatizados cobrem casos de uso, regras de negócio e integração.
- Recomenda-se rodar os testes via Maven para garantir a integridade do sistema antes de qualquer entrega.

```bash
mvn test
```

---



## 🌱 Versionamento de API

- Versionamento por URL, configurado centralizadamente (desafio/src/main/resources/infrastructure/configuration/api-version.yml).
- Exemplo de URL: `/api/v1/topics`
- Nova versão: criar controllers em `interfaces.rest.v2` e atualizar configuração.
- Controllers não possuem versão hardcoded, facilitando evolução.

---

## 🌿 Estratégia de Branches (Git)

- **main:** Produção estável.
- **develop:** Desenvolvimento contínuo.
- **release/x.x:** Estabilização e preparação de versões.
- Estratégia baseada em Git Flow, garantindo organização e controle de entregas.

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

### 🗄️ Configuração do banco de dados
É obrigatório configurar os arquivos de propriedades tanto do ambiente **main** quanto **test**.

```sql
CREATE DATABASE votingdb;
CREATE DATABASE votingdb_test;
```

⚠️ Importante
O Spring não cria o banco de dados automaticamente.
É necessário criar manualmente os bancos com os mesmos nomes definidos nos arquivos de configuração.

### Executar:

```bash
./mvnw spring-boot:run
```

A API estará disponível em:  
`http://localhost:8080/api/v1`

---

## 👤 Autor

**Gabriel Portugal**  
💼 [Portfólio](https://gabrielportugal.web.app/)  
💻 [LinkedIn](https://www.linkedin.com/in/gabriel-portugal-b26a13188/)