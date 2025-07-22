package br.ufc.gvp.view;

import br.ufc.gvp.controller.PessoaController;
import br.ufc.gvp.model.emprestimo.Emprestimo;
import br.ufc.gvp.model.item.interfaces.IEmprestavel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TabelaDeEmprestimos extends JFrame {
    private final PessoaController controller;
    private final DefaultTableModel modelo;
    private final JFrame janelaAnterior;
    private final JTable tabela;

    public TabelaDeEmprestimos(JFrame janelaAnterior, PessoaController controller) {
        this.controller = controller;
        this.janelaAnterior = janelaAnterior;

        setTitle("Empréstimos de " + controller.getPessoa().getNome());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new String[]{"Item", "Pessoa Destino", "Data", "Status"}, 0);
        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton finalizarEmprestimo = new JButton("Finalizar Empréstimo");
        JButton voltar = new JButton("Voltar");

        JPanel painel = new JPanel();
        painel.add(finalizarEmprestimo);
        painel.add(voltar);

        add(painel, BorderLayout.SOUTH);

        finalizarEmprestimo.addActionListener(e -> finalizarEmprestimo());
        voltar.addActionListener(e -> voltar());

        carregarTabela();
        setVisible(true);
    }

    private void finalizarEmprestimo(){
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            Emprestimo emprestimo = controller.getEmprestimos().get(linha);
            if (emprestimo.estaDevolvido()) {
                JOptionPane.showMessageDialog(this, "Este empréstimo já está finalizado.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja finalizar o empréstimo do item \"" + emprestimo.getItem().getNome() + "\"?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                emprestimo.registrarDevolucao();
                controller.salvar();  // garante persistência
                carregarTabela();     // atualiza a tabela
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um empréstimo.");
        }
    }

    private void voltar() {
        janelaAnterior.setVisible(true);
        dispose();
    }

    private void carregarTabela() {
        modelo.setRowCount(0);
        for (Emprestimo emprestimo : controller.getEmprestimos()) {
            modelo.addRow(new Object[]{
                    emprestimo.getItem().getNome(),
                    emprestimo.getNomePessoa(),
                    emprestimo.getDataEmprestimo().toString() + ((IEmprestavel) emprestimo.getItem()).quantidadeDeDiasDesdeOEmprestimo(),
                    emprestimo.estaDevolvido() ? "Finalizado" : "Em andamento"
            });
        }
    }
}
