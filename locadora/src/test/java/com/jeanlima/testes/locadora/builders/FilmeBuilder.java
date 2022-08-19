package com.jeanlima.testes.locadora.builders;

import com.jeanlima.testes.locadora.entidades.Filme;

public class FilmeBuilder {

    private Filme filme;

    private FilmeBuilder(){}

    public static FilmeBuilder umFilme(){
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(2);
        builder.filme.setNome("Filme 1");
        //builder.filme.setPrecoLocacao(5.0);
        builder.filme.setPrecoLocacao(4.0);
        return builder;
    }

    public FilmeBuilder semEstoque(){
        filme.setEstoque(0);
        return this;
    }

    public FilmeBuilder comValor(Double valor){
        filme.setPrecoLocacao(valor);
        return this;
    }

    public Filme agora(){
        return filme;
    }

    /*
     * Segundo método de entrada :filme sem estroque
     */

     public static FilmeBuilder umFilmeSemEstoque(){
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(0);
        builder.filme.setNome("Filme 1");
        //builder.filme.setPrecoLocacao(5.0);
        builder.filme.setPrecoLocacao(4.0);
        return builder;
     }
    
}
