package br.ufc.gvp.view;

import br.ufc.gvp.controller.PessoaController;
import br.ufc.gvp.model.look.Look;
import br.ufc.gvp.model.look.RegistroUso;

import javax.swing.*;
import java.awt.*;

public class RegistroUsoDetalhado extends JFrame {

    private final PessoaController controller;
    private final Look look;
    private final JFrame janelaAnterior;
    private final DefaultListModel<RegistroUso> modeloLista;
    private final JList<RegistroUso> listaUsos;

    public RegistroUsoDetalhado(JFrame janelaAnterior, PessoaController controller, Look look) {
        this.controller = controller;
        this.look = look;
        this.janelaAnterior = janelaAnterior;

        setTitle("Usos do Look: " + look.getNome());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Lista de usos
        modeloLista = new DefaultListModel<>();
        look.getUsos().forEach(modeloLista::addElement);
        listaUsos = new JList<>(modeloLista);
        listaUsos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaUsos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                RegistroUso uso = (RegistroUso) value;
                String texto = uso.getDataHora() + " - " + uso.getDescricao();
                return super.getListCellRendererComponent(list, texto, index, isSelected, cellHasFocus);
            }
        });
        add(new JScrollPane(listaUsos), BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel();
        JButton btnRemover = new JButton("Remover Uso Selecionado");
        JButton btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnRemover);
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ação de remover
        btnRemover.addActionListener(e -> removerUso());

        // Voltar
        btnVoltar.addActionListener(e -> voltar());

        setVisible(true);
    }

    private void removerUso() {
        RegistroUso usoSelecionado = listaUsos.getSelectedValue();
        if (usoSelecionado != null) {
            int resposta = JOptionPane.showConfirmDialog(this,
                    "Deseja remover este registro de uso?",
                    "Confirmar Remoção",
                    JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                modeloLista.removeElement(usoSelecionado);
                look.getUsos().remove(usoSelecionado);
                controller.salvar();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um uso para remover.");
        }
    }

    private void voltar() {
        dispose();
        janelaAnterior.setVisible(true);
    }
}
