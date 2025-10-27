# 🧠 Digital Twin Sensores – Sprint 4
### Backend – Spring Boot 3.5 | PostgreSQL | Flyway | JWT

---

## 👥 Integrantes
| Nome | RM |
|------|----|
| Henriques Paulo da Silva Soares | **551033** |
| Guilherme de Souza Pereira | **552551** |
| Laís de Fátima Silva Gonçalves | **98851** |
| João Vítor Estella de França | **552479** |
| Lucas Ramos Coelho | **551975** |

---

## 🚀 Descrição do Projeto
O **Digital Twin Sensores** é um sistema voltado à simulação e monitoramento de sensores industriais, integrando backend em **Spring Boot** com frontend mobile em **React Native / Expo**.

Nesta **Sprint 4**, o backend foi evoluído com foco em **escalabilidade e segurança**, realizando:

- Migração da persistência de **H2** para **PostgreSQL**;
- Controle de versões do banco via **Flyway**;
- Implementação de **autenticação JWT**;
- Habilitação de **CORS** para integração com o aplicativo mobile.

---

## ⚙️ Tecnologias Utilizadas
- **Java 17 / Spring Boot 3.5**
- **PostgreSQL 16**
- **Flyway 11.10**
- **Spring Security + JWT (JJWT 0.11.5)**
- **Docker & Docker Compose**
- **Maven 3.9+**

---

## 🐳 Subindo o Banco de Dados
Certifique-se de ter o **Docker Desktop** ativo e, na raiz do projeto, execute:

```bash
docker compose up -d
