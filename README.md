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

## Versionamento de Branches
O projeto adota uma estratégia de versionamento de branches baseada no **Git Flow**, utilizando a **branch main** para código estável e pronto para produção, a **branch develop** como base para o desenvolvimento contínuo de novas funcionalidades e melhorias, e branches no formato **release/x.x** para estabilização, ajustes finais e preparação de versões antes do merge definitivo na **main**, garantindo organização, controle de versões e segurança no processo de entrega.

## Versionamento da API
### Estratégia de Versionamento
A aplicação utiliza versionamento de API por URL, seguindo o padrão:
```
/api/v1/recursos
```
A versão da API é definida de forma centralizada por configuração, permitindo a evolução para novas versões sem necessidade de alterar o código dos controllers.

### Configuração Centralizada
A versão da API é definida no arquivo:
```
desafio/src/main/resources/infrastructure/configuration/api-version.yml
```

Exemplo:

```
api:
  version: v1
```

### Como funciona

- O path base dos endpoints é montado dinamicamente usando a configuração de versão.
- Para alterar a versão, basta atualizar a propriedade `api.version` no arquivo de configuração.
- Os controllers não possuem a versão hardcoded, garantindo fácil manutenção e evolução.
- Para criar uma nova versão (ex: v2), basta criar novos controllers em `interfaces.rest.v2`.
- As regras de negócio e repositórios são reutilizados entre versões, mantendo apenas a camada de interface separada.

### Exemplo de endpoint versionado

```
GET /api/v1/topics
```

### Evolução para novas versões

- Crie um novo pacote `interfaces.rest.v2` e adicione controllers específicos para a nova versão.
- Mantenha compatibilidade com versões anteriores, não quebrando contratos existentes.
- Não duplique regras de negócio ou implementações de repositório.
- Controllers devem permanecer finos, sem lógica de negócio.

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

  # Regras de Negócio e Decisões Arquiteturais

### Regras de Negócio Implementadas

- **Abertura de Pautas (Topic):** Toda pauta é criada com estado inicial `OPEN`, permitindo o início imediato de sessões de votação vinculadas.
- **Criação de Sessões de Votação (VotingSession):** Sessões são sempre associadas a uma pauta existente, garantindo integridade referencial.
- **Duração Configurável:** A duração da sessão de votação é definida no momento da criação. Caso não seja informada, um valor padrão é aplicado automaticamente.
- **Expiração de Sessão:** A expiração é determinada por `createdAt + duration` (em minutos). Após esse período, a sessão não permite novos votos.
- **Validação de Expiração:** O método de domínio `isExpired()` é utilizado exclusivamente para validação de regra de negócio, sem realizar persistência ou efeitos colaterais.
- **Centralização do Fechamento:** A atualização do status da sessão (`OPEN` → `CLOSED`) e o registro do `closedAt` são realizados exclusivamente pelo caso de uso `CheckAndCloseVotingSessionUseCase`, garantindo consistência e evitando duplicidade de lógica.
- **Fechamento Sob Demanda (Lazy Evaluation):** Não há uso de schedulers, threads ou jobs em background. O fechamento ocorre sob demanda: ao acessar ou utilizar uma sessão, o sistema verifica e atualiza seu estado conforme necessário.
- **Consistência Garantida:** Toda leitura ou uso de sessão de votação (consultas, validações, votação, etc.) obrigatoriamente passa pelo `CheckAndCloseVotingSessionUseCase`, assegurando que o estado retornado sempre reflita a situação real (aberta ou fechada).
- **Remoção de closedBy:** O campo `closedBy` foi removido da entidade e da tabela, pois não faz parte das regras de negócio do domínio.

### Decisões Arquiteturais

- **Separação de Camadas:**
  - *Domínio:* Contém entidades e regras de negócio puras, sem dependências técnicas.
  - *Application:* Implementa casos de uso, orquestrando operações e persistência, sem lógica de domínio.
  - *Infrastructure:* Responsável por persistência (JPA, repositórios), controllers REST e mapeamentos.
- **Uso de Mappers:** Conversão entre entidades de domínio, entidades JPA e DTOs é realizada por mappers dedicados, promovendo baixo acoplamento e clareza.
- **Princípios de DDD Light, Clean Architecture e SOLID:**
  - O domínio é isolado e protegido de detalhes técnicos.
  - Casos de uso centralizam regras de aplicação e persistência.
  - Controllers e infraestrutura apenas adaptam dados e delegam operações.
  - O projeto favorece baixo acoplamento, alta coesão e facilidade de testes.

Essas decisões garantem que o sistema seja robusto, consistente e de fácil manutenção, alinhado com as melhores práticas de arquitetura backend.

## Estratégia de Fechamento de Sessões

A aplicação utiliza uma estratégia de atualização sob demanda para o fechamento de sessões de votação. Em vez de empregar threads, schedulers ou jobs em background, toda operação de leitura ou uso de uma sessão (como consultas, votação ou obtenção de tópicos) passa por um caso de uso responsável por validar se a sessão expirou. Caso a expiração seja detectada, a sessão é automaticamente fechada e persistida antes de ser retornada.

Essa abordagem é semelhante à utilizada por grandes portais como [Portfólio](https://g1.globo.com/) e [Portal Multiplix](https://www.portalmultiplix.com/) , onde o estado mais recente é atualizado no momento do primeiro acesso, garantindo consistência, simplicidade operacional e menor custo de infraestrutura. Assim, o sistema permanece sempre atualizado sem a complexidade de processos assíncronos contínuos.

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

## Autor
**Gabriel Portugal**  
💼 [Portfólio](https://gabrielportugal.web.app/)  
💻 [LinkedIn](https://www.linkedin.com/in/gabriel-portugal-b26a13188/)