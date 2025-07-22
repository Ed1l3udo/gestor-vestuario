package br.ufc.gvp.view;

import br.ufc.gvp.controller.PessoaController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class JanelaPrincipal extends JFrame {
    private final DefaultListModel<String> listaModelo = new DefaultListModel<>();
    private final JList<String> listaPessoas = new JList<>(listaModelo);
    private final File pastaDados = new File("pessoas");

    public JanelaPrincipal() {
        setTitle("Gestor de Vestuário Pessoal – Pessoas");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        if (!pastaDados.exists()) pastaDados.mkdir();

        add(new JScrollPane(listaPessoas), BorderLayout.CENTER);

        JButton btnNova = new JButton("Nova Pessoa");
        JButton btnExcluir = new JButton("Excluir Pessoa");
        JButton btnAbrirArmario = new JButton("Abrir Armário");
        JButton btnAbrirLooks = new JButton("Abrir Looks");
        JButton btnAbrirEstatisticas = new JButton("Abrir Estatisticas");


        JPanel painel = new JPanel();
        painel.add(btnNova);
        painel.add(btnExcluir);
        painel.add(btnAbrirArmario);
        painel.add(btnAbrirLooks);
        painel.add(btnAbrirEstatisticas);
        add(painel, BorderLayout.SOUTH);

        btnNova.addActionListener(e -> criarPessoa());
        btnExcluir.addActionListener(e -> excluirPessoa());
        btnAbrirArmario.addActionListener(e -> abrirArmario());
        btnAbrirLooks.addActionListener(e -> abrirLooks());
        btnAbrirEstatisticas.addActionListener(e -> abrirEstatisticas());

        carregarLista();
        setVisible(true);
    }

    private void carregarLista() {
        listaModelo.clear();
        File[] arquivos = Objects.requireNonNull(pastaDados.listFiles((d, n) -> n.startsWith("dados_") && n.endsWith(".dat")));for (File f : arquivos) listaModelo.addElement(extrairNome(f.getName()));
    }

    private void criarPessoa() {
        String nome = JOptionPane.showInputDialog(this, "Nome da pessoa:");
        if (nome == null || nome.isBlank()) return;
        if (listaModelo.contains(nome)) {
            JOptionPane.showMessageDialog(this, "Pessoa já existe!");
            return;
        }

        PessoaController pc = new PessoaController(nome.trim());
        pc.salvar();
        carregarLista();
    }

    private void excluirPessoa(){
        String nome = listaPessoas.getSelectedValue();
        if (nome == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir \"" + nome + "\"?\nEssa ação é irreversível.", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            File arquivo = new File(pastaDados, "dados_" + nome.replace(" ", "_") + ".dat");

            if (!(arquivo.exists())) {
                JOptionPane.showMessageDialog(this, "Arquivo não encontrado.");
                return;
            }

            if (arquivo.delete()) {
                JOptionPane.showMessageDialog(this, "Pessoa excluída com sucesso.");
                carregarLista();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir o arquivo.");
            }
        }
    }


    private void abrirArmario() {
        String nome = listaPessoas.getSelectedValue();
        if (nome == null) {
            JOptionPane.showMessageDialog(this, "Selecione alguém.");
            return;
        }
        PessoaController pc = new PessoaController(nome);
        new TabelaDeRoupas(this, pc);
        setVisible(false);
    }

    private void abrirLooks() {
        String nome = listaPessoas.getSelectedValue();
        if (nome == null) {
            JOptionPane.showMessageDialog(this, "Selecione alguém.");
            return;
        }
        PessoaController pc = new PessoaController(nome);
        new TabelaDeLooks(this, pc);
        setVisible(false);
    }

    private void abrirEstatisticas()
    {
        String nome = listaPessoas.getSelectedValue();
        if (nome == null) {
            JOptionPane.showMessageDialog(this, "Selecione alguém.");
            return;
        }
        PessoaController pc = new PessoaController(nome);
        new JanelaEstatisticas(this, pc);
        setVisible(false);
    }

    private String extrairNome(String arquivo) {
        return arquivo.replace("dados_", "").replace(".dat", "").replace("_", " ").trim();
    }


}
