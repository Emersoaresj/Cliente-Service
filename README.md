# Cliente Service

Serviço Java baseado em Spring Boot para gerenciamento de clientes, utilizando PostgreSQL, Flyway para migrações e arquitetura hexagonal (Ports & Adapters).

---

## 🏗️ Arquitetura

O projeto segue o padrão de arquitetura hexagonal, separando as regras de negócio (domínio) das implementações externas (gateways/adapters):

- **API**: Endpoints REST para operações de clientes.
- **Domain**: Modelos, exceções e portas (interfaces) do domínio.
- **Gateway**: Implementações de acesso a dados (JPA).
- **Service**: Lógica de negócio central.
- **Config/Utils**: Configurações e utilitários.

---

## 📁 Estrutura de Pastas

```
src/main/java/com/fiap/postech/cliente_service/
├── api/
│   ├── controller/
│   │   └── ClienteController.java
│   ├── dto/
│   │   ├── AtualizaClienteRequest.java
│   │   ├── CadastraClienteRequest.java
│   │   ├── ClienteDto.java
│   │   └── ResponseDto.java
│   └── mapper/
│       └── ClienteMapper.java
├── config/
│   └── SwaggerConfig.java
├── domain/
│   ├── exceptions/
│   │   ├── ErroInternoException.java
│   │   ├── GlobalHandlerException.java
│   │   └── internal/
│   │       ├── ClienteExistsException.java
│   │       ├── ClienteNotFoundException.java
│   │       ├── InvalidCepException.java
│   │       ├── InvalidCpfException.java
│   │       ├── InvalidDataNascimentoException.java
│   │       └── InvalidEstadoException.java
│   └── model/
│       └── Cliente.java
├── gateway/
│   └── database/
│       ├── ClienteRepositoryImpl.java
│       ├── entity/
│       │   └── ClienteEntity.java
│       └── repository/
│           └── ClienteRepositoryJPA.java
├── port/
│   ├── ClienteRepositoryPort.java
│   └── ClienteServicePort.java
├── service/
│   └── ClienteServiceImpl.java
├── utils/
│   └── ConstantUtils.java
└── ClienteServiceApplication.java
```
---

## 🧩 Principais Classes

- **ClienteController**: Endpoints REST para cadastro, atualização, busca e remoção de clientes.
- **ClienteServiceImpl**: Implementação da lógica de negócio.
- **ClienteRepositoryPort**: Interface para chamada de endpoints na implementação de persistência dos clientes.
- **ClienteRepositoryImpl**: Implementação do repositório usando JPA.
- **ClienteEntity**: Entidade JPA para persistência.
- **Cliente**: Modelo de domínio.
- **DTOs**: Objetos para transferência de dados entre camadas.
- **Exceções**: Tratamento centralizado de erros e validações.

---

## ⚙️ Configuração

O arquivo `src/main/resources/application.yml` define:

- Conexão com PostgreSQL (ajustável por variáveis de ambiente).
- Flyway para migrações automáticas.
- JPA configurado para atualização automática do schema e exibição de SQL.

---

## ▶️ Executando o Projeto

1. Configure o banco PostgreSQL e ajuste as variáveis de ambiente se necessário.
2. Execute as migrações Flyway automaticamente ao iniciar a aplicação.
3. Rode o projeto com: mvn spring-boot:run
4. A aplicação estará disponível em http://localhost:8080.

---

## 📚 Documentação
A documentação dos endpoints pode ser acessada via Swagger em /swagger-ui.html (caso habilitado).
