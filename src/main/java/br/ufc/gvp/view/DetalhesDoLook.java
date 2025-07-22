package br.ufc.gvp.view;

import br.ufc.gvp.controller.PessoaController;
import br.ufc.gvp.model.item.Item;
import br.ufc.gvp.model.look.Look;
import br.ufc.gvp.model.look.RegistroUso;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DetalhesDoLook extends JFrame {
    private final PessoaController controller;
    private final Look look;
    private final JFrame janelaAnterior;

    private DefaultListModel<Item> modeloListaItens;
    private DefaultListModel<String> modeloListaUsos;

    public DetalhesDoLook(JFrame janelaAnterior, PessoaController controller, Look look) {
        this.controller = controller;
        this.look = look;
        this.janelaAnterior = janelaAnterior;

        setTitle("Detalhes do Look: " + look.getNome());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(criarPainelPrincipal(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel criarPainelPrincipal() {
        JPanel painel = new JPanel(new GridLayout(1, 2));

        // Lista de itens
        modeloListaItens = new DefaultListModel<>();
        look.getItens().forEach(modeloListaItens::addElement);
        JList<Item> listaItens = new JList<>(modeloListaItens);
        painel.add(new JScrollPane(listaItens));

        // Lista de usos
        modeloListaUsos = new DefaultListModel<>();
        for (RegistroUso uso : look.getUsos()) {
            modeloListaUsos.addElement(uso.getDataHora() + " - " + uso.getDescricao());
        }
        JList<String> listaUsos = new JList<>(modeloListaUsos);
        painel.add(new JScrollPane(listaUsos));

        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new GridLayout(2, 1));

        JPanel linha1 = new JPanel();
        JButton btnAdicionarItem = new JButton("Adicionar Item");
        JButton btnRemoverItem = new JButton("Remover Item");
        JButton btnRegistrarUso = new JButton("Registrar Uso");
        JButton btnVerUsos = new JButton("Ver Usos");
        JButton btnVoltar = new JButton("Voltar");

        linha1.add(btnAdicionarItem);
        linha1.add(btnRemoverItem);
        linha1.add(btnRegistrarUso);
        linha1.add(btnVerUsos);
        linha1.add(btnVoltar);

        painel.add(linha1);

        btnAdicionarItem.addActionListener(e -> adicionarItem());
        btnRemoverItem.addActionListener(e -> removerItem());
        btnRegistrarUso.addActionListener(e -> registrarUso());
        btnVerUsos.addActionListener(e -> verUsos());
        btnVoltar.addActionListener(e -> voltar());

        return painel;
    }

    private void adicionarItem() {
        Item itemSelecionado = selecionarItemDialogo();
        if (itemSelecionado != null) {
            look.adicionarItem(itemSelecionado);
            modeloListaItens.addElement(itemSelecionado);
            controller.salvar();
        }
    }

    private void removerItem() {
        Item itemSelecionado = selecionarItemParaRemover();
        if (itemSelecionado != null) {
            look.removerItem(itemSelecionado);
            modeloListaItens.removeElement(itemSelecionado);
            controller.salvar();
        }
    }

    private void registrarUso() {
        String descricao = JOptionPane.showInputDialog(this, "Descrição do uso:");
        if (descricao != null && !descricao.isBlank()) {
            look.registrarUso(LocalDateTime.now(), descricao);
            modeloListaUsos.addElement(LocalDate.now() + " - " + descricao);
            controller.salvar();
        }
    }

    private void verUsos() {
        setVisible(false);
        new RegistroUsoDetalhado(this, controller, look);
    }

    private void voltar() {
        dispose();
        janelaAnterior.setVisible(true);
        if (janelaAnterior instanceof TabelaDeLooks tabela) {
            tabela.carregarTabela();
        }
    }

    private Item selecionarItemDialogo() {
        List<Item> todosItens = controller.getItens();
        List<String> tiposJaAdicionados = look.getItens().stream().map(Item::getNome).toList();
        List<Item> itensDisponiveis = todosItens.stream().filter(item -> !tiposJaAdicionados.contains(item.getNome())).toList();

        if (itensDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum item disponível com tipo diferente.");
            return null;
        }

        Item itemSelecionado = (Item) JOptionPane.showInputDialog(
                this,
                "Selecione um item:",
                "Itens Disponíveis",
                JOptionPane.PLAIN_MESSAGE,
                null,
                itensDisponiveis.toArray(),
                null
        );
        return itemSelecionado;
    }

    private Item selecionarItemParaRemover() {
        List<Item> itensDoLook = look.getItens();

        if (itensDoLook.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum item para remover.");
            return null;
        }

        Item itemSelecionado = (Item) JOptionPane.showInputDialog(
                this,
                "Selecione um item para remover:",
                "Itens no Look",
                JOptionPane.PLAIN_MESSAGE,
                null,
                itensDoLook.toArray(),
                null
        );
        return itemSelecionado;
    }

}
