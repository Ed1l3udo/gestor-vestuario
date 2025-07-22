package br.ufc.gvp.controller;

import br.ufc.gvp.model.item.Item;
import br.ufc.gvp.model.item.interfaces.IEmprestavel;
import br.ufc.gvp.model.look.Look;
import br.ufc.gvp.model.pessoa.Pessoa;
import br.ufc.gvp.utils.Persistencia;
import br.ufc.gvp.model.emprestimo.Emprestimo;

import java.util.List;

public class PessoaController {
    private Pessoa pessoa;
    private final String caminhoArquivo;

    public PessoaController(String nomePessoa) {
        this.caminhoArquivo = "pessoas/dados_" + nomePessoa.toLowerCase().replace(" ", "_") + ".dat";
        this.pessoa = Persistencia.carregar(caminhoArquivo);
        if (this.pessoa == null) this.pessoa = new Pessoa(nomePessoa);
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
    public List<Item>  getItens() {
        return pessoa.getItens();
    }
    public List<Look>  getLooks() {
        return pessoa.getLooks();
    }
    public List<Emprestimo> getEmprestimos() {
        return pessoa.getEmprestimos();
    }

    public void adicionarItem(Item i) {
        pessoa.adicionarItem(i);
        salvar();
    }
    public void removerItem(Item i) {
        pessoa.removerItem(i);
        salvar();
    }
    public void adicionarLook(Look l) {
        pessoa.adicionarLook(l);
        salvar();
    }
    public void removerLook(Look l) {
        pessoa.removerLook(l);
        salvar();
    }
    public void salvar() {
        Persistencia.salvar(pessoa, caminhoArquivo);
    }

    public void adicionarEmprestimo(Item item, String nomePessoa) {
        pessoa.adicionarEmprestimo(new Emprestimo(item, nomePessoa));
        ((IEmprestavel) item).registrarEmprestimo(nomePessoa);
        salvar();
    }

    public boolean itemEstaEmprestado(Item item) {
        return ((IEmprestavel) item).estaEmprestado();
    }
}
