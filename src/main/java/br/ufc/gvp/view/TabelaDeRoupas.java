package br.ufc.gvp.view;

import br.ufc.gvp.controller.PessoaController;
import br.ufc.gvp.model.item.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.function.Consumer;

public class TabelaDeRoupas extends JFrame {
    private final PessoaController controller;
    private final DefaultTableModel modelo;
    private final JTable tabela;

    public TabelaDeRoupas(PessoaController controller) {
        this.controller = controller;

        setTitle("Armário de " + controller.getPessoa().getNome());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        JButton add = new JButton("Adicionar Item");
        JButton salvar = new JButton("Salvar Dados");
        JPanel p = new JPanel();
        p.add(add); p.add(salvar);
        add(p, BorderLayout.SOUTH);

        add.addActionListener(e -> { new FormularioAdicionarItem(this, controller); carregar(); });
        salvar.addActionListener(e -> { controller.salvar(); JOptionPane.showMessageDialog(this,"Salvo!"); });

        carregar();
        setVisible(true);
    }

    private void carregar(){
        modelo.setRowCount(0);
        for(Item it: controller.getItens()){
            modelo.addRow(new Object[]{
                    it.getTipo(), it.getCor(), it.getTamanho(),
                    it.getLojaDeOrigem(), it.getEstadoConservacao(),
                    "Editar","Excluir"});
        }
    }
    private void editarLinha(int row){
        Item it = controller.getItens().get(row);
        new FormularioEditarItem(this, controller, it);
        carregar();
    }
    private void excluirLinha(int row){
        int resp = JOptionPane.showConfirmDialog(this,"Excluir?","Confirma",JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION){ controller.getItens().remove(row); controller.salvar(); carregar(); }
    }

    /* ----- botão em célula ----- */
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
