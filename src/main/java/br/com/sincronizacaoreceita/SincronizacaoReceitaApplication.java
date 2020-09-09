package br.com.sincronizacaoreceita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import br.com.sincronizacaoreceita.beans.SincronizacaoReceita;

@SpringBootApplication
public class SincronizacaoReceitaApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SincronizacaoReceitaApplication.class, args);
		System.out.println("******************* INÍCIO DO PROCESSAMENTO ***************");
		SincronizacaoReceita sincronizacaoReceita = context.getBean(SincronizacaoReceita.class);
		sincronizacaoReceita.executarSincronizacaoReceita(args[1]);
		System.out.println("******************* FIM DO PROCESSAMENTO ***************");
	}

}
