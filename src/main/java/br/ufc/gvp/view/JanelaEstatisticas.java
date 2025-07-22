package br.ufc.gvp.view;

import br.ufc.gvp.controller.PessoaController;
import br.ufc.gvp.model.item.Item;
import br.ufc.gvp.model.look.Look;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JanelaEstatisticas extends JFrame {
    private final PessoaController controller;
    private final JFrame janelaAnterior;

    public JanelaEstatisticas(JFrame janelaAnterior, PessoaController controller) {
        this.janelaAnterior = janelaAnterior;
        this.controller = controller;

        setTitle("Estatísticas - " + controller.getPessoa().getNome());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane abas = new JTabbedPane();

        abas.add("Itens + Usados", painelItensMaisUsados());
        abas.add("Itens - Usados", painelItensMenosUsados());
        abas.add("Itens + Lavados", painelItensMaisLavados());
        abas.add("Itens Emprestados", painelItensEmprestados());
        abas.add("Looks + Usados", painelLooksMaisUsados());

        add(abas, BorderLayout.CENTER);

        JButton voltar = new JButton("Voltar");
        voltar.addActionListener(e -> voltar());
        JPanel painelVoltar = new JPanel();
        painelVoltar.add(voltar);
        add(painelVoltar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel painelItensMaisUsados() {
        return criarTabelaItem(controller.getItens().stream().sorted(Comparator.comparingInt(Item::getTotalUsos).reversed()).limit(10).collect(Collectors.toList()));
    }

    private JPanel painelItensMenosUsados() {
        return criarTabelaItem(controller.getItens().stream().sorted(Comparator.comparingInt(Item::getTotalUsos)).limit(10).collect(Collectors.toList()));
    }

    private JPanel painelItensMaisLavados() {
        return criarTabelaItem(controller.getItens().stream().sorted(Comparator.comparingInt(Item::getTotalLavagens).reversed()).limit(10).collect(Collectors.toList()));
    }

    private JPanel painelItensEmprestados() {
        List<Item> emprestados = controller.getEmprestimos().stream().filter(e -> !e.estaDevolvido()).map(e -> e.getItem()).collect(Collectors.toList());
        return criarTabelaItem(emprestados);
    }

    private JPanel painelLooksMaisUsados() {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"Look", "Usos"}, 0);
        JTable tabela = new JTable(modelo);

        controller.getLooks().stream().sorted(Comparator.comparingInt(l -> -l.getUsos().size())).limit(10).forEach(look -> modelo.addRow(new Object[]{look.getNome(), look.getTotalUsos()}));

        return new JPanel(new BorderLayout()) {{
            add(new JScrollPane(tabela), BorderLayout.CENTER);
        }};
    }

    private JPanel criarTabelaItem(List<Item> itens) {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"Nome", "Usos", "Lavagens"}, 0);
        for (Item item : itens) {
            modelo.addRow(new Object[]{item.toString(), item.getTotalUsos(), item.getTotalLavagens()});
        }
        JTable tabela = new JTable(modelo);
        return new JPanel(new BorderLayout()) {{
            add(new JScrollPane(tabela), BorderLayout.CENTER);
        }};
    }

    private void voltar() {
        janelaAnterior.setVisible(true);
        dispose();
    }
}
