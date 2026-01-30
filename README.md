
# Desafio de Votação

API REST para gerenciamento de pautas, sessões de votação e votos de associados, conforme requisitos do desafio técnico.

## 📌 Descrição
Este projeto implementa uma solução backend para sessões de votação cooperativa onde:
- Cada associado pode votar uma vez por pauta (Sim/Não).
- É possível abrir sessões de votação com tempo configurável.
- A API expõe serviços REST para:
    - cadastrar pautas;
    - abrir sessões;
    - registrar votos;
    - consultar resultados.

A solução segue uma arquitetura DDD Light + Clean Architecture, promovendo boa organização de código, separação de responsabilidades e facilidade de manutenção e evolução.

## 🚀 Funcionalidades
- Criar pauta
- Abrir sessão de votação (tempo configurável)
- Receber votos de associados (YES / NO)
- Contabilizar votos e devolver resultado
- Cada associado pode votar apenas uma vez por pauta
- Sessões expiram automaticamente pelo tempo definido
- Documentação de API via OpenAPI/Swagger
- Métricas e observabilidade com Spring Boot Actuator

## 🧱 Tecnologias e Ferramentas
- Linguagen: Java 17
- Framework: Spring Boot
- Persistência: PostgreSQL
- Migrações: Flyway
- Arquitetura: DDD Light + Clean Architecture

## 🧠 Arquitetura
O uso da arquitetura DDD Light + Clean Architecture permite organizar o projeto com foco no domínio do negócio, mantendo o código desacoplado de frameworks e detalhes técnicos. Essa abordagem facilita a manutenção, a evolução do sistema e os testes, além de tornar as regras de negócio mais claras, reutilizáveis e protegidas contra mudanças em tecnologias externas. Abaixo é apresentada a estrutura de pastas do projeto, refletindo essa organização arquitetural.

```text
src/main/java/com/sicredi/votacao
├── application
│ └── usecase
├── domain
│ ├── model
│ ├── repository
├── exceptions
├── infrastructure
│ ├── persistence
│ │ ├── entity
│ │ ├── repository
│ │ └── mapper
│ └── configuration
├── interface
│ └── rest
│   ├── controller
│   ├── dto
│   └── mapper
└── VotacaoApplication.java
```

### 📚 Camadas
- **application**  
  Contém os casos de uso da aplicação e coordena o fluxo entre domínio e infraestrutura. Não possui regras de negócio, apenas orquestra ações.

- **domain**  
  Representa o núcleo do negócio com entidades, regras e contratos. Não depende de frameworks ou detalhes técnicos.

- **exceptions**  
  Centraliza exceções de negócio e de aplicação. Facilita o tratamento consistente de erros.

- **infrastructure**  
  Implementa detalhes técnicos como banco de dados, JPA e configurações do Spring. Pode mudar sem afetar o domínio.

- **interface**  
  Define os pontos de entrada da aplicação, como controllers REST e DTOs. Apenas adapta dados entre o mundo externo e a aplicação.



## 🛠️ Como rodar localmente

### Pré-requisitos
- JDK 17 instalado
- Maven
- PostgreSQL

### Passos
```bash
git clone https://github.com/<seu-usuario>/DesafioVotacao.git
cd DesafioVotacao
```

Configurar banco em application.yml:

```bash
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/votingdb
    username: postgres
    password: senha
  jpa:
    hibernate:
      ddl-auto: update
```

Executar:

```bash
.\mvnw.cmd spring-boot:run
```

A API estará disponível em:
```bash
http://localhost:8080/api/v1
```