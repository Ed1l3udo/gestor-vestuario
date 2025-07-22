package br.ufc.gvp.view;

import br.ufc.gvp.controller.PessoaController;
import br.ufc.gvp.model.item.Item;

import javax.swing.*;
import java.awt.*;

public class FormularioEditarItem extends JDialog {
    private final JTextField cor=new JTextField(), tam=new JTextField(), loja=new JTextField(), est=new JTextField();

    public FormularioEditarItem(JFrame parent, PessoaController ctrl, Item item){
        super(parent,"Editar Item",true);
        setSize(420,300);
        setLayout(new BorderLayout());

        cor.setText(item.getCor());
        tam.setText(item.getTamanho());
        loja.setText(item.getLojaDeOrigem());
        est.setText(item.getEstadoConservacao());

        JPanel grid=new JPanel(new GridLayout(5,2,5,5));
        grid.add(new JLabel("Cor:"));     grid.add(cor);
        grid.add(new JLabel("Tamanho:")); grid.add(tam);
        grid.add(new JLabel("Loja:"));    grid.add(loja);
        grid.add(new JLabel("Estado:"));  grid.add(est);
        add(grid,BorderLayout.CENTER);

        JButton salvar=new JButton("Salvar");
        salvar.addActionListener(e->{
            item.setCor(cor.getText());
            item.setTamanho(tam.getText());
            item.setLojaDeOrigem(loja.getText());
            item.setEstadoConservacao(est.getText());
            ctrl.salvar();
            dispose();
        });
        add(salvar,BorderLayout.SOUTH);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
