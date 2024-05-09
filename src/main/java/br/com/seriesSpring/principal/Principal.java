package br.com.seriesSpring.principal;

import br.com.seriesSpring.model.DadosSerie;
import br.com.seriesSpring.model.DadosTemporada;
import br.com.seriesSpring.service.ConsumoAPI;
import br.com.seriesSpring.service.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);

    private final String  ENDERECO = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=6585022c";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() throws JsonProcessingException {
        System.out.println("Digite o nome da serie");


        var nomeSerie = scanner.nextLine();
        var json = consumoAPI.obterDados( ENDERECO+ nomeSerie + APIKEY);
        DadosSerie serie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(serie);


        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= serie.totalTemporadas(); i++){
            json = consumoAPI.obterDados(ENDERECO + nomeSerie + "&season=" + i + APIKEY);
            DadosTemporada temporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(temporada);
        }

        temporadas.forEach(System.out::println);

    }
}
