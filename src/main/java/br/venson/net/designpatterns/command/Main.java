package br.venson.net.designpatterns.command;

public class Main {

    public static void main(String[] args) {
        Editor editor = new Editor();
        Macro macro = new Macro();

        // Montagem da macro com comandos tipados
        macro.adicionar(new DigitarComando(editor, "Ola "));
        macro.adicionar(new DigitarComando(editor, "mundo"));
        macro.adicionar(new ApagarComando(editor, 3));

        System.out.println("Texto inicial: \"" + editor.getTexto() + "\"");

        // Executando a macro
        macro.executar();
        System.out.println("Apos executar macro: \"" + editor.getTexto() + "\"");

        // Desfazendo a macro passo a passo (ordem inversa)
        macro.desfazer();
        System.out.println("Apos desfazer macro: \"" + editor.getTexto() + "\"");
    }
}
