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
docker compose build
docker compose up
```
# Documentação Swagger

É possivel acessar os endpoints disponiveis para visualização no seguinte endereço, depois que o container estiver rodando

http://localhost:8080/swagger-ui/index.html

Depois, na sessão 'Autenticação', executar a operação de Login, e com o token, utlizar no botão 'Authorize' no canto superior direito
Colar o token no campo 'Value', clica em 'Authorize' e depois 'Close'
A partir desse momento, qualquer operação da aplicação estará autenticada e poderá ser executada

# Executando e testando com Postman

Existe um arquivo postman 'Contas de Pagamento.postman_collection.json' na raiz do projeto com todas as operações disponiveis
É necessario autenticar com o usuario admin que ja esta configurado no ambiente, pegar o token e setar nas demais requisições como Bearer
Tambem tem um arquivo CSV de exemplo para requisição de importação chamado contas.csv

# Arquivos Uteis Na raiz

- Contas de Pagamento.postman_collection.json
- contas.csv

# cURLs de exemplo

- Login (Utilizar o token gerado no campo header Authorization substituindo ••••••)
```bash
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "login": "admin",
    "password": "admin123"
}'
```
- Cadastrar Conta
```bash
curl --location 'http://localhost:8080/conta' \
--header 'Content-Type: application/json' \
--header 'Authorization: ••••••' \
--data '{
    "dataVencimento": "2024-08-19",
    "dataPagamento": "2024-08-15",
    "valor": 125.58,
    "descricao": "descricao",
    "situacao": "a vencer"
}'
```

- Obter Contas
```bash
curl --location 'http://localhost:8080/conta?page=0&size=5' \
--header 'Accept: application/json' \
--header 'Authorization: ••••••'
```