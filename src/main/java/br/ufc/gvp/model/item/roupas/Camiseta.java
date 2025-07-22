package br.ufc.gvp.model.item.roupas;

import br.ufc.gvp.model.item.Roupa;

public class Camiseta extends Roupa {
    public Camiseta(String cor, String tamanho, String loja, String estado) {
        super("Camiseta", cor, tamanho, loja, estado);
    }
}