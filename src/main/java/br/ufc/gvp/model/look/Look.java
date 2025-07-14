package br.ufc.gvp.model.look;

import br.ufc.gvp.model.item.Item;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Look implements Serializable {
    private String nome;
    private List<Item> itens;
    private List<RegistroUso> usos;

    public Look(String nome) {
        this.nome = nome;
        this.itens = new ArrayList<>();
        this.usos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public void removerItem(Item item) {
        itens.remove(item);
    }

    public void registrarUso(LocalDateTime dataHora, String descricao) {
        usos.add(new RegistroUso(dataHora, descricao));
    }

    public List<RegistroUso> getUsos() {
        return usos;
    }
}

