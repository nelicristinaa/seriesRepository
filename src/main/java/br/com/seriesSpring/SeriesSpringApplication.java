package br.com.seriesSpring;

import br.com.seriesSpring.model.DadosSerie;
import br.com.seriesSpring.service.ConsumoAPI;
import br.com.seriesSpring.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeriesSpringApplication implements CommandLineRunner
{

	public static void main(String[] args) {
		SpringApplication.run(SeriesSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello World");
		ConsumoAPI consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadosSerie serie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(serie);
	}
}
