package br.ufc.gvp.model.look;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegistroUso implements Serializable {
    private LocalDateTime dataHora;
    private String descricao;

    public RegistroUso(LocalDateTime dataHora, String descricao) {
        this.dataHora = dataHora;
        this.descricao = descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDescricao() {
        return descricao;
    }
}
