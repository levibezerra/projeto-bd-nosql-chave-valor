# 🎵 Projeto Preferências Musicais

### Este é um projeto web desenvolvido com **Spring Boot** que permite que usuários se cadastrem, façam login e salvem suas **preferências musicais** (nome, gênero musical favorito e volume). As informações são armazenadas usando o banco de dados **LevelDB**, um banco leve e eficiente baseado em chave-valor.

## 📌 Funcionalidades

- **Cadastro e login de usuários.**
- **Salvar preferências musicais por usuário.**
- **Listar preferências salvas do usuário logado.**
- **Listar todos usuários.**
- **Deletar usuário pelo ID.**
- **Deletar preferências específicas.**
- **Armazenamento dos dados no banco **LevelDB**, criado localmente.**

## 💾 Tecnologias Usadas

- **Java 21+.**
- **Spring Boot.**
- **Maven → gerenciamento de dependências.**
- **LevelDB (com `org.iq80.leveldb`).**
- **HTML, CSS e JavaScript (frontend puro).**
- **Postman (testes de API).**

## ⚙️ Como Executar

### Pré-requisitos:
- **Java JDK 21+.**
- **Maven.**

### Passos:

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/preferencias-musicais.git

# Execute o projeto
./mvnw spring-boot:run 
```

# 🔌 Endpoints disponíveis

- **POST /api/usuario - Cadastrar novo usuário.**
- **POST /api/login - Autenticar usuário.**
- **POST /api/preferencia/{usuarioId} - Salvar nova preferência.**
- **GET /api/preferencia/{usuarioId} - Listar preferências do usuário.**
- **DELETE /api/preferencia/{usuarioId}/{id} - Deletar preferência pelo ID.**
- **GET /api/usuarios - Listar todos usuários.**
- **DELETE /api/usuario/{id} - Deletar usuário pelo ID.**