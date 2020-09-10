package br.com.sincronizacaoreceita;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import br.com.sincronizacaoreceita.beans.SincronizacaoReceita;
import br.com.sincronizacaoreceita.exception.CabecalhoException;

@SpringBootApplication
public class SincronizacaoReceitaApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SincronizacaoReceitaApplication.class);

	public static void main(String[] args) throws CabecalhoException {

		ApplicationContext context = SpringApplication.run(SincronizacaoReceitaApplication.class, args);
		LOGGER.info("******************* INÍCIO DO PROCESSAMENTO ***************");
		SincronizacaoReceita sincronizacaoReceita = context.getBean(SincronizacaoReceita.class);
		String nameFile = null;
		if (args.length == 2) {
			for (String arg : args) {
				if (!arg.startsWith("--spring.output.ansi.enabled")) {
					nameFile = arg;
					break;
				}
			}
			sincronizacaoReceita.executarSincronizacaoReceita(nameFile);
			LOGGER.info("******************* FIM DO PROCESSAMENTO ***************");
		} else {
			LOGGER.error("Número de Angumentos Inválido para processamento do arquivo.");
		}

	}

}
