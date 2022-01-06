Link para os diagramas UML: https://app.diagrams.net/#G1ox_TONGRrIdne8lVmdbdKmCj05S7GdKJ

Ao inicializar a aplicação haverá os seguintes usuários criados:
* Usuário admin: *admin* com senha *admin*
* Usuário mensal: *usuario* com senha *usuario*

## Testes Automatizados
Os testes da aplicação estão concentrados na classe MainTest.

Os testes simulam o uso da aplicação e fazem a conexão com o banco de dados.

## LOGIN
Se o usuário não existir será retornado uma mensagem de erro.

Existem melhorias que podem ser feitas, como por exemplo a validação desses campos.

## Tipos de usuário

O sistema possui dois tipo de usuários: mensal e admin.

### Usuário admin
- [x] Login
- [x] Cadastrar usuário: Os campos pedidos são nome, usuário, senha e o tipo de usuário (1 mensal e 2 admin).
- [x] Cadastrar item: Os campos pedidos são descrição, valor, estoque atual e estoque mínimo.
- [x] Comprar produto: 0 para voltar ao menu anterior ou digitar o número do respectivo item a ser comprado.
- [x] Imprimir histórico das vendas na máquina
- [x] Colocar crédito, coloca crédito no usuario logado
- [x] Deslogar do sistema, sai da tela do usuário e permite outro usuário logar

### Usuário mensal
O usuário mensal é aquele que irá utilizar a máquina de café de forma limitada ao consumo.
Ele terá acesso a seguintes funcionalidades:
- [x] Login
- [x] Comprar produto: 0 para voltar ao menu anterior ou digitar o número do respectivo item a ser comprado.
- [x] Colocar crédito, coloca crédito no usuario logado
- [x] Deslogar do sistema, sai da tela do usuário e permite outro usuário logar

