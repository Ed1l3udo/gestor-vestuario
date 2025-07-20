package br.ufc.gvp.view;

import br.ufc.gvp.controller.PessoaController;
import br.ufc.gvp.model.look.Look;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TabelaDeLooks extends JFrame {

    private final PessoaController controller;
    private final DefaultTableModel modelo;
    private final JFrame janelaAnterior;
    private final JTable tabela;

    public TabelaDeLooks(JFrame janelaAnterior, PessoaController controller) {
        this.controller = controller;
        this.janelaAnterior = janelaAnterior;

        setTitle("Looks de " + controller.getPessoa().getNome());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new String[]{"Nome do Look"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linha = tabela.getSelectedRow();
                    if (linha >= 0) {
                        Look lookSelecionado = controller.getLooks().get(linha);
                        new DetalhesDoLook(TabelaDeLooks.this, controller, lookSelecionado);
                        setVisible(false);
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JButton btnCriar = new JButton("Criar Look");
        JButton btnExcluir = new JButton("Excluir Look");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painel = new JPanel();
        painel.add(btnCriar);
        painel.add(btnExcluir);
        painel.add(btnVoltar);
        add(painel, BorderLayout.SOUTH);

        // Ações
        btnCriar.addActionListener(e -> criarLook());
        btnExcluir.addActionListener(e -> excluirLook());
        btnVoltar.addActionListener(e -> voltar());

        carregarTabela();
        setVisible(true);
    }

    private void criarLook() {
        String nome = JOptionPane.showInputDialog(this, "Nome do novo look:");
        if (nome != null && !nome.trim().isEmpty()) {
            Look novoLook = new Look(nome.trim());
            controller.adicionarLook(novoLook);
            controller.salvar();
            new DetalhesDoLook(this, controller, novoLook);
            setVisible(false);
        }
    }

    public void excluirLook() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            Look look = controller.getLooks().get(linha);
            int confirm = JOptionPane.showConfirmDialog(this, "Excluir o look \"" + look.getNome() + "\"?");
            if (confirm == JOptionPane.YES_OPTION) {
                controller.removerLook(look);
                carregarTabela();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um look para excluir.");
        }
    }

    public void voltar() {
        dispose();
        janelaAnterior.setVisible(true);
    }

    public void carregarTabela() {
        modelo.setRowCount(0);
        List<Look> looks = controller.getLooks();
        for (Look look : looks) {
            modelo.addRow(new Object[]{look.getNome()});
        }
    }
}
