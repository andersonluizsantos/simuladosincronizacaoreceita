package br.com.sincronizacaoreceita.beans;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Component;

import br.com.sincronizacaoreceita.service.ReceitaService;

@Component
public class SincronizacaoReceita {
	
	public void executarSincronizacaoReceita() {
		this.lerArquivo();
	}
	
	public void lerArquivo() {

		System.out.println("Diretório" + System.getProperty("user.dir"));
		String diretorio = System.getProperty("user.dir") + "\\src\\";
		String cvsArquivo = diretorio + "dados.csv";
		System.out.println("Caminho do arquivo ---> " + cvsArquivo);
		BufferedReader conteudoCVS = null;
		String linha = "";
		String cvsSeparadorCampo = ";";
		try {

			conteudoCVS = new BufferedReader(new FileReader(cvsArquivo));
			while ((linha = conteudoCVS.readLine()) != null) {

				String[] dados = linha.split(cvsSeparadorCampo);
				processarArquivo(dados);

			}

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo Não encontrado: \n" + e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("IndexOutOfBounds: \n" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO Error: \n" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (conteudoCVS != null) {
				try {
					conteudoCVS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void processarArquivo(String[] dados) {
		ReceitaService receitaService = new ReceitaService();
		boolean processsado;
		try {
			if (!dados[0].equalsIgnoreCase("agencia")) {
				double saldo = this.convertValue(dados[2]); 
				processsado = receitaService.atualizarConta(dados[0], dados[1].replace("-", ""),
						saldo, dados[3]);
				System.out.println("[" + "Agência = " + dados[0] + " , " + "Conta =" + dados[1] + " , "
						+ "Saldo =" + dados[2] + " , " + "Status =" + dados[3] + " , " + "Processado ="
						+ processsado + "" + "]");
			}

		} catch (RuntimeException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public double convertValue(String value) {
		return Double.parseDouble(value.replace(",", "."));
	}

}
