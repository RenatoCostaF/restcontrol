# RestControl API

API REST para gestao de usuarios no contexto de restaurantes, desenvolvida com Spring Boot. O projeto oferece cadastro, autenticacao com JWT, controle de acesso por perfil e documentacao interativa via Swagger.

## Visao Geral

O `restcontrol-api` foi desenvolvido para centralizar o gerenciamento de usuarios com uma base simples, segura e preparada para evolucao. A aplicacao segue uma organizacao em camadas e utiliza boas praticas comuns em APIs Java modernas, como validacao de payloads, tratamento global de excecoes e autenticacao stateless.

## Principais Funcionalidades

- Cadastro de usuarios com validacao de dados
- Autenticacao via login e senha com emissao de token JWT
- Controle de acesso baseado em perfis
- Atualizacao e remocao de usuarios
- Busca de usuarios por nome
- Documentacao automatica da API com OpenAPI/Swagger
- Ambiente de execucao local e com Docker Compose

## Stack Tecnologica

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- MapStruct
- Swagger / OpenAPI
- Docker e Docker Compose

## Arquitetura do Projeto

O projeto esta organizado em camadas para separar responsabilidades e facilitar manutencao:

- `controllers`: exposicao dos endpoints HTTP
- `services`: regras de negocio e orquestracao
- `repositories`: acesso a dados com JPA
- `entities`: representacao do dominio
- `dtos`: contratos de entrada e saida da API
- `mappers`: conversao entre entidades e DTOs
- `infra/security`: autenticacao, autorizacao e filtro JWT
- `infra/exceptions`: excecoes de dominio e tratamento global

## Regras de Acesso

As regras de seguranca atualmente implementadas sao:

- `POST /v1/auth/**`: acesso publico
- `POST /v1/user`: acesso publico
- `GET /v1/user/{name}`: restrito ao perfil `DONO_RESTAURANTE`
- `DELETE /v1/user/{id}`: restrito ao perfil `DONO_RESTAURANTE`
- Demais endpoints: exigem autenticacao

Os perfis de usuario aceitos pelo sistema sao:

- `dono_restaurante`
- `cliente`

## Endpoints Principais

| Metodo | Endpoint | Descricao | Autenticacao |
| --- | --- | --- | --- |
| `POST` | `/v1/auth/login` | Autentica usuario e retorna JWT | Nao |
| `POST` | `/v1/user` | Cria um novo usuario | Nao |
| `GET` | `/v1/user/{name}` | Busca usuarios pelo nome | Sim |
| `PUT` | `/v1/user/{id}` | Atualiza dados de um usuario | Sim |
| `DELETE` | `/v1/user/{id}` | Remove um usuario | Sim |

## Exemplo de Payloads

### Criacao de usuario

```json
{
  "name": "Maria Oliveira",
  "email": "maria@example.com",
  "login": "maria.oliveira",
  "password": "senha123",
  "address": "Rua das Flores, 123",
  "role": "cliente"
}
```

### Login

```json
{
  "login": "maria.oliveira",
  "password": "senha123"
}
```

### Atualizacao de usuario

```json
{
  "name": "Maria Oliveira Santos",
  "email": "maria.santos@example.com",
  "login": "maria.santos",
  "address": "Av. Central, 500"
}
```

## Como Executar Localmente

### Pre-requisitos

- Java 21
- Maven 3.9+ ou uso do Maven Wrapper
- PostgreSQL

### 1. Suba o banco de dados

Voce pode usar uma instancia local do PostgreSQL ou subir apenas o banco com Docker:

```bash
docker compose up -d postgres
```

### 2. Configure a aplicacao

As configuracoes padrao estao em `src/main/resources/application.properties`.

Por padrao, o projeto utiliza:

- Banco: `jdbc:postgresql://localhost:5432/restcontrol`
- Usuario: `postgres`
- Senha: `postgres`

### 3. Execute a aplicacao

Com Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Ou com Maven instalado:

```bash
mvn spring-boot:run
```

A API ficara disponivel em:

```text
http://localhost:8080
```

## Como Executar com Docker Compose

Para subir banco e aplicacao juntos:

```bash
docker compose up --build
```

Servicos expostos:

- API: `http://localhost:8080`
- PostgreSQL: `localhost:5432`

## Variaveis de Ambiente

Para execucao em ambientes externos ao arquivo local de propriedades, as seguintes variaveis sao as mais importantes:

| Variavel | Descricao |
| --- | --- |
| `SPRING_DATASOURCE_URL` | URL de conexao com o PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | Usuario do banco |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco |
| `JWT_SECRET` | Segredo utilizado para assinatura do token JWT |

## Banco de Dados e Seeds

O projeto utiliza os arquivos abaixo na inicializacao:

- `src/main/resources/schema.sql`: criacao da estrutura da tabela `users`
- `src/main/resources/data.sql`: carga inicial de usuarios

Existem usuarios seed para apoio em testes e desenvolvimento. Como as senhas estao armazenadas de forma segura, o ideal e criar seus proprios usuarios de teste via endpoint publico de cadastro quando necessario.

## Documentacao da API

A documentacao interativa da API fica disponivel em:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

O repositorio tambem inclui uma collection do Postman:

- `postman-collection.json`

## Qualidade e Seguranca

O projeto ja conta com alguns fundamentos importantes:

- Validacao de entrada com Jakarta Validation
- Tratamento centralizado de excecoes
- Senhas protegidas com `BCrypt`
- Autenticacao stateless com JWT
- Autorizacao por perfil com Spring Security

## Estrutura do Repositorio

```text
.
|-- src/
|   |-- main/
|   |   |-- java/com/restocontrol/restcontrol_api/
|   |   |   |-- controllers/
|   |   |   |-- dtos/
|   |   |   |-- entities/
|   |   |   |-- infra/
|   |   |   |-- mappers/
|   |   |   |-- repositories/
|   |   |   `-- services/
|   |   `-- resources/
|   |       |-- application.properties
|   |       |-- data.sql
|   |       `-- schema.sql
|   `-- test/
|-- Dockerfile
|-- docker-compose.yml
|-- pom.xml
`-- postman-collection.json
```

## Autor

Projeto desenvolvido no contexto do Tech Challenge / FIAP, com foco em boas praticas de desenvolvimento backend, seguranca e organizacao arquitetural.
