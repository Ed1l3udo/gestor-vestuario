package br.ufc.gvp.model.emprestimo;

import br.ufc.gvp.model.item.Item;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Emprestimo implements Serializable {
    private final Item item;
    private final String nomePessoa;
    private final LocalDateTime dataEmprestimo;
    private LocalDateTime dataDevolucao;

    public Emprestimo(Item item, String nomePessoa) {
        this.item = item;
        this.nomePessoa = nomePessoa;
        this.dataEmprestimo = LocalDateTime.now();
    }

    public Item getItem() {
        return item;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public LocalDateTime getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void registrarDevolucao() {
        this.dataDevolucao = LocalDateTime.now();
    }

    public boolean estaDevolvido() {
        return dataDevolucao != null;
    }
}
