package br.ufc.gvp;

import javax.swing.SwingUtilities;
import br.ufc.gvp.view.JanelaPrincipal;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JanelaPrincipal::new);
    }
}
