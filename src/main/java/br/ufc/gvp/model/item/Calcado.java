package br.ufc.gvp.model.item;

import br.ufc.gvp.model.item.interfaces.IEmprestavel;
import br.ufc.gvp.model.item.interfaces.ILavavel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public abstract class Calcado extends Item implements IEmprestavel, ILavavel {
    private boolean emprestado;
    private String emprestadoPara;
    private LocalDate dataEmprestimo;
    private final List<LocalDate> lavagens = new ArrayList<>();

    public Calcado(String nome, String cor, String tamanho, String loja, String estado) {
        super(nome, cor, tamanho, loja, estado);
    }

    @Override
    public void registrarEmprestimo(String pessoa) {
        emprestado = true;
        emprestadoPara = pessoa;
        dataEmprestimo = LocalDate.now();
    }

    @Override
    public long quantidadeDeDiasDesdeOEmprestimo() {
        return emprestado && dataEmprestimo != null ? ChronoUnit.DAYS.between(dataEmprestimo, LocalDate.now()) : 0;
    }

    @Override
    public void registrarDevolucao() {
        emprestado = false;
        emprestadoPara = null;
        dataEmprestimo = null;
    }

    @Override
    public boolean estaEmprestado(){
        return emprestado;
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
