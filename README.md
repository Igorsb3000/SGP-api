### **README para o Projeto SGP**

# **API Sistema de Gestão de Pontos (SGP)**

### **Descrição do Projeto**
Este projeto faz parte do desenvolvimento de uma aplicação para controle de pontos em jornadas de trabalho. A API foi construída utilizando o **Spring Boot 3.4.0** com **Java 17**, integrando-se ao banco de dados **PostgreSQL**. O objetivo é gerenciar o registro de pontos, calcular horas trabalhadas, horas excedentes, tempo restante e fornecer um resumo do dia de trabalho.

O projeto foi desenvolvido seguindo a arquitetura REST.

---

### **Pré-requisitos para Execução do Projeto**
- **Docker** instalado;
- **JDK 17** ou superior instalado;
- **Maven 3.x** instalado;
- **PostgreSQL** instalado;
- **IntelliJ IDEA** configurado.

---

### **Execução do Projeto no Terminal**

#### **1. Configuração das Variáveis de Ambiente**
- No Windows, procure por **"Editar as variáveis de ambiente do sistema"**.
- Na janela de **Propriedades do Sistema**, clique em **Variáveis de ambiente...**.
- Adicione as variáveis listadas no arquivo `.env` com os valores adequados:
    - Exemplo:
        - Nome: `DATABASE_HOST`
        - Valor: `localhost`.
    - Repita o processo para as demais variáveis, incluindo `DATABASE_USERNAME` e `DATABASE_PASSWORD` com suas credenciais do PostgreSQL.

**Alternativa:**
- Copie as variáveis do `.env` e cole-as no arquivo `application.properties`, localizado em `src/main/resources`.
- Preencha `DATABASE_USERNAME` e `DATABASE_PASSWORD` com as credenciais do PostgreSQL.
- Salve as alterações.

---

#### **2. Executando o Docker**
- Abra o terminal ou Git Bash.
- Navegue até a pasta raiz do projeto.
- Execute o comando:
  ```bash
  docker-compose up -d
  ```
  Isso iniciará o contêiner do PostgreSQL.

---

#### **3. Executando o Projeto**
- No terminal, navegue até a pasta raiz do projeto.
- Execute o comando:
  ```bash
  mvn spring-boot:run
  ```
- A API estará disponível em:
  ```
  http://localhost:8081
  ```
- O Swagger estará disponível em:
  ```
  http://localhost:8081/api/swagger-ui/index.html
  ```
**OBS:** Caso a porta `8081` esteja em uso, altere-a no arquivo `application.properties`.

---

### **Execução do Projeto no IntelliJ IDEA**

#### **1. Configurando Variáveis de Ambiente**
- Abra o IntelliJ IDEA e carregue o projeto.
- No menu superior, clique em **File > Settings** (ou pressione `Ctrl+Alt+S`).
- No painel lateral, vá até **Build, Execution, Deployment > Build Tools > Maven**.
- Em **Runner**, insira as variáveis de ambiente do arquivo `.env` no campo **Environment Variables**.
- Adicione as credenciais do PostgreSQL em `DATABASE_USERNAME` e `DATABASE_PASSWORD`.
- Clique em **Apply** e depois em **OK**.

**Alternativa:**
- Cole as variáveis do `.env` no arquivo `application.properties`, localizado em `src/main/resources`.
- Insira as credenciais do PostgreSQL diretamente no arquivo.
- Salve as alterações.

---

#### **2. Executando o Docker**
- No IntelliJ IDEA, abra o terminal integrado ou um terminal externo.
- Navegue até a pasta raiz do projeto.
- Execute o comando:
  ```bash
  docker-compose up -d
  ```
  Isso iniciará o contêiner do PostgreSQL.

---

#### **3. Executando o Projeto**
- No IntelliJ IDEA, localize a classe principal do projeto (`SGPApplication.java`).
- Clique com o botão direito na classe e selecione **Run 'SGPApplication.main()'**.
- Isso iniciará a aplicação.

---

### **Exemplo de Requisição**

#### **Registro de Ponto**
**Request:**
```http
POST /api/v1/points HTTP/1.1
Host: localhost:8081
Content-Type: application/json

{
   "dateHour": "02-12-2024 08:00:00"
}
```

**Response:**
```http
HTTP/1.1 204 No Content
```

#### **Resumo Diário**
**Request:**
```http
GET /api/v1/points/summary HTTP/1.1
Host: localhost:8081
```

**Response:**
```json
{
  "name": "Igor Bento",
  "date": "03-12-2024",
  "hoursWorked": {
    "hours": 9,
    "minutes": 12
  },
  "hoursRemaining": {
    "hours": 0,
    "minutes": 0
  },
  "overtime": {
    "hours": 1,
    "minutes": 12
  },
  "journeyComplete": true
}
```

---

### **Autor**
**Igor Silva Bento**  
[GitHub](https://github.com/Igorsb3000)  
[LinkedIn](https://www.linkedin.com/in/igor-silva-bento-7542004a/)