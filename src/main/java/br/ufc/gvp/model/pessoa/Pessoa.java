package br.ufc.gvp.model.pessoa;

import br.ufc.gvp.model.item.Item;
import br.ufc.gvp.model.item.interfaces.IEmprestavel;
import br.ufc.gvp.model.look.Look;
import br.ufc.gvp.model.emprestimo.Emprestimo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pessoa implements Serializable {
    private String nome;
    private List<Item> itens;
    private List<Look> looks;
    private List<Emprestimo> emprestimos;

    public Pessoa(String nome) {
        this.nome = nome;
        this.itens = new ArrayList<>();
        this.looks = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Item> getItens() {
        return itens;
    }

    public List<Look> getLooks() {
        return looks;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public void removerItem(Item item) {
        itens.remove(item);
    }

    public void adicionarLook(Look look) {
        looks.add(look);
    }

    public void removerLook(Look look) {
        looks.remove(look);
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void adicionarEmprestimo(Emprestimo e) {
        emprestimos.add(e);
    }

    public void removerEmprestimo(Emprestimo e) {
        emprestimos.remove(e);
    }

    public List<Item> getItensEmprestados() {
        List<Item> emprestados = new ArrayList<>();
        for (Item item : itens) {
            if (item instanceof IEmprestavel emprestavel) {
                if (emprestavel.quantidadeDeDiasDesdeOEmprestimo() > 0) {
                    emprestados.add(item);
                }
            }
        }
        return emprestados;
    }
}
