package br.ufc.gvp.model.item.interfaces;

import java.time.LocalDate;
import java.util.List;

public interface ILavavel {
    void registrarLavagem(LocalDate data);
    List<LocalDate> getLavagens();
}
