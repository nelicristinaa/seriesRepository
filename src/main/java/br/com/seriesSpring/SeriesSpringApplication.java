package br.com.seriesSpring;

import br.com.seriesSpring.model.DadosEpisodio;
import br.com.seriesSpring.model.DadosSerie;
import br.com.seriesSpring.model.DadosTemporada;
import br.com.seriesSpring.principal.Principal;
import br.com.seriesSpring.service.ConsumoAPI;
import br.com.seriesSpring.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SeriesSpringApplication implements CommandLineRunner
{

	public static void main(String[] args) {
		SpringApplication.run(SeriesSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal();
		principal.exibeMenu();

	}
}
