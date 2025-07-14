package br.ufc.gvp.model.item.interfaces;

import java.time.LocalDate;

public interface IEmprestavel {
    void registrarEmprestimo(String nomePessoa, LocalDate data);
    long quantidadeDeDiasDesdeOEmprestimo();
    void registrarDevolucao();
}