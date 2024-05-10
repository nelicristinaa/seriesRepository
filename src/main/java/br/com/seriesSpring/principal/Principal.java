package br.com.seriesSpring.principal;

import br.com.seriesSpring.model.DadosEpisodio;
import br.com.seriesSpring.model.DadosSerie;
import br.com.seriesSpring.model.DadosTemporada;
import br.com.seriesSpring.model.Episodio;
import br.com.seriesSpring.service.ConsumoAPI;
import br.com.seriesSpring.service.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);

    private final String  ENDERECO = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=6585022c";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() throws JsonProcessingException {
        System.out.println("Digite o nome da serie");


        var nomeSerie = scanner.nextLine();
        nomeSerie =  nomeSerie.replace(" ", "+");

        var json = consumoAPI.obterDados( ENDERECO+ nomeSerie + APIKEY);
        DadosSerie serie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(serie);


        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= serie.totalTemporadas(); i++){
            json = consumoAPI.obterDados(ENDERECO + nomeSerie + "&season=" + i + APIKEY);
            DadosTemporada temporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(temporada);
        }




    //    temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());


        dadosEpisodios.stream()
                 .filter(e -> !e.avaliacao().equals("N/A"))
                 .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                 .limit(5)
                 .forEach(System.out::println);


        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                    .map(d -> new Episodio(t.numeroTemporada(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de qual ano você quer ver os episodios");
        var ano = scanner.nextInt();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> !e.getDataAvaliacao().equals(null) && e.getDataAvaliacao().isAfter(dataBusca))
                .forEach(e -> System.out.println("temporada: " + e.getTemporada() + " titulo: " + e.getTitulo() + " data: " + e.getDataAvaliacao().format(formatter)));

    }
}
