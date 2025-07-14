package br.ufc.gvp.controller;

import br.ufc.gvp.model.item.Item;
import br.ufc.gvp.model.look.Look;
import br.ufc.gvp.model.pessoa.Pessoa;
import br.ufc.gvp.utils.Persistencia;

import java.util.List;

public class PessoaController {
    private Pessoa pessoa;
    private final String caminhoArquivo = "dados.dat";

    public PessoaController(String nomePessoa) {
        this.pessoa = Persistencia.carregar(caminhoArquivo);
        if (this.pessoa == null) {
            this.pessoa = new Pessoa(nomePessoa);
        }
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public List<Item> getItens() {
        return pessoa.getItens();
    }

    public List<Look> getLooks() {
        return pessoa.getLooks();
    }

    public void adicionarItem(Item item) {
        pessoa.adicionarItem(item);
        salvar();
    }

    public void removerItem(Item item) {
        pessoa.removerItem(item);
        salvar();
    }

    public void adicionarLook(Look look) {
        pessoa.adicionarLook(look);
        salvar();
    }

    public void removerLook(Look look) {
        pessoa.removerLook(look);
        salvar();
    }

    public void salvar() {
        Persistencia.salvar(pessoa, caminhoArquivo);
    }
}
