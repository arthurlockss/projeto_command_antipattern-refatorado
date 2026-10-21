# Editor de Texto Refatorado com o Padrão Command

Projeto de um editor de texto com suporte a **macros** desenvolvido em **Java 17 / Maven**, refatorado a partir de um código legado que apresentava anti-patterns de design (acoplamento forte entre invocador e operações, ausência de type-safety e impossibilidade de desfazer ações).

---

## O Problema Original (Anti-Pattern)

No código original:
1. **Acoplamento forte no Invocador (`Macro`):** A classe `Macro` conhecia todas as operações disponíveis, suas respectivas sintaxes e chamava diretamente os métodos da classe `Editor` através de um `switch`.
2. **Violação do Princípio Aberto/Fechado (OCP):** Para adicionar uma nova operação (como `SUBSTITUIR`), era necessário alterar o código interno de `Macro`, editando o `switch` e a lógica de parsing.
3. **Ausência de Type-Safety e Fragilidade em Runtime:** As ações eram representadas por strings brutas (ex.: `"DIGITAR:Ola"`, `"APAGAR:3"`). Erros de sintaxe ou tipo só eram detectados em tempo de execução, além de o delimitador `:` quebrar facilmente caso o texto contivesse dois pontos (ex.: `"DIGITAR:nota:10"`).
4. **Impossibilidade de Desfazer (`undo`):** A execução era unidirecional. A classe `Macro` descartava retornos de métodos (como o texto retornado por `Editor.apagar`) e não armazenava o estado anterior de cada passo.

---

## Solução com o Padrão Command

A refatoração adotou o padrão comportamental **Command (GoF)**, separando as responsabilidades em papéis bem definidos:

1. **Interface de Comando (`Comando`):**
   - Declara o contrato uniforme `executar()` e `desfazer()`.
2. **Comandos Concretos (`DigitarComando` e `ApagarComando`):**
   - Encapsulam os parâmetros e a referência ao *Receiver* (`Editor`).
   - `DigitarComando`: executa digitando no editor e desfaz apagando a mesma quantidade de caracteres.
   - `ApagarComando`: armazena o texto apagado retornado pelo editor no momento da execução para redigitá-lo no `desfazer()`.
3. **Receiver (`Editor`):**
   - Executa o trabalho real de mutação do texto (`digitar`, `apagar`, `getTexto`).
4. **Invoker (`Macro`):**
   - Mantém uma lista de objetos `Comando` e um histórico de comandos executados.
   - Totalmente desacoplado do `Editor`: apenas percorre os comandos disparando `executar()`.
   - Implementa `desfazer()` percorrendo o histórico na ordem inversa (LIFO).

---

## Estrutura das Classes

* **`Comando`:** Interface comum que define os métodos `executar()` e `desfazer()`.
* **`DigitarComando`:** Comando concreto para adição de texto no editor.
* **`ApagarComando`:** Comando concreto para remoção de caracteres, com suporte a rollback.
* **`Editor`:** Receiver que gerencia o buffer de texto.
* **`Macro`:** Invoker responsável por enfileirar, disparar e reverter a sequência de comandos.
* **`Main`:** Ponto de entrada demonstrando a montagem da macro, sua execução e o posterior desfazimento.

---

## Como Rodar

O projeto utiliza **Java 17+** e **Maven**:

```bash
# Compilar o projeto
mvn compile

# Executar a classe Main
java -cp target/classes br.venson.net.designpatterns.command.Main
```

### Saída esperada:
```text
Texto inicial: ""
Apos executar macro: "Ola mu"
Apos desfazer macro: ""
```
