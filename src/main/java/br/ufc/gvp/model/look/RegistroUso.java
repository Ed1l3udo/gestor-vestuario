package br.ufc.gvp.model.look;

import java.io.Serializable;
import java.time.LocalDate;

public class RegistroUso implements Serializable {
    private LocalDate dataHora;
    private String descricao;

    public RegistroUso(LocalDate dataHora, String descricao) {
        this.dataHora = dataHora;
        this.descricao = descricao;
    }

    public LocalDate getDataHora() {
        return dataHora;
    }

    public String getDescricao() {
        return descricao;
    }
}
