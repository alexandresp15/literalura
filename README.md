# 📚 LiterAlura - Catálogo de Livros

Projeto desenvolvido como parte do **Challenge Back-End Java da Alura**.

A aplicação consome dados de uma API pública de livros, armazena essas informações em um banco de dados PostgreSQL e permite realizar consultas através de um **menu interativo no console**.

---

# 🚀 Funcionalidades

A aplicação permite:

1. 🔎 **Buscar livro pelo título** através da API Gutendex
2. 📖 **Listar livros registrados** no banco de dados
3. 👨‍🏫 **Listar autores registrados**
4. 📅 **Listar autores vivos em determinado ano**
5. 🌎 **Listar livros por idioma**
6. 🔝 **Listar os Top 10 livros mais baixados**

---

# 🛠 Tecnologias Utilizadas

* **Java 17**
* **Spring Boot**
* **Spring Data JPA**
* **PostgreSQL**
* **Maven**
* **API Gutendex (Projeto Gutenberg)**

---

# 🌐 API utilizada

A aplicação consome dados da API pública:

https://gutendex.com/

Exemplo de requisição:

https://gutendex.com/books/?search=dom+casmurro

---

# ⚙️ Como executar o projeto

### 1️⃣ Clonar o repositório

```bash
git clone https://github.com/seu-usuario/literalura.git
```

---

### 2️⃣ Configurar banco de dados PostgreSQL

Criar banco:

```sql
CREATE DATABASE literalura;
```

Configurar no arquivo:

```
src/main/resources/application.properties
```

Exemplo:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=postgres
```

---

### 3️⃣ Executar a aplicação

Rodar pelo Maven:

```bash
mvn spring-boot:run
```

Ou executar a classe:

```
LiteraluraApplication.java
```

---

# 🖥 Exemplo de uso

Menu exibido no console:

```
-------- LiterAlura --------
1 - Buscar livro pelo título
2 - Listar livros registrados
3 - Listar autores registrados
4 - Listar autores vivos em determinado ano
5 - Listar livros por idioma
6 - Listar Top 10 livros mais baixados
0 - Sair
```

---

# 📂 Estrutura do Projeto

```
literalura
│
├── model
├── repository
├── service
├── principal
└── LiteraluraApplication
```

---

# 👨‍💻 Autor

Projeto desenvolvido por **Alexandre Pereira** durante o **Challenge Java da Alura**.
