package br.ufc.gvp.model.item.calcados;

import br.ufc.gvp.model.item.Calcado;

public class Tenis extends Calcado {
    public Tenis(String cor, String tamanho, String loja, String estado, String imagem) {
        super("Tênis", cor, tamanho, loja, estado, imagem);
    }

    @Override
    public String getTipo() {
        return "Tênis";
    }
}
