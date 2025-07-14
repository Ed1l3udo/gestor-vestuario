package br.ufc.gvp.view;

import br.ufc.gvp.controller.PessoaController;
import br.ufc.gvp.model.item.Item;

import javax.swing.*;
import java.awt.*;

public class FormularioEditarItem extends JDialog {
    private final JTextField cor=new JTextField(), tam=new JTextField(),
            loja=new JTextField(), est=new JTextField(), img=new JTextField();

    public FormularioEditarItem(JFrame parent, PessoaController ctrl, Item it){
        super(parent,"Editar Item",true);
        setSize(420,300);
        setLayout(new BorderLayout());

        cor.setText(it.getCor()); tam.setText(it.getTamanho());
        loja.setText(it.getLojaDeOrigem()); est.setText(it.getEstadoConservacao());
        img.setText(it.getImagem());

        JPanel grid=new JPanel(new GridLayout(5,2,5,5));
        grid.add(new JLabel("Cor:"));     grid.add(cor);
        grid.add(new JLabel("Tamanho:")); grid.add(tam);
        grid.add(new JLabel("Loja:"));    grid.add(loja);
        grid.add(new JLabel("Estado:"));  grid.add(est);
        grid.add(new JLabel("Imagem:"));  grid.add(img);
        add(grid,BorderLayout.CENTER);

        JButton salvar=new JButton("Salvar");
        salvar.addActionListener(e->{
            it.setCor(cor.getText());
            it.setTamanho(tam.getText());
            it.setLojaDeOrigem(loja.getText());
            it.setEstadoConservacao(est.getText());
            it.setImagem(img.getText());
            ctrl.salvar();
            dispose();
        });
        add(salvar,BorderLayout.SOUTH);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
