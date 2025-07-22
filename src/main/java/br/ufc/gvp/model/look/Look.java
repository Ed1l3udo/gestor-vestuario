package br.ufc.gvp.model.look;

import br.ufc.gvp.model.item.Item;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Look implements Serializable {
    private String nome;
    private List<Item> itens;
    private List<RegistroUso> usos;
    private int totalUsos = 0;

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

    public int getTotalUsos(){
        return totalUsos;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public void removerItem(Item item) {
        itens.remove(item);
    }

    public void registrarUso(LocalDate dataHora, String descricao) {
        usos.add(new RegistroUso(dataHora, descricao));
        totalUsos++;
        for(Item item : itens){
            item.setTotalUsos(item.getTotalUsos()+1);
        }
    }

    public void excluirUso(RegistroUso uso){
        getUsos().remove(uso);
        totalUsos--;
        for(Item item : itens){
            item.setTotalUsos(item.getTotalUsos()-1);
        }
    }

    public List<RegistroUso> getUsos() {
        return usos;
    }
}

