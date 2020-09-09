package br.com.sincronizacaoreceita.beans;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.sincronizacaoreceita.service.ReceitaService;

@Component
public class SincronizacaoReceita {
	
	public void executarSincronizacaoReceita(String fileName) {
		this.lerArquivo(fileName);
	}
	
	public void lerArquivo(String fileName) {
		System.out.println("");
		String diretorio = System.getProperty("user.dir") + "\\";
		String cvsArquivo = diretorio + fileName;
		System.out.println("Lendo o arquivo no diretório: " + cvsArquivo);
		System.out.println("");
		BufferedReader conteudoCVS = null;
		String linha = "";
		String cvsSeparadorCampo = ";";
		try {

			conteudoCVS = new BufferedReader(new FileReader(cvsArquivo));
			List<String[]> linhas = new ArrayList();
			while ((linha = conteudoCVS.readLine()) != null) {
				String[] dados = linha.split(cvsSeparadorCampo);
				processarArquivo(dados, linhas);
			}
			criarArquivoCVS(linhas, fileName);

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
	
	private void criarArquivoCVS(List<String[]> linhas, String fileName) {
		System.out.println("");
		String diretorio = System.getProperty("user.dir") + "\\";
		
		String cvsArquivo = diretorio + getFileName(fileName) + "-saida" + "." + getFileExtension(fileName);
		System.out.println("CRIANDO ARQUIVO: " + cvsArquivo);
		System.out.println("");
		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter(cvsArquivo);
			csvWriter.append("agencia");
			csvWriter.append(";");
			csvWriter.append("conta");
			csvWriter.append(";");
			csvWriter.append("saldo");
			csvWriter.append(";");
			csvWriter.append("status");
			csvWriter.append(";");
			csvWriter.append("Processamento");
			csvWriter.append("\n");
			
			for (String[] rowData : linhas) {
			    csvWriter.append(String.join(";", rowData));
			    csvWriter.append("\n");
			}

			csvWriter.flush();
			csvWriter.close();	
			System.out.println("ARQUIVO GRAVADO COM SUCESSO: " + cvsArquivo);
			System.out.println("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void processarArquivo(String[] dados, List<String[]> linhas) {
		ReceitaService receitaService = new ReceitaService();
		boolean processsado;
		try {
			if (!dados[0].equalsIgnoreCase("agencia")) {
				double saldo = this.convertValue(dados[2]); 
				processsado = receitaService.atualizarConta(dados[0], dados[1].replace("-", ""),
						saldo, dados[3]);
				System.out.println("[" + "Agência = " + dados[0] + " , " + "Conta = " + dados[1] + " , "
						+ "Saldo = " + dados[2] + " , " + "Status = " + dados[3] + " , " + "Processado = "
						+ processsado + "" + "]");
				
				String resultadoProcessamento = (processsado) ? "OK" : "ERROR";
				
				linhas.add(new String[] {dados[0], dados[1], dados[2], dados[3], resultadoProcessamento});
			}

		} catch (RuntimeException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public double convertValue(String value) {
		return Double.parseDouble(value.replace(",", "."));
	}
	
	public String getFileExtension(String filename) {
        if (filename.contains("."))
            return filename.substring(filename.lastIndexOf(".") + 1);
        else
            return "";
    }
	
	public String getFileName(String filename) {
        if (filename.contains("."))
            return filename.substring(0, filename.lastIndexOf("."));
        else
            return "";
    }
}
