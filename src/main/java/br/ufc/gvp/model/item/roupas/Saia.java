package br.ufc.gvp.model.item.roupas;

import br.ufc.gvp.model.item.Roupa;

public class Saia extends Roupa {
    public Saia(String cor, String tamanho, String loja, String estado, String imagem) {
        super("Saia", cor, tamanho, loja, estado, imagem);
    }

    @Override
    public String getTipo() {
        return "Saia";
    }
}
