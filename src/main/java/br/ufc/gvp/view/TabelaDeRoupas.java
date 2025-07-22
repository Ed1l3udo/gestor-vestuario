package br.ufc.gvp.view;

import br.ufc.gvp.controller.PessoaController;
import br.ufc.gvp.model.item.Item;
import br.ufc.gvp.model.item.interfaces.IEmprestavel;
import br.ufc.gvp.model.item.interfaces.ILavavel;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

public class TabelaDeRoupas extends JFrame {
    private final JFrame janelaAnterior;
    private final PessoaController controller;
    private final DefaultTableModel modelo;
    private final JTable tabela;

    public TabelaDeRoupas(JFrame janelaAnterior, PessoaController controller) {
        this.controller = controller;
        this.janelaAnterior = janelaAnterior;

        setTitle("Armário de " + controller.getPessoa().getNome());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        /* tabela */
        String[] colunas = {"Tipo", "Cor", "Tam.", "Loja", "Estado", "Editar", "Excluir"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r,int c){ return c>=5; }
        };
        tabela = new JTable(modelo);
        tabela.setRowHeight(28);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        /* render/editor */
        tabela.getColumn("Editar").setCellRenderer(new BotaoRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new BotaoEditor("Editar", this::editarLinha));
        tabela.getColumn("Excluir").setCellRenderer(new BotaoRenderer("Excluir"));
        tabela.getColumn("Excluir").setCellEditor(new BotaoEditor("Excluir", this::excluirLinha));

        /* botões inferiores */
        JButton btnAdicionar = new JButton("Adicionar Item");
        JButton btnEmprestar = new JButton("Emprestar Item");
        JButton btnLavar = new JButton("Lavar Item");
        JButton btnVerLavagens = new JButton("Ver Lavagens");
        JButton btnVerEmprestimos = new JButton("Ver Empréstimos");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painel = new JPanel();
        painel.add(btnAdicionar);
        painel.add(btnEmprestar);
        painel.add(btnLavar);
        painel.add(btnVerEmprestimos);
        painel.add(btnVerLavagens);
        painel.add(btnVoltar);

        add(painel, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> adicionarItem());
        btnEmprestar.addActionListener(e -> emprestarItem());
        btnLavar.addActionListener(e -> lavarItem());
        btnVerLavagens.addActionListener(e -> verLavagens());
        btnVerEmprestimos.addActionListener(e -> verEmprestimos());
        btnVoltar.addActionListener(e -> voltar());

        carregar();
        setVisible(true);
    }

    private void carregar(){
        modelo.setRowCount(0);
        for(Item item: controller.getItens()){
            modelo.addRow(new Object[]{
                    item.getTipo(),
                    item.getCor(),
                    item.getTamanho(),
                    item.getLojaDeOrigem(),
                    item.getEstadoConservacao(),
                    "Editar",
                    "Excluir"});
        }
    }

    private void editarLinha(int row){
        Item item = controller.getItens().get(row);
        new FormularioEditarItem(this, controller, item);
        carregar();
    }

    private void excluirLinha(int row){
        int resposta  = JOptionPane.showConfirmDialog(this,"Excluir?","Confirma",JOptionPane.YES_NO_OPTION);
        if(resposta == JOptionPane.YES_OPTION){
            controller.removerItem(controller.getItens().get(row));
            controller.salvar();
            carregar();
        }
    }

    private void adicionarItem() {
        new FormularioAdicionarItem(this, controller);
        carregar();
    }

    private void emprestarItem() {
        int linha = tabela.getSelectedRow();
        if(linha < 0){
            JOptionPane.showMessageDialog(this, "Selecione um item para emprestar.");
        }

        Item item = controller.getItens().get(linha);
        if (!(item instanceof IEmprestavel)) {
            JOptionPane.showMessageDialog(this, "Este item não é emprestável.");
            return;
        }
        if (controller.itemEstaEmprestado(item)) {
            JOptionPane.showMessageDialog(this, "Item já está emprestado.");
            return;
        }
        String nomePessoa = JOptionPane.showInputDialog(this, "Nome de quem vai pegar emprestado:");
        if (nomePessoa != null && !nomePessoa.isBlank()) {
            controller.adicionarEmprestimo(item, nomePessoa);
            controller.salvar();
            JOptionPane.showMessageDialog(this, "Empréstimo registrado.");
        }
    }

    private void lavarItem(){
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um item para registrar a lavagem.");
            return;
        }

        Item item = controller.getItens().get(linha);

        if (!(item instanceof ILavavel)) {
            JOptionPane.showMessageDialog(this, "Este item não é lavável.");
            return;
        }

        ILavavel itemLavavel = (ILavavel) item;
        itemLavavel.registrarLavagem(LocalDate.now());
        controller.salvar();
        JOptionPane.showMessageDialog(this, "Lavagem registrada com sucesso.");
    }

    private void verEmprestimos() {
        setVisible(false);
        new TabelaDeEmprestimos(this, controller);
    }

    private void verLavagens() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um item para ver as lavagens.");
            return;
        }
        Item item = controller.getItens().get(linha);
        if(!(item instanceof ILavavel)) {
            JOptionPane.showMessageDialog(this, "Este item não é lavável.");
            return;
        }
        ILavavel itemLavavel = (ILavavel) item;
        new JanelaLavagens(this, itemLavavel);
        setVisible(false);
    }

    private void voltar() {
        dispose();
        janelaAnterior.setVisible(true);
    }

    private static class BotaoRenderer extends JButton implements TableCellRenderer{
        BotaoRenderer(String t){ setText(t); }
        public Component getTableCellRendererComponent(JTable t,Object v,boolean s,boolean f,int r,int c){
            return this;
        }
    }
    private class BotaoEditor extends DefaultCellEditor{
        private final JButton btn = new JButton(); private final Consumer<Integer> acao; private int linha;
        BotaoEditor(String t,Consumer<Integer> a){ super(new JTextField()); acao=a; btn.setText(t);
            btn.addActionListener((ActionEvent e)->{ acao.accept(linha); fireEditingStopped(); }); }
        public Component getTableCellEditorComponent(JTable t,Object v,boolean s,int r,int c){ this.linha=r; return btn; }
    }
}
