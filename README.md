# Bills Manager

`Microserviço responsável pelo gerenciamento de contas a pagar`

# Pré Requisitos

Para que seja possível rodar essa aplicação é necessário atender alguns requisitos básicos.

- JDK 17 LTS
- Gradle 8.8+
- PostgreSQL 14+

# Compilando para IDEAs como IntelliJ

Assim como todo projeto *Gradle*, é necessário primeiramente realizarmos a geração dos fontes. Conforme o exemplo abaixo:

```bash
./gradlew clean build
```

# Compilando e inicializando com Docker

Executar os comandos docker abaixo

```bash
docker-compose build
docker-compose up
```

# Executando e testando

Existe um arquivo postman 'Contas de Pagamento.postman_collection.json' na raiz do projeto com todas as operações disponiveis
É necessario autenticar com o usuario admin que ja esta configurado no ambiente, pegar o token e setar nas demais requisições como Bearer
Tambem tem um arquivo CSV de exemplo para requisição de importação chamado contas.csv

# Arquivos Uteis Na raiz

- Contas de Pagamento.postman_collection.json
- contas.csv

# cURLs de exemplo

```bash
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "login": "admin",
    "password": "admin123"
}'
```

```bash
curl --location 'http://localhost:8080/conta' \
--header 'Content-Type: application/json' \
--header 'Authorization: ••••••' \
--data '{
    "dataVencimento": "2024-06-19",
    "dataPagamento": "2024-06-15",
    "valor": 125.58,
    "descricao": "descricao feita no docker 2",
    "situacao": "a vencer"
}'
```
