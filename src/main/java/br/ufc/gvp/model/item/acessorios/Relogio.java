package br.ufc.gvp.model.item.acessorios;

import br.ufc.gvp.model.item.Acessorio;

public class Relogio extends Acessorio {
    public Relogio(String cor, String tamanho, String loja, String estado, String imagem) {
        super("Relógio", cor, tamanho, loja, estado, imagem);
    }

    @Override
    public String getTipo() {
        return "Relógio";
    }
}
