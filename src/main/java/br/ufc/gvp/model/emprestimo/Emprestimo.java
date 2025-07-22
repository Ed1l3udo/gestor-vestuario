package br.ufc.gvp.model.emprestimo;

import br.ufc.gvp.model.item.Item;

import java.io.Serializable;
import java.time.LocalDate;

public class Emprestimo implements Serializable {
    private final Item item;
    private final String nomePessoa;
    private final LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public Emprestimo(Item item, String nomePessoa) {
        this.item = item;
        this.nomePessoa = nomePessoa;
        this.dataEmprestimo = LocalDate.now();
    }

    public Item getItem() {
        return item;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void registrarDevolucao() {
        this.dataDevolucao = LocalDate.now();
    }

    public boolean estaDevolvido() {
        return dataDevolucao != null;
    }
}
