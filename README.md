# Projeto de uma aplicação de cadastro de pessoas

## Tecnologias utilizadas

- Java 17
- Spring Boot
- PostgreSQL
- React Native
- Expo

## Como executar a aplicação

### Backend

1. Criar o banco de dados com o nome `demo_app` e editar as credenciais de acesso ao banco de dados no arquivo [application.properties](./server/src/main/resources/application.properties).
2. Abrir o projeto  partir da pasta [server](./server) utilizando a sua IDE de preferência.
3. Executar o projeto pela IDE ou via terminal com o comando `mvn spring-boot:run`.

### Frontend

1. Abrir o projeto a partir da pasta [mobile](./mobile).
2. Executar o comando `npm install --force` para instalar as dependências.
3. Executar o comando `npm run web` para iniciar a aplicação.

## Endpoints

- `GET http://localhost:8080/api/v1/people` - Retorna todas as pessoas cadastradas.
- `POST http://localhost:8080/api/v1/people` - Cria uma nova pessoa.
- `DELETE http://localhost:8080/api/v1/people/{id}` - Deleta uma pessoa existente.
