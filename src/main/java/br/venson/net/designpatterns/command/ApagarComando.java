package br.venson.net.designpatterns.command;

public class ApagarComando implements Comando {

    private final Editor editor;
    private final int quantidade;
    private String textoApagado;

    public ApagarComando(Editor editor, int quantidade) {
        this.editor = editor;
        this.quantidade = quantidade;
    }

    @Override
    public void executar() {
        this.textoApagado = editor.apagar(quantidade);
    }

    @Override
    public void desfazer() {
        if (textoApagado != null) {
            editor.digitar(textoApagado);
        }
    }
}
