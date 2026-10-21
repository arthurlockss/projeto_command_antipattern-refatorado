package br.venson.net.designpatterns.command;

public interface Comando {
    void executar();
    void desfazer();
}
