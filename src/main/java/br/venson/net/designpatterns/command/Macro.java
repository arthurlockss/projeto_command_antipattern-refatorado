package br.venson.net.designpatterns.command;

import java.util.ArrayList;
import java.util.List;

public class Macro implements Comando {

    private final List<Comando> comandos = new ArrayList<>();
    private final List<Comando> historicoExecutado = new ArrayList<>();

    public void adicionar(Comando comando) {
        comandos.add(comando);
    }

    @Override
    public void executar() {
        historicoExecutado.clear();
        for (Comando comando : comandos) {
            comando.executar();
            historicoExecutado.add(comando);
        }
    }

    @Override
    public void desfazer() {
        for (int i = historicoExecutado.size() - 1; i >= 0; i--) {
            historicoExecutado.get(i).desfazer();
        }
        historicoExecutado.clear();
    }
}
