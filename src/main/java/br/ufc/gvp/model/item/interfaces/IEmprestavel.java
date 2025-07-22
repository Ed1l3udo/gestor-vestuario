package br.ufc.gvp.model.item.interfaces;

import java.time.LocalDate;

public interface IEmprestavel {
    void registrarEmprestimo(String nomePessoa);
    long quantidadeDeDiasDesdeOEmprestimo();
    void registrarDevolucao();
    boolean estaEmprestado();
}