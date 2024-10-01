# Conecta UFAPE

## Descrição do Projeto

O **Conecta UFAPE** é uma rede social simples desenvolvida como projeto de fim de período para conectar estudantes da UFAPE com base em seus cursos, interesses e amigos em comum. A aplicação permite a criação de usuários, autenticação por e-mail e senha, adição de amigos, e recomendações de amizade baseadas em algoritmos que priorizam curso, interesses e conexões de amizade.

## Funcionalidades

- **Cadastro e Login de Usuários:** Usuários podem se cadastrar informando nome, e-mail, senha, curso e interesses.
- **Sistema de Amigos:** Adicionar amigos, visualizar lista de amigos e solicitar amizade a outros usuários.
- **Recomendações de Amigos:** Recomendação de novos amigos com base em curso, interesses ou amigos em comum.
- **Sistema de Solicitações de Amizade:** Gerenciar solicitações de amizade recebidas e enviadas.
- **Armazenamento Persistente:** Dados de usuários e amizades são armazenados em um arquivo JSON para garantir persistência.

## Estrutura do Projeto

O projeto é organizado nas seguintes partes:

- **Main:** Arquivo principal que executa a CLI da aplicação.
- **Usuario:** Classe responsável por definir as propriedades e métodos do usuário.
- **RedeSocial:** Classe que gerencia a rede social e os algoritmos de recomendação de amigos.
- **PersistenciaJSON:** Classe responsável pela leitura e escrita dos dados no arquivo JSON.

## Pré-requisitos

Antes de rodar o projeto, certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

- **Java JDK 11+**
- **Maven**

## Como Rodar o Projeto

1. **Clone o Repositório:**

   ```bash
   git clone https://github.com/luisfelipe03/Conecta-UFAPE.git
   cd Conecta-UFAPE
   ```

2. **Compilar o Projeto:**

   No diretório raiz do projeto, execute o Maven para compilar:

   ```bash
   mvn clean compile
   ```

3. **Executar a Aplicação:**

   Para rodar a aplicação, utilize o seguinte comando:

   ```bash
   mvn exec:java -Dexec.mainClass="main.java.Main"
   ```

   O menu da CLI será exibido e você poderá interagir com as opções disponíveis.

4. **Dados Persistentes:**

   O arquivo `data.json` localizado na pasta `resources` é onde todos os dados dos usuários e amizades são armazenados. Ele será atualizado automaticamente conforme novos usuários são cadastrados ou novas amizades são feitas.

## Exemplo de Uso

Após iniciar a aplicação, o usuário pode se registrar e realizar login. Após o login, ele verá um menu com as seguintes opções:

- Ver seus amigos
- Ver recomendações de amizade
- Buscar usuários pelo nome
- Enviar solicitações de amizade
- Ver solicitações de amizade pendentes

A interface de linha de comando será exibida para que o usuário possa navegar entre as opções e executar as ações desejadas.

## Estrutura de Arquivos

```
Conecta-UFAPE/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Main.java
│   │   │   ├── PersistenciaJSON.java
│   │   │   ├── RedeSocial.java
│   │   │   └── Usuario.java
│   └── resources/
│       └── data.json
├── test/
├── pom.xml
└── README.md
```
