package br.ufc.gvp.model.item;

import br.ufc.gvp.model.item.interfaces.ILavavel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class RoupaIntima extends Item implements ILavavel {
    private final List<LocalDate> lavagens = new ArrayList<>();

    public RoupaIntima(String nome, String cor, String tamanho, String loja, String estado) {
        super(nome, cor, tamanho, loja, estado);
    }

    @Override
    public void registrarLavagem(LocalDate data) {
        lavagens.add(data);
        setTotalLavagens(getTotalLavagens()+1);
    }

    @Override
    public List<LocalDate> getLavagens() {
        return lavagens;
    }
}