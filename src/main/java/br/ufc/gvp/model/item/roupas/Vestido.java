package br.ufc.gvp.model.item.roupas;

import br.ufc.gvp.model.item.Roupa;

public class Vestido extends Roupa {
    public Vestido(String cor, String tamanho, String loja, String estado) {
        super("Vestido", cor, tamanho, loja, estado);
    }
}