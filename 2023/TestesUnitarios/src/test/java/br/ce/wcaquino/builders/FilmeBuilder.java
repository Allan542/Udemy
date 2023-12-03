package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;

public class FilmeBuilder {
    private Filme filme;
    private FilmeBuilder(){}

    public static FilmeBuilder umFilme() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(2);
        builder.filme.setNome("Harry Potter");
        builder.filme.setPrecoLocacao(10.00);
        return builder;
    }

    public static FilmeBuilder umFilmeSemEstoque() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(0);
        builder.filme.setNome("Velozes e furiosos");
        builder.filme.setPrecoLocacao(5.00);
        return builder;
    }

    public static FilmeBuilder filmeDois() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(1);
        builder.filme.setNome("Velozes e furiosos");
        builder.filme.setPrecoLocacao(5.00);
        return builder;
    }

    public static FilmeBuilder filmeTres() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(3);
        builder.filme.setNome("O Pequeno Stuart Little");
        builder.filme.setPrecoLocacao(15.00);
        return builder;
    }

    public static FilmeBuilder filmeQuatro() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(20);
        builder.filme.setNome("Marley e eu");
        builder.filme.setPrecoLocacao(6.00);
        return builder;
    }

    public static FilmeBuilder filmeCinco() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(5);
        builder.filme.setNome("As Crônicas de Narnia");
        builder.filme.setPrecoLocacao(20.00);
        return builder;
    }

    public static FilmeBuilder filmeSeis() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(4);
        builder.filme.setNome("Os Vingadores: Guerra Infinita");
        builder.filme.setPrecoLocacao(30.00);
        return builder;
    }

    public static FilmeBuilder filmeSete() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(1);
        builder.filme.setNome("Os Vingadores: Ultimato");
        builder.filme.setPrecoLocacao(40.00);
        return builder;
    }

    public FilmeBuilder semEstoque() {
        filme.setEstoque(0);
        return this;
    }

    public FilmeBuilder comValor(Double valor) {
        filme.setPrecoLocacao(valor);
        return this;
    }

    public Filme agora() {
        return filme;
    }
}

