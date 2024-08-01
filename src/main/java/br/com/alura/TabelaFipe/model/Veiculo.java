package br.com.alura.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//está ignorando o que não foi mapeado aqui, pois há outros atributos que não se fizeram interessantes trazer
@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(
// precisa fazer a troca se não ele não traz o valor, visto que não estão iguais na escrita.
                @JsonAlias("Valor") String valor,
                @JsonAlias("Marca") String marca,
                @JsonAlias("Modelo") String modelo,
                @JsonAlias("AnoModelo") Integer ano,
                @JsonAlias("Combustivel") String combustivel
){
}
