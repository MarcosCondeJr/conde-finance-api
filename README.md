# 💰 CondeFinance - API de Gestão Financeira

**CondeFinance** é uma API REST desenvolvida com Spring Boot, voltada para gestão financeira pessoal.
O sistema permite controle completo de contas, categorias e transações (receitas e despesas), aplicando boas práticas de arquitetura e regras consistentes de domínio financeiro.

---

## 🎯 Objetivos do Projeto

* Consolidar conhecimentos em Spring Boot e arquitetura em camadas
* Aplicar boas práticas de modelagem financeira
* Implementar regras robustas de validação de saldo
* Trabalhar com JPA, DTOs, Mappers e tratamento global de exceções

---

## 🧠 Tecnologias Aplicadas

* Java 17+
* Spring Boot 4
* Spring Data JPA
* Spring Security
* PostgreSQL
* Hibernate
* Bean Validation (Jakarta Validation)
* Maven
* Flyway

---

## 🏗️ Estrutura do Projeto

O projeto segue arquitetura em camadas:

```controller → service → repository → entity```

```text
src/main/java/com/marcoscondejr/conde_finance_api
 ├── config/          # Configurações globais
 ├── controller/      # Endpoints REST
 ├── dto/             # Objetos de Request e Response
 ├── entity/          # Entidades JPA
 ├── enums/           # Enumerações do domínio
 ├── exception/       # Exceptions customizadas e handler global
 ├── infra/security/  # Configuração de segurança (JWT, filtros, etc.)
 ├── mapper/          # Conversores Entity ↔ DTO
 ├── repository/      # Interfaces JPA
 ├── service/         # Regras de negócio
 └── CondeFinanceApiApplication.java
```

## 💳 Funcionalidades

* Cadastro de usuários
* Gerenciamento de contas
* Cadastro de categorias (Receita / Despesa)
* Lançamentos financeiros (Transactions)
* Validação automática de saldo
* Regras consistentes de atualização de saldo
* Segurança com autenticação

## 💡 Regras de Negócio Importantes

* O valor (amount) é sempre armazenado como positivo.
* O impacto no saldo é calculado com base no transactionType.
* Não é permitido que o saldo da conta fique negativo.
* A edição de transações recalcula corretamente a diferença de saldo.

## 🚀 Como Executar o Projeto

### 1️⃣ Clonar o repositório

```
git clone https://github.com/marcoscondejr/conde-finance-api.git
```

### 2️⃣ Configurar o banco de dados

Crie um banco PostgreSQL e configure o application.yml:

```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/conde_finance
    username: postgres
    password: sua_senha
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 3️⃣ Rodar a aplicação

```
mvn spring-boot:run
```

Ou pela sua IDE executando:

```
CondeFinanceApiApplication
```

## 📚 Próximos Passos

* Implementar paginação e filtros avançados
* Adicionar relatórios financeiros
* Criar testes automatizados (JUnit + Mockito)
