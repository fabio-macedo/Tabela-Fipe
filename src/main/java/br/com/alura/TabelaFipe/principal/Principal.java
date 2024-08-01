package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.model.Veiculo;
import br.com.alura.TabelaFipe.service.ConsumoAPI;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){
        var menu = """
                *** OPÇÕES ***
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar: 
                """;

        System.out.println(menu);
        var opcao = sc.nextLine();

//https://parallelum.com.br/fipe/api/v1/carros/marcas/59/modelos/5940/anos

        //criando o endereço com base no escolhido pelo usuário
        String endereco;

        if(opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")){
            endereco = URL_BASE + "motos/marcas";
        }else {
            endereco = URL_BASE + "caminhoes/marcas";
        }
//aqui obteve-se os dados
        var json = consumo.obterDados(endereco);
        System.out.println(json);
//transformando os dados numa estrutura conhecida
//vai devolver um List com uma estrutura chamada 'Dados'
        var marcas = conversor.obterLista(json, Dados.class);
    //exibindo por ordem do código
        marcas.stream()
                //para comparar   ------------    ordenar pelo código
                .sorted(Comparator.comparing((Dados::codigo)))
                .forEach(System.out::println);

//agora pede ao usuário a marca que ele quer pelo código que foi exibido na tela

        System.out.println("\nInforme o código da marca para consulta");
        var codigoMarca = sc.nextLine();
//apos escolher a marca precisa fazer uma nova requisição

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
    //agora vai converter como uma lista de modelos
        //modelo já é uma lista então usa obterDados pois já tem a representação de lista
        var modeloLista = conversor.obterDados(json, Modelos.class);
// o modelo da lista possui varios modelos no json como retorno da escolha da marca
        System.out.println("\nModelos dessa marca");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

//OBS: o json retorna uma chave-valor:  a chave é o 'modelos' e o valor é uma lista de modelos representado pela classe Dados


    // BUSCANDO TODAS AS AVALIAÇÕES DO VEÍCULO...

        System.out.println("\nDigite um trecho do nome do carro a ser buscado");
        var nomeVeiculo = sc.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
/*
pegando a lista de modelos que foi filtrada pela marca, filtrando todos que tenham nome parecido com o digitado agora
e colocando numa nova lista
*/
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                        .collect(Collectors.toList());

        System.out.println("\nModelos filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite por favor o código do modelo para buscar os valores de avaliação");
        var codigoModelo = sc.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
//como tem código e nome chamamos de list de Dados...  e só tem código e nome e não representação de lista, como no modelo.
        List<Dados> anos = conversor.obterLista(json, Dados.class);

//se um carro tem tres anos por exemplo de avaliação, vou percorrer esses anos e mostrar essas avaliações pro usuário ver todas
        //além de fazer uma estrutura de repetição buscando a avaliação por ano, precisa também representar o veículo.
        //visto que no resultado final terá nome, ano, valor, combustível, etc, então precisa de uma classe que o represente

//percorrer a lista de anos, busque o dado do carro e jogue numa lista para no final apresentar

        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
//buscando no final o código em cada índice, percorre e pega o código do ano que está no indice 0, dps no 1 etc...
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veículos filtrados com avaliações por ano:");
        veiculos.forEach(System.out::println);

        }

    }

