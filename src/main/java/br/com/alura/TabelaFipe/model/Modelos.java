package br.com.alura.TabelaFipe.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
//como está representando com o mesmo nome que já vem na API não precisa do @JasonAlias  "modelos"

//ignorando especificamente a propriedade "anos"
@JsonIgnoreProperties(ignoreUnknown = true)
//o meu modelo é um List de dados
public record Modelos(List<Dados> modelos) {
}
