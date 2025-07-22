package br.ufc.gvp.view;

import br.ufc.gvp.model.item.Item;
import br.ufc.gvp.model.item.interfaces.ILavavel;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class JanelaLavagens extends JFrame {
    private final JFrame janelaAnterior;
    private final ILavavel item;

    public JanelaLavagens(JFrame janelaAnterior, ILavavel item) {
        this.janelaAnterior = janelaAnterior;
        this.item = item;

        setTitle("Lavagens do Item");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        DefaultListModel<String> modelo = new DefaultListModel<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        item.getLavagens().forEach(data -> modelo.addElement(data.format(formatter)));

        JList<String> lista = new JList<>(modelo);
        add(new JScrollPane(lista), BorderLayout.CENTER);

        JButton voltar = new JButton("Voltar");
        voltar.addActionListener(e -> {
            dispose();
            janelaAnterior.setVisible(true);
        });

        JPanel painel = new JPanel();
        painel.add(voltar);
        add(painel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
