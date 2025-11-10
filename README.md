# 🛒 Foursales - Sistema de E-commerce

## 📋 Sobre o Projeto

Foursales é uma API REST desenvolvida em Spring Boot para gerenciamento de e-commerce, oferecendo funcionalidades completas de autenticação, gerenciamento de produtos, pedidos e análises de vendas. O sistema utiliza autenticação JWT (JSON Web Token) e implementa controle de acesso baseado em roles (ADMIN e USER).

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Security** (OAuth2 Resource Server com JWT)
- **Spring Data JPA**
- **MySQL 8.0**
- **Maven**
- **Lombok**
- **ModelMapper**
- **Docker & Docker Compose**

## 📦 Estrutura do Projeto

O projeto segue uma arquitetura em camadas:

```
src/main/java/br/com/thomas/foursales/
├── application/          # Serviços de aplicação
│   ├── payment/         # Gateway de pagamento
│   └── service/         # Implementações de serviços
├── domain/              # Camada de domínio
│   ├── dto/            # Data Transfer Objects
│   ├── entity/         # Entidades JPA
│   ├── enums/          # Enumerações
│   ├── repository/     # Repositórios
│   ├── request/        # Objetos de requisição
│   └── response/       # Objetos de resposta
└── infrastructure/      # Camada de infraestrutura
    ├── configuration/  # Configurações
    ├── controller/     # Controllers REST
    ├── exception/      # Tratamento de exceções
    ├── mapper/         # Conversores
    ├── security/       # Configurações de segurança
    └── service/        # Interfaces de serviço
```

## 🐳 Como Rodar com Docker Compose

### Pré-requisitos
- Docker instalado
- Docker Compose instalado

### Passo 1: Subir o banco de dados MySQL

Navegue até o diretório `docker` e execute o comando:

```bash
cd docker
docker-compose up -d
```

Isso irá:
- Criar um container MySQL 8.0
- Expor a porta 3306
- Criar o banco de dados `foursalesdb`
- Configurar usuário `root` com senha `pass`

### Passo 2: Executar a aplicação

Volte para o diretório raiz do projeto e execute:

```bash
cd ..
./mvnw spring-boot:run
```

Ou, se estiver no Windows:

```bash
mvnw.cmd spring-boot:run
```

Isso irá criar todas as tabelas de entidades de banco de dados, conforme está 
no arquivo `src/main/resources/schema.sql` e também, vai fazer o insert de dados conforme comandos SQL 
no arquivo `src/main/resources/data.sql`.

A aplicação estará disponível em: `http://localhost:8080`

### Passo 3: Parar o Docker Compose

Para parar o container do MySQL:

```bash
cd docker
docker-compose down
```

## 👥 Credenciais de Acesso

O sistema vem com usuários pré-cadastrados para testes:

### 👨‍💼 Usuário ADMIN
- **Username:** `admin`
- **Password:** `adminpass`
- **Email:** `admin@gmail.com`
- **Role:** `ADMIN`

### 👤 Usuário USER
- **Username:** `user1`
- **Password:** `user1`
- **Email:** `user1@gmail.com`
- **Role:** `USER`

> **Nota:** Existem outros usuários USER disponíveis (user2, user3, user4) com a senha sendo igual ao username de cada um.

## 🔐 Autenticação

### Obter Token JWT

**Endpoint:** `POST /auth/authenticate`

**Autenticação:** HTTP Basic Authentication

**Exemplo de requisição:**

```bash
curl -X POST http://localhost:8080/auth/authenticate \
  -u admin:adminpass
```

Ou com user1:

```bash
curl -X POST http://localhost:8080/auth/authenticate \
  -u user1:user1
```

**Resposta:**

```json
{
  "token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 1800
}
```

**Uso do Token:**

Para as próximas requisições autenticadas, inclua o token no header:

```
Authorization: Bearer {seu_token_aqui}
```

## 📚 Endpoints da API

### 🔓 Endpoints Públicos (Sem Autenticação)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/auth/authenticate` | Autenticação e geração de token JWT |
| GET | `/users/purchases` | Lista usuários com mais compras (paginado) |
| GET | `/users/ticket-average` | Lista ticket médio por usuário |
| GET | `/products/{id}` | Busca produto por ID |

### 👨‍💼 Endpoints ADMIN (Apenas administradores)

#### Produtos

| Método | Endpoint | Descrição | Permissão |
|--------|----------|-----------|-----------|
| POST | `/products/` | Criar novo produto | ADMIN |
| PUT | `/products/{id}` | Atualizar produto | ADMIN |
| DELETE | `/products/{id}` | Deletar produto | ADMIN |

**Exemplo - Criar Produto:**

```bash
curl -X POST http://localhost:8080/products/ \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Notebook Gamer",
    "description": "Notebook para jogos com RTX 4060",
    "price": 5500.00,
    "category": "eletronico",
    "qtStock": 5
  }'
```

**Resposta:**

```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "name": "Notebook Gamer",
  "description": "Notebook para jogos com RTX 4060",
  "price": 5500.00,
  "category": "eletronico",
  "qtStock": 5,
  "createdAt": "2025-11-10T10:30:00",
  "updatedAt": null
}
```

**Exemplo - Atualizar Produto:**

```bash
curl -X PUT http://localhost:8080/products/{id} \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Notebook Gamer Pro",
    "description": "Notebook para jogos com RTX 4070",
    "price": 6500.00,
    "category": "eletronico",
    "qtStock": 3
  }'
```

**Exemplo - Deletar Produto:**

```bash
curl -X DELETE http://localhost:8080/products/{id} \
  -H "Authorization: Bearer {token}"
```

### 👤 Endpoints USER e ADMIN (Usuários autenticados)

#### Pedidos

| Método | Endpoint | Descrição | Permissão |
|--------|----------|-----------|-----------|
| POST | `/orders/user/{username}` | Criar novo pedido | USER, ADMIN |
| GET | `/orders/user/{username}` | Listar pedidos do usuário | USER, ADMIN |
| PUT | `/orders/order/{order-id}/payment` | Realizar pagamento de pedido | USER, ADMIN |
| GET | `/orders/monthly-revenue` | Consultar receita mensal | USER, ADMIN |

**Exemplo - Criar Pedido:**

```bash
curl -X POST http://localhost:8080/orders/user/user1 \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '[
    {
      "productId": "c8b3a9e5-6f24-4c9e-a74f-08b0fd25a1d1",
      "qtProduct": 2
    },
    {
      "productId": "d2f5b1cc-1b93-4b9e-8b8a-3b6c53d6a1a2",
      "qtProduct": 1
    }
  ]'
```

**Resposta:**

```json
{
  "id": 10,
  "user": {
    "id": 2,
    "username": "user1",
    "email": "user1@gmail.com",
    "role": "USER"
  },
  "items": [
    {
      "id": 1,
      "product": {
        "id": "c8b3a9e5-6f24-4c9e-a74f-08b0fd25a1d1",
        "name": "Teclado Mecânico",
        "price": 300.00
      },
      "quantity": 2,
      "subtotal": 600.00
    },
    {
      "id": 2,
      "product": {
        "id": "d2f5b1cc-1b93-4b9e-8b8a-3b6c53d6a1a2",
        "name": "Mouse Gamer",
        "price": 200.00
      },
      "quantity": 1,
      "subtotal": 200.00
    }
  ],
  "status": "PENDENTE",
  "totalValue": 800.00,
  "createdAt": "2025-11-10T15:30:00",
  "updatedAt": null
}
```

**Exemplo - Listar Pedidos do Usuário:**

```bash
curl -X GET http://localhost:8080/orders/user/user1 \
  -H "Authorization: Bearer {token}"
```

**Exemplo - Realizar Pagamento:**

```bash
curl -X PUT http://localhost:8080/orders/order/1/payment \
  -H "Authorization: Bearer {token}"
```

**Exemplo - Consultar Receita Mensal:**

```bash
# Receita do mês atual
curl -X GET http://localhost:8080/orders/monthly-revenue \
  -H "Authorization: Bearer {token}"

# Receita de um mês específico
curl -X GET "http://localhost:8080/orders/monthly-revenue?month=11&year=2025&orderStatus=PAGO" \
  -H "Authorization: Bearer {token}"
```

**Resposta:**

```json
15000.00
```

**Parâmetros de query:**
- `month` (opcional): Mês (1-12)
- `year` (opcional): Ano (ex: 2025)
- `orderStatus` (opcional, padrão: PAGO): Status do pedido (PENDENTE, PAGO, CANCELADO)

#### Usuários

| Método | Endpoint | Descrição | Permissão |
|--------|----------|-----------|-----------|
| GET | `/users/purchases` | Top usuários com mais compras | Público |
| GET | `/users/ticket-average` | Ticket médio por usuário | Público |

**Exemplo - Top Usuários (Paginado):**

```bash
curl -X GET "http://localhost:8080/users/purchases?page=0&size=5" \
  -H "Authorization: Bearer {token}"
```

**Resposta:**

```json
{
  "content": [
    {
      "userId": 1,
      "username": "admin",
      "email": "admin@gmail.com",
      "totalPurchases": 5,
      "totalSpent": 3800.00
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5
  },
  "totalElements": 1,
  "totalPages": 1
}
```

**Exemplo - Ticket Médio:**

```bash
curl -X GET http://localhost:8080/users/ticket-average \
  -H "Authorization: Bearer {token}"
```

**Resposta:**

```json
[
  {
    "userId": 1,
    "username": "admin",
    "email": "admin@gmail.com",
    "averageTicket": 760.00
  }
]
```

## 🔒 Resumo de Permissões por Role

### 🔓 Público (Sem Autenticação)
- ✅ Autenticação (`POST /auth/authenticate`)
- ✅ Consultar produto específico (`GET /products/{id}`)
- ✅ Consultar estatísticas de usuários (`GET /users/**`)

### 👤 USER (Usuários autenticados)
- ✅ Todos os endpoints de **Pedidos** (`/orders/**`)
- ❌ Gerenciamento de produtos (criar, atualizar, deletar)

### 👨‍💼 ADMIN (Administradores)
- ✅ Todos os endpoints de **Pedidos** (`/orders/**`)
- ✅ Criar produtos (`POST /products/`)
- ✅ Atualizar produtos (`PUT /products/{id}`)
- ✅ Deletar produtos (`DELETE /products/{id}`)

## 🗄️ Banco de Dados

### Produtos Pré-cadastrados

| ID | Nome | Preço | Estoque |
|----|------|-------|---------|
| c8b3a9e5-6f24-4c9e-a74f-08b0fd25a1d1 | Teclado Mecânico | R$ 300,00 | 10 |
| d2f5b1cc-1b93-4b9e-8b8a-3b6c53d6a1a2 | Mouse Gamer | R$ 200,00 | 15 |
| a1c9e4f7-13b9-44e4-bba9-04a83b22c6d3 | Monitor 27" | R$ 800,00 | 8 |
| f6a2d57b-4dc9-4f2a-94c4-4d5cb2a45c8e | Headset Gamer | R$ 400,00 | 12 |

### Status de Pedidos

- `PENDENTE`: Pedido criado, aguardando pagamento
- `PAGO`: Pedido pago com sucesso
- `CANCELADO`: Pedido cancelado

## ⚙️ Configuração

### application.properties

```properties
spring.application.name=foursales
spring.datasource.url=jdbc:mysql://localhost:3306/foursalesdb
spring.datasource.username=root
spring.datasource.password=pass

# JWT Configuration
jwt.expiration=1800  # 30 minutos
```

## 🧪 Testes

Para executar os testes unitarios:

```bash
./mvnw test
```

## 📝 Notas Importantes

1. **Tokens JWT expiram em 30 minutos** (1800 segundos). Após esse período, é necessário gerar um novo token.

2. **Autenticação Basic** é usada apenas no endpoint `/auth/authenticate`. Todos os outros endpoints requerem **Bearer Token**.

3. **Controle de Estoque**: Ao criar um pedido, o sistema verifica automaticamente a disponibilidade de estoque dos produtos.

4. **Gateway de Pagamento**: O sistema simula um gateway de pagamento ao processar pedidos.

5. **Paginação**: O endpoint `/users/purchases` suporta paginação com os parâmetros `page`, `size` e `sort`.

## Autor

**Thomas Pauletti**
- Case Técnico - Foursales


