# Desafio Sprint 01 - Backend Sensores (Java + Spring Boot + H2)

## Integrantes

- Henriques Paulo da Silva Soares | RM551033
- GUILHERME DE SOUZA PEREIRA | RM552551
- LAIS DE FÁTIMA SILVA GONÇALVES | RM98851
- JOÃO VÍTOR ESTELLA DE FRANÇA | RM552479
- LUCAS RAMOS COELHO | RM551975

---

## Descrição

Projeto backend desenvolvido em **Java com Spring Boot**, responsável por persistir leituras de sensores em banco H2 (modo arquivo) e fornecer endpoints REST consumidos pelo app mobile.  
O banco **H2** é persistido em arquivo, garantindo que os dados não sejam perdidos após reiniciar a aplicação.

---

## Como rodar o projeto

**Pré-requisitos:**  
- Java 17 ou superior  
- Maven 3.8+  

**Passos para executar:**

1. Clone o repositório:
    ```bash
    git clone https://github.com/Henriques-Soares/CS2_Backend_Java_com_Spring_Boot_H2.git
    cd CS2_Backend_Java_com_Spring_Boot_H2
    ```

2. Inicie a aplicação:
    ```bash
    mvn spring-boot:run
    ```

3. Acesse a API na porta padrão: [http://localhost:8080](http://localhost:8080)

---

## Localização do arquivo do banco de dados

O banco H2 é salvo automaticamente em:
