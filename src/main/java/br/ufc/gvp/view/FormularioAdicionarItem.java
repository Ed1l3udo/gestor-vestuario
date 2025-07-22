package br.ufc.gvp.view;

import br.ufc.gvp.controller.PessoaController;
import br.ufc.gvp.model.item.*;
import br.ufc.gvp.model.item.acessorios.*;
import br.ufc.gvp.model.item.calcados.*;
import br.ufc.gvp.model.item.roupas.*;
import br.ufc.gvp.model.item.roupasIntimas.*;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class FormularioAdicionarItem extends JDialog {

    private final JTextField campoCor     = new JTextField();
    private final JTextField campoTamanho = new JTextField();
    private final JTextField campoLoja    = new JTextField();
    private final JTextField campoEstado  = new JTextField();

    /** Enum interno que associa rótulo → construtor de Item */
    private enum TipoItem {
        CAMISA   ("Camisa", args -> new Camisa  (args[0], args[1], args[2], args[3])),
        CALCA    ("Calça", args -> new Calca   (args[0], args[1], args[2], args[3])),
        SAIA     ("Saia", args -> new Saia    (args[0], args[1], args[2], args[3])),
        CASACO   ("Casaco", args -> new Casaco  (args[0], args[1], args[2], args[3])),
        TENIS    ("Tênis", args -> new Tenis   (args[0], args[1], args[2], args[3])),
        SANDALIA ("Sandália", args -> new Sandalia(args[0], args[1], args[2], args[3])),
        RELOGIO  ("Relógio", args -> new Relogio (args[0], args[1], args[2], args[3])),
        PULSEIRA ("Pulseira", args -> new Pulseira(args[0], args[1], args[2], args[3])),
        ANEL ("Pulseira", args -> new Pulseira(args[0], args[1], args[2], args[3])),
        COLAR ("Colar", args -> new Colar(args[0], args[1], args[2], args[3])),
        BOTA ("Bota", args -> new Bota(args[0], args[1], args[2], args[3])),
        SAPATILHA ("Sapatilha", args -> new Sapatilha(args[0], args[1], args[2], args[3])),
        SAPATO ("Sapato", args -> new Sapato(args[0], args[1], args[2], args[3])),
        CAMISETA ("Camiseta", args -> new Camiseta(args[0], args[1], args[2], args[3])),
        VESTIDO ("Vestido", args -> new Vestido(args[0], args[1], args[2], args[3])),
        CALCINHA ("Calcinha", args -> new Calcinha(args[0], args[1], args[2], args[3])),
        SUTIA ("Sutiã", args -> new Sutia(args[0], args[1], args[2], args[3])),
        CUECA    ("Cueca", args -> new Cueca(args[0], args[1], args[2], args[3]));


        final String rotulo;
        final Function<String[], Item> factory;
        TipoItem(String rotulo, Function<String[], Item> factory) {
            this.rotulo = rotulo;
            this.factory = factory;
        }
        @Override public String toString() { return rotulo; }
    }

    public FormularioAdicionarItem(JFrame parent, PessoaController controller) {
        super(parent, "Adicionar Item", true);
        setSize(420, 320);
        setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel(new GridLayout(6, 2, 5, 5));
        JComboBox<TipoItem> comboTipo = new JComboBox<>(TipoItem.values());

        painelCampos.add(new JLabel("Tipo:"));
        painelCampos.add(comboTipo);
        painelCampos.add(new JLabel("Cor:"));
        painelCampos.add(campoCor);
        painelCampos.add(new JLabel("Tamanho:"));
        painelCampos.add(campoTamanho);
        painelCampos.add(new JLabel("Loja de origem:"));
        painelCampos.add(campoLoja);
        painelCampos.add(new JLabel("Estado:"));
        painelCampos.add(campoEstado);


        add(painelCampos, BorderLayout.CENTER);

        JButton botaoSalvar = new JButton("Salvar");
        add(botaoSalvar, BorderLayout.SOUTH);

        botaoSalvar.addActionListener(e -> {
            String[] args = {campoCor.getText(), campoTamanho.getText(), campoLoja.getText(), campoEstado.getText()};

            Item novoItem = ((TipoItem) comboTipo.getSelectedItem()).factory.apply(args);
            controller.adicionarItem(novoItem);
            dispose();
        });

        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
