# Estudo de ferramentas para monitoramento, testes, qualidade e CI/CD - Gestão de Vagas - Backend

Esta é uma aplicação backend para a gestão de vagas, desenvolvida com o foco na **integração de ferramentas modernas de monitoramento, análise de qualidade de código, identificação de erros**, e automação de processos de build e deploy. 

---

## 🛠️ Tecnologias Utilizadas

- **Spring Boot**: Framework principal para o desenvolvimento do backend.
- **PostgreSQL**: Banco de dados relacional.
- **SonarQube**: Ferramenta de análise de qualidade de código e identificação de problemas.
- **Prometheus e Grafana**: Soluções para monitoramento e visualização de métricas em tempo real.
- **Docker e Docker Compose**: Para containerização e gerenciamento de serviços.
- **GitHub Actions**: Pipeline CI/CD para automação de build e deploy.

---

## 🌟 Destaques do Projeto

1. **Documentação da API**: Utilizando Swagger, a API possui endpoints documentados de forma clara, facilitando os testes e a integração.  
   ![Swagger UI](https://github.com/user-attachments/assets/37367e4a-a35d-4350-9161-d8a84b2a62bf)
   ![Teste de criação de Candidato](https://github.com/user-attachments/assets/dab1422f-ad70-42b3-b47d-62e9af97435e)

2. **Monitoramento**: A aplicação está integrada com Prometheus e Grafana para monitoramento em tempo real. Métricas como uso de CPU, tempo de atividade e requisições HTTP são capturadas e exibidas em painéis interativos.  


4. **Análise de Qualidade de Código**: O SonarQube analisa o código, detectando bugs, problemas de segurança e sugerindo melhorias. O dashboard fornece relatórios completos de qualidade.  


5. **Testes Automatizados**: A aplicação foi desenvolvida com suporte a testes unitários e de integração. Endpoints como o de cadastro de candidatos foram implementados com validações detalhadas.  


6. **Pipeline CI/CD Automatizado**: Com o GitHub Actions, todo o processo de build, testes, e deploy ocorre automaticamente após um push para o branch `main`. Isso reduz erros manuais e acelera o ciclo de entrega.  


---

## 🚀 Como Executar a Aplicação

### Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:
- [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/)

### Configuração do Banco de Dados

Crie um banco de dados PostgreSQL com as informações de conexão que serão usadas no arquivo `application.properties`.

### Configuração do `application.properties`

Preencha o arquivo `src/main/resources/application.properties` com as seguintes informações:

```properties
# Configuração da aplicação
spring.application.name=gestao_vagas

# Configuração do banco de dados
spring.datasource.url=jdbc:postgresql://<HOST>:<PORT>/<DATABASE>
spring.datasource.username=<USUARIO>
spring.datasource.password=<SENHA>
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update

# Segurança
security.token.secret=<CHAVE_SECRETA>
token.sonar=<TOKEN_SONARQUBE>

# Endpoints de monitoramento
management.endpoints.web.exposure.include=health,prometheus,metrics
management.endpoints.enabled-by-default=false
management.endpoint.metrics.enabled=true
management.endpoint.health.enabled=true
management.endpoint.health.show-details=always
management.endpoint.prometheus.enabled=true
```

Substitua `<HOST>`, `<PORT>`, `<DATABASE>`, `<USUARIO>`, `<SENHA>`, `<CHAVE_SECRETA>` e `<TOKEN_SONARQUBE>` pelos valores do seu ambiente.

---

### Inicialização

1. Suba os serviços de monitoramento com Docker Compose:
   ```bash
   docker-compose up -d
   ```

2. Compile e execute a aplicação com Maven:
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

3. Acesse o Swagger para testar os endpoints:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

---

## 🔍 Execução da Análise de Qualidade de Código com SonarQube

1. Certifique-se de que o SonarQube está em execução:
   - Acesse `http://localhost:9000` e verifique se o serviço está ativo.

2. Gere um token no painel do SonarQube e configure-o no `application.properties` ou passe-o diretamente no comando abaixo.

3. Execute a análise com Maven:
   ```bash
   mvn clean verify sonar:sonar \
   -Dsonar.projectKey=gestao_vagas \
   -Dsonar.host.url=http://localhost:9000 \
   -Dsonar.login=<SEU_TOKEN_SONARQUBE>
   ```

4. Acompanhe o relatório no painel do SonarQube, em `http://localhost:9000/dashboard?id=gestao_vagas`.

![SonarQube Dashboard](https://github.com/user-attachments/assets/5fcdf466-f19f-46d9-872b-208e72ce9514)

---

## 📊 Monitoramento

- Acesse o painel do **Prometheus** para visualizar métricas:
  ```
  http://localhost:9090
  ```

- Acesse o painel do **Grafana** para gráficos e visualizações:
  ```
  http://localhost:3000
  ```

  Credenciais padrão:
  - Usuário: `admin`
  - Senha: `admin` (alterar após o primeiro login)

![Painel Grafana](https://github.com/user-attachments/assets/5195002e-8c9a-450f-b4e5-eed843f1c158)

---

## 🚀 Pipeline CI/CD

O projeto possui um workflow automatizado de **CI/CD** configurado no GitHub Actions. Toda vez que um `git push` é realizado no branch `main`, o pipeline executa:

1. **Build e Testes**: Compilação da aplicação e execução dos testes automatizados.
2. **Containerização**: Criação de uma imagem Docker e upload para o Docker Hub.
3. **Deploy Automatizado**: Início de um novo container no ambiente de produção.

![Git Actions](https://github.com/user-attachments/assets/5a2c7826-c0be-45ad-ae29-26f1c35d00f5)
---

## 🤝 Contribuindo

Fique à vontade para abrir **issues** ou enviar **pull requests** no repositório para melhorias e sugestões. Feedbacks são sempre bem-vindos!

---
