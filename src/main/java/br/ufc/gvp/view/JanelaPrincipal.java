package br.ufc.gvp.view;

import br.ufc.gvp.controller.PessoaController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

/** Tela inicial: adiciona ou seleciona pessoas. */
public class JanelaPrincipal extends JFrame {
    private final DefaultListModel<String> listaModelo = new DefaultListModel<>();
    private final JList<String> listaPessoas          = new JList<>(listaModelo);
    private final File pastaDados                     = new File("pessoas");

    public JanelaPrincipal() {
        setTitle("Gestor de Vestuário Pessoal – Pessoas");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        if (!pastaDados.exists()) pastaDados.mkdir();

        add(new JScrollPane(listaPessoas), BorderLayout.CENTER);

        /* -------- botões -------- */
        JButton nova = new JButton("Nova Pessoa");
        JButton abrir = new JButton("Abrir Armário");
        JPanel painel = new JPanel();
        painel.add(nova);
        painel.add(abrir);
        add(painel, BorderLayout.SOUTH);

        nova.addActionListener(e -> criarPessoa());
        abrir.addActionListener(e -> abrirPessoa());

        carregarLista();
        setVisible(true);
    }

    /* -------- lista arquivos .dat -------- */
    private void carregarLista() {
        listaModelo.clear();
        File[] arquivos = Objects.requireNonNull(
                pastaDados.listFiles((d, n) -> n.startsWith("dados_") && n.endsWith(".dat")));
        for (File f : arquivos)
            listaModelo.addElement(extrairNome(f.getName()));
    }

    /* -------- cria nova pessoa -------- */
    private void criarPessoa() {
        String nome = JOptionPane.showInputDialog(this, "Nome da pessoa:");
        if (nome == null || nome.isBlank()) return;
        if (listaModelo.contains(nome)) {
            JOptionPane.showMessageDialog(this, "Pessoa já existe!");
            return;
        }
        // cria controler e salva arquivo inicial
        PessoaController pc = new PessoaController(nome.trim());
        pc.salvar();
        carregarLista();
    }

    /* -------- abre armário -------- */
    private void abrirPessoa() {
        String nome = listaPessoas.getSelectedValue();
        if (nome == null) {
            JOptionPane.showMessageDialog(this, "Selecione alguém.");
            return;
        }
        PessoaController pc = new PessoaController(nome);
        new TabelaDeRoupas(pc);
    }

    /* util p/ remover prefixo/sufixo */
    private String extrairNome(String arquivo) {
        return arquivo.replace("dados_", "").replace(".dat", "")
                .replace("_", " ").trim();
    }
}
