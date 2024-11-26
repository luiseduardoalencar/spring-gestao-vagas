# Plataforma de Gestão de Vagas - Backend

Este é o backend de uma plataforma de gestão de vagas, desenvolvido em Spring Boot. A aplicação utiliza PostgreSQL como banco de dados, Prometheus e Grafana para monitoramento, e SonarQube para análise de código.

## Pré-requisitos

Certifique-se de que os seguintes softwares estejam instalados na sua máquina:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [PostgreSQL](https://www.postgresql.org/)
- [Insomnia](https://insomnia.rest/)

---

## Configuração

### Configuração do `application.properties`

Preencha o arquivo `application.properties` com as informações do banco de dados e outras variáveis necessárias:

```properties
# Configuração da aplicação
spring.application.name=gestao_vagas
spring.devtools.livereload.enabled=true
spring.devtools.restart.enabled=true

# Configuração do banco de dados
spring.datasource.url=jdbc:postgresql://<HOST>:<PORT>/<DATABASE>
spring.datasource.username=<USUARIO>
spring.datasource.password=<SENHA>
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update

# Segurança
security.token.secret=INSIRA_AQUI_UMA_CHAVE_SECRETA
security.token.secret.candidate=INSIRA_AQUI_UMA_CHAVE_SECRETA
token.sonar=<TOKEN_DO_SONARQUBE>

# Endpoints de monitoramento
management.endpoints.web.exposure.include=health,prometheus,metrics
management.endpoints.enabled-by-default=false
management.endpoint.metrics.enabled=true
management.endpoint.health.enabled=true
management.endpoint.health.show-details=always
management.endpoint.prometheus.enabled=true
```

### Variáveis do banco de dados

Substitua `<HOST>`, `<PORT>`, `<DATABASE>`, `<USUARIO>` e `<SENHA>` com as configurações do seu banco de dados PostgreSQL.

---

## Inicialização

1. Clone o repositório:
   ```bash
   git clone https://github.com/luiseduardoalencar/spring-gestao-vagas
   cd spring-gestao-vagas
   ```

2. Suba os serviços do Docker (Prometheus e Grafana):
   ```bash
   docker-compose up -d
   ```

3. Compile e inicie a aplicação Spring Boot:
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

4. Acesse o Swagger para testar os endpoints da aplicação:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```


## Monitoramento

- Acesse o Prometheus:
  ```
  http://localhost:9090
  ```

- Acesse o Grafana:
  ```
  http://localhost:3000
  ```
  Login padrão:
  - Usuário: `admin`
  - Senha: `admin` (alterar após o primeiro login)

---

## SonarQube

- Certifique-se de que o SonarQube está rodando no seu ambiente local.
- Utilize o token configurado no `application.properties` para análise.

Execute a análise do código com o comando:
```bash
./mvnw sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=<TOKEN_SONARQUBE>
```
---

## Integração e Deploy Contínuos (CI/CD) com GitHub Actions

A aplicação conta com um workflow automatizado de build e deploy utilizando o **GitHub Actions**, configurado no arquivo `.github/workflows/prod.yml`. Esse arquivo define um pipeline que executa os seguintes passos ao realizar um push para o branch `main`:

#### Estrutura do Workflow

1. **Eventos Disparadores**
   - O workflow é executado automaticamente toda vez que há um `push` para o branch `main`.

2. **Jobs Definidos**
   - O workflow possui dois jobs principais: `build` e `deploy`.

#### Job: Build

O job `build` é responsável por compilar a aplicação e preparar a imagem Docker. Ele utiliza a máquina virtual do GitHub para executar os seguintes passos:

- **Checkout do Código**
  - Obtém o código da aplicação do repositório.

- **Configuração do Ambiente Java**
  - Configura o ambiente com Java 17, utilizando o Temurin como distribuição.

- **Compilação da Aplicação**
  - Realiza o build da aplicação com o Maven, garantindo que a aplicação está pronta para ser empacotada.

- **Autenticação no Docker Hub**
  - Faz login no Docker Hub utilizando credenciais armazenadas como secrets no repositório.

- **Build da Imagem Docker**
  - Constrói a imagem Docker da aplicação, com a tag correspondente ao repositório no Docker Hub.

- **Publicação da Imagem**
  - Envia a imagem construída para o Docker Hub.

#### Job: Deploy

O job `deploy` depende do `build` para ser executado. Ele realiza o deploy da aplicação utilizando um runner self-hosted (um servidor próprio configurado para rodar ações do GitHub). Os passos executados são:

- **Download da Imagem Docker**
  - Faz o pull da imagem mais recente da aplicação a partir do Docker Hub.

- **Remoção do Container Anterior**
  - Remove o container existente para garantir que não há conflitos.

- **Início do Novo Container**
  - Inicia um novo container da aplicação, configurando as variáveis de ambiente para conexão com o banco de dados.

#### Variáveis de Ambiente

O workflow utiliza secrets do GitHub para armazenar informações sensíveis, como:
- `DOCKER_USERNAME` e `DOCKER_PASSWORD` (credenciais do Docker Hub)
- `DATABASE_URL`, `DATABASE_USERNAME` e `DATABASE_PASSWORD` (informações do banco de dados)

Essas variáveis são injetadas no ambiente durante o build e deploy, garantindo segurança e flexibilidade.

#### Benefícios

1. **Automação Total**
   - Ao realizar um `git push` para o branch `main`, o pipeline cuida de todo o processo de build e deploy automaticamente.

2. **Confiabilidade**
   - Cada etapa do workflow é isolada e validada, minimizando erros manuais durante o deploy.

3. **Escalabilidade**
   - A utilização do Docker garante que a aplicação seja executada de forma consistente em qualquer ambiente.

4. **Segurança**
   - Secrets armazenados no repositório GitHub garantem que informações sensíveis não sejam expostas.

#### Como Usar

1. Faça commit e push das alterações para o branch `main`:
   ```bash
   git add .
   git commit -m "Descrição do commit"
   git push origin main
   ```

2. O pipeline será disparado automaticamente, e você poderá acompanhar a execução no GitHub Actions.

3. Após a conclusão, a aplicação estará disponível no ambiente configurado.

Com essa abordagem, você garante agilidade no desenvolvimento, reduzindo o tempo necessário para colocar mudanças em produção!

