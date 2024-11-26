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
