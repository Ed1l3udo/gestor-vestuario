package br.ufc.gvp.model.item.calcados;

import br.ufc.gvp.model.item.Calcado;

public class Sandalia extends Calcado {
    public Sandalia(String cor, String tamanho, String loja, String estado, String imagem) {
        super("Sandália", cor, tamanho, loja, estado, imagem);
    }

    @Override
    public String getTipo() {
        return "Sandália";
    }
}
