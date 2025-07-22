package br.ufc.gvp.model.item;

import br.ufc.gvp.model.item.interfaces.IEmprestavel;
import br.ufc.gvp.model.item.interfaces.ILavavel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public abstract class Roupa extends Item implements IEmprestavel, ILavavel {
    private boolean emprestado;
    private String emprestadoPara;
    private LocalDate dataEmprestimo;
    private final List<LocalDate> lavagens = new ArrayList<>();

    public Roupa(String nome, String cor, String tamanho, String loja, String estado, String imagem) {
        super(nome, cor, tamanho, loja, estado, imagem);
    }

    @Override
    public void registrarEmprestimo(String pessoa, LocalDate data) {
        this.emprestado = true;
        this.emprestadoPara = pessoa;
        this.dataEmprestimo = data;
    }

    @Override
    public long quantidadeDeDiasDesdeOEmprestimo() {
        return emprestado && dataEmprestimo != null ? ChronoUnit.DAYS.between(dataEmprestimo, LocalDate.now()) : 0;
    }

    @Override
    public void registrarDevolucao() {
        this.emprestado = false;
        this.emprestadoPara = null;
        this.dataEmprestimo = null;
    }

    @Override
    public void registrarLavagem(LocalDate data) {
        lavagens.add(data);
    }

    @Override
    public List<LocalDate> getLavagens() {
        return lavagens;
    }
}
