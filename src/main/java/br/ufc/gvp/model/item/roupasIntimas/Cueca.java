package br.ufc.gvp.model.item.roupasIntimas;

import br.ufc.gvp.model.item.RoupaIntima;

public class Cueca extends RoupaIntima {
    public Cueca (String cor, String tamanho, String loja, String estado, String imagem) {
        super("Cueca", cor, tamanho, loja, estado, imagem);
    }

    @Override
    public String getTipo() {
        return "Cueca";
    }
}
