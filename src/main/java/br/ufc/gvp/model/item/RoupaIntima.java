package br.ufc.gvp.model.item;

import br.ufc.gvp.model.item.interfaces.ILavavel;

import java.time.LocalDate;

public abstract class RoupaIntima extends Item implements ILavavel {
    private LocalDate ultimaLavagem;

    public RoupaIntima(String nome, String cor, String tamanho, String loja, String estado, String imagem) {
        super(nome, cor, tamanho, loja, estado, imagem);
    }

    @Override
    public void registrarLavagem(LocalDate data) {
        this.ultimaLavagem = data;
    }

    public LocalDate getUltimaLavagem() {
        return ultimaLavagem;
    }
}