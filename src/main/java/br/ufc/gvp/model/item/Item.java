package br.ufc.gvp.model.item;

import java.io.Serializable;

public abstract class Item implements Serializable {
    private String nome;
    private String cor;
    private String tamanho;
    private String lojaDeOrigem;
    private String estadoConservacao;
    private String imagem;

    public Item(String nome, String cor, String tamanho, String lojaDeOrigem, String estadoConservacao, String imagem) {
        this.nome = nome;
        this.cor = cor;
        this.tamanho = tamanho;
        this.lojaDeOrigem = lojaDeOrigem;
        this.estadoConservacao = estadoConservacao;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTamanho() {
        return tamanho;
    }
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getLojaDeOrigem() {
        return lojaDeOrigem;
    }
    public void setLojaDeOrigem(String lojaDeOrigem) {
        this.lojaDeOrigem = lojaDeOrigem;
    }

    public String getEstadoConservacao() {
        return estadoConservacao;
    }
    public void setEstadoConservacao(String estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public abstract String getTipo();
}