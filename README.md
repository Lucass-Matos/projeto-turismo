# TURISMO BRASIL

Projeto individual: cadastro e consulta de destinos turisticos.

A aplicacao possui um front-end desenvolvido em React e uma API REST desenvolvida em Java com Spring Boot, JdbcTemplate e banco de dados H2.

## Requisitos atendidos

### Front-end

- Aplicacao desenvolvida em React.
- Formulario com 7 campos: nome, cidade, estado, tipo, preco, dias e descricao.
- Componente para cadastrar destinos.
- Componente para exibir os destinos cadastrados.
- Consumo da API utilizando GET e POST.
- Componentizacao em React.
- Uso de estado com `useState`.
- Uso de JSX.
- Uso de CSS Modules.
- Tratamento de carregamento, sucesso e erro.
- Lista com as 27 UFs brasileiras.
- Validacao de preco aceitando virgula ou ponto.

### Back-end

- API REST desenvolvida com Java e Spring Boot.
- Persistencia com JdbcTemplate.
- Banco de dados relacional H2.
- Validacao dos dados no servidor.
- GET para consultar destinos.
- POST para cadastrar destinos.
- GET por ID, PUT e DELETE como funcionalidades adicionais.
- Retorno dos codigos HTTP com `ResponseEntity.status(numero).body(resposta)`.
- CORS configurado para o front-end.

## Pre-requisitos

- Node.js e npm
- Java 17
- Maven
- IntelliJ IDEA ou Visual Studio Code

## Como executar

### Back-end

1. Abra a pasta `api` no IntelliJ IDEA ou no Visual Studio Code.
2. Aguarde o Maven baixar as dependencias.
3. Execute a classe `TurismoApiApplication` pelo botao Play do IntelliJ.

Tambem e possivel executar pelo terminal:

```powershell
cd api
mvn spring-boot:run
```

API:

```text
http://localhost:8080
```

Teste a listagem no navegador:

```text
http://localhost:8080/destinos
```

### Front-end

Na pasta `cliente`, execute:

```powershell
npm install
npm run dev
```

Acesse:

```text
http://localhost:5173
```

A API deve estar ligada antes de abrir o front-end.

## Banco de dados H2

O projeto utiliza o H2 em modo arquivo. Os dados permanecem salvos depois que a API e encerrada.

Console do H2:

```text
http://localhost:8080/h2-console
```

Dados de conexao:

```text
JDBC URL: jdbc:h2:file:./dados/turismodb
Usuario: sa
Senha: deixar vazia
```

## Endpoints utilizados

Base URL:

```text
http://localhost:8080
```

### GET /destinos

Lista os destinos cadastrados.

Retorno: `200 OK`

```json
[
  {
    "id": 1,
    "nome": "Praia de Maresias",
    "cidade": "Sao Sebastiao",
    "estado": "SP",
    "tipo": "Praia",
    "preco": 1200.50,
    "dias": 5,
    "descricao": "Viagem para o litoral"
  }
]
```

### GET /destinos/{id}

Busca um destino pelo ID.

Retornos:

- `200 OK` quando o destino existe.
- `404 Not Found` quando o destino nao existe.

### POST /destinos

Cadastra um novo destino.

Retornos:

- `201 Created` para cadastro realizado com sucesso.
- `400 Bad Request` para dados invalidos.

```json
{
  "nome": "Praia de Maresias",
  "cidade": "Sao Sebastiao",
  "estado": "SP",
  "tipo": "Praia",
  "preco": 1200.50,
  "dias": 5,
  "descricao": "Viagem para o litoral"
}
```

### PUT /destinos/{id}

Atualiza um destino existente.

Retornos:

- `200 OK` para atualizacao realizada com sucesso.
- `400 Bad Request` para dados invalidos.
- `404 Not Found` quando o destino nao existe.

### DELETE /destinos/{id}

Exclui um destino.

Retornos:

- `204 No Content` para exclusao realizada com sucesso.
- `404 Not Found` quando o destino nao existe.

## Validacoes

O back-end verifica:

- nome com pelo menos 3 caracteres;
- cidade obrigatoria;
- estado pertencente as 27 UFs brasileiras;
- tipo obrigatorio;
- preco maior que zero;
- quantidade de dias maior que zero.

O front-end permite informar o preco com virgula ou ponto, por exemplo:

```text
1200,50
1200.50
```

## Estrutura do projeto

```text
projeto-turismo-simples/
├── api/
│   ├── src/main/java/school/sptech/
│   │   ├── TurismoApiApplication.java
│   │   ├── Destino.java
│   │   ├── DestinoController.java
│   │   └── DestinoRepository.java
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── script.sql
│   └── pom.xml
├── cliente/
│   ├── src/
│   ├── package.json
│   └── vite.config.js
└── README.md
```

