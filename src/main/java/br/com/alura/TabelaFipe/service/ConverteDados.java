package br.com.alura.TabelaFipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.Collections;
import java.util.List;

public class ConverteDados implements IConverteDados{
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try{
            return mapper.readValue(json, classe);
        } catch(JsonProcessingException e){
            throw new RuntimeException();
        }
    }

    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        //para construir uma coleção  a partir da classe que eu passar
        CollectionType lista = mapper.getTypeFactory()
                //contrói a coleção/-lista baseado naquilo que se passar, está definido como genérico na interface
                .constructCollectionType(List.class, classe);
    //foi feito o try porque foi indicado que poderia dar uma exceção
        try {
            return mapper.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
