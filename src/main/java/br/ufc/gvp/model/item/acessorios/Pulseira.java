package br.ufc.gvp.model.item.acessorios;

import br.ufc.gvp.model.item.Acessorio;

public class Pulseira extends Acessorio {
    public Pulseira(String cor, String tamanho, String loja, String estado, String imagem) {
        super("Pulseira", cor, tamanho, loja, estado, imagem);
    }

    @Override
    public String getTipo() {
        return "Pulseira";
    }
}
