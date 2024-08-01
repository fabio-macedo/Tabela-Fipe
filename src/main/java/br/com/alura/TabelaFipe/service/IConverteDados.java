package br.com.alura.TabelaFipe.service;

import java.util.List;

public interface IConverteDados {
//obtenho um dado, que é uma representação de uma unidade daquele tipo, seja record ou uma classe
    <T> T obterDados(String json, Class<T> classe);

//não quero um dado qualquer de uma classe e sim uma lista daquele dado
//tipo genérico dizendo que vai devolver uma lista de alguma coisa, que não sei o que é, quem chamar o método quem vai dizer
    <T> List<T> obterLista(String json, Class<T> classe);
}
