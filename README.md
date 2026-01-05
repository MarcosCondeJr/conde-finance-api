# 💰 CondeFinance - API de Gestão Financeira

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![RestAPI](https://img.shields.io/badge/Rest%20API-005571?style=for-the-badge&logo=rest&logoColor=white)

**CondeFinance** é uma API REST robusta desenvolvida em **Java puro**, focada na aplicação de conceitos avançados da linguagem. O projeto foi arquitetado para ser uma base sólida e escalável, facilitando uma migração futura para frameworks como Spring Boot.

O sistema oferece controle financeiro pessoal completo, permitindo a gestão de usuários, categorias e lançamentos (receitas e despesas).

---

## 🎯 Objetivos do Projeto

* **Consolidar fundamentos:** Praticar Java 17+ sem dependências de frameworks de alto nível.
* **Arquitetura Limpa:** Construir uma API REST do zero utilizando `com.sun.net.httpserver`.
* **Domínio Técnico:** Aplicar padrões de projeto (SOLID), Generics para reaproveitamento de código e Streams para manipulação de dados.
* **Persistência Real:** Implementar uma camada de acesso a dados (DAO) manual utilizando JDBC.

---

## 🧠 Conceitos e Tecnologias Aplicadas

* **POO & SOLID:** Princípios de responsabilidade única e inversão de dependência.
* **Generics:** Repositórios genéricos para operações CRUD.
* **Streams & Lambdas:** Processamento eficiente de coleções e filtragem de dados.
* **JDBC:** Comunicação direta com o banco de dados via SQL.
* **API REST:** Manipulação de verbos HTTP, status codes e JSON.
* **Arquitetura em Camadas:** Separação clara entre Controller, Service, Repository e DTO.

---

## 🏗️ Estrutura do Projeto

```text
src/
 ├── config/      # Configurações de banco e servidor
 ├── controller/  # Handlers HTTP (Endpoints)
 ├── dto/         # Objetos de transferência (Request/Response)
 ├── model/       # Entidades de domínio
 ├── repository/  # Camada de persistência (Interfaces e JDBC)
 ├── service/     # Regras de negócio e lógica de Streams
 ├── exception/   # Tratamento de erros customizados
 ├── util/        # Classes utilitárias (Conexão, Formatação)
 ├── server/      # Inicialização do servidor HTTP
 └── Main.java    # Ponto de entrada
