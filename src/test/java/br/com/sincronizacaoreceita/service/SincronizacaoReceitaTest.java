package br.com.sincronizacaoreceita.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SincronizacaoReceitaTest {

	@Test
	public void sincronizacaoReceitaSucessoTest() throws Exception {
		ReceitaService receitaService = new ReceitaService();
		boolean processarConta = receitaService.atualizarConta("0101", "12225-6".replace("-", ""), 100, "A");
		assertEquals(true, processarConta);
	}
	
	@Test
	public void sincronizacaoReceitaFalhaTest() throws Exception {
		ReceitaService receitaService = new ReceitaService();
		boolean processarConta = receitaService.atualizarConta("101", "12225-6".replace("-", ""), 100, "A");
		assertEquals(false, processarConta);
	}
}
