package br.com.sincronizacaoreceita.beans;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.sincronizacaoreceita.exception.CabecalhoException;
import br.com.sincronizacaoreceita.service.ReceitaService;
import br.com.sincronizacaoreceita.util.Constants;
import br.com.sincronizacaoreceita.util.StringUtils;

@Component
public class SincronizacaoReceita {

	private static final Logger LOGGER = LoggerFactory.getLogger(SincronizacaoReceita.class);

	public void executarSincronizacaoReceita(String fileName) throws CabecalhoException {
		this.lerArquivo(fileName);
	}

	public void lerArquivo(String fileName) throws CabecalhoException {
		String diretorio = System.getProperty(Constants.DIRETORIO_APLICACAO) + "\\";
		String cvsArquivo = diretorio + fileName;
		LOGGER.info("Lendo o arquivo no diretório: " + cvsArquivo);
		BufferedReader conteudoCVS = null;
		String linha = "";
		String cvsSeparadorCampo = ";";
		try {

			conteudoCVS = new BufferedReader(new FileReader(cvsArquivo));
			List<String[]> linhas = new ArrayList<String[]>();
			while ((linha = conteudoCVS.readLine()) != null) {
				String[] dados = linha.split(cvsSeparadorCampo);
				processarArquivo(dados, linhas);
			}
			criarArquivoCSVSaida(linhas, fileName);

		} catch (FileNotFoundException e) {
			LOGGER.error("Arquivo Não encontrado: \n" + e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			LOGGER.error("IndexOutOfBounds: \n" + e.getMessage());
		} catch (IOException e) {
			LOGGER.error("IO Error: \n" + e.getMessage());
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

	private void criarArquivoCSVSaida(List<String[]> linhas, String fileName) {
		String diretorio = System.getProperty(Constants.DIRETORIO_APLICACAO) + "\\";
		String cvsArquivo = diretorio + StringUtils.getFileName(fileName) + "-saida" + "."
				+ StringUtils.getFileExtension(fileName);
		LOGGER.info("GRAVANDO O ARQUIVO NO DIRETÓRIO: " + cvsArquivo);
		FileWriter csvWriter = null;
		try {
			csvWriter = criarCabecalhoArquivoCSVSaida(cvsArquivo);

			for (String[] rowData : linhas) {
				csvWriter.append("\t" + String.join(";", rowData));
				csvWriter.append("\n");
			}

			csvWriter.flush();
			LOGGER.info("ARQUIVO GRAVADO COM SUCESSO: " + cvsArquivo);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (csvWriter != null) {
				try {
					csvWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private FileWriter criarCabecalhoArquivoCSVSaida(String cvsArquivo) throws IOException {
		FileWriter csvWriter;
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
		return csvWriter;
	}

	private void processarArquivo(String[] dados, List<String[]> linhas) throws CabecalhoException {
		ReceitaService receitaService = new ReceitaService();
		boolean processsado;
		try {
			if (!dados[0].equalsIgnoreCase("agencia")) {
				double saldo = StringUtils.convertValue(dados[2]);
				processsado = receitaService.atualizarConta(dados[0], dados[1].replace("-", ""), saldo, dados[3]);
				LOGGER.info("[" + "Agência = " + dados[0] + " , " + "Conta = " + dados[1] + " , " + "Saldo = "
						+ dados[2] + " , " + "Status = " + dados[3] + " , " + "Processado = " + processsado + "" + "]");

				String resultadoProcessamento = (processsado) ? "OK" : "ERROR";

				linhas.add(new String[] { dados[0], dados[1], dados[2], dados[3], resultadoProcessamento });
			} else {
				verificaCabecalhoArquivoEntrada(dados);
			}

		} catch (RuntimeException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean verificaCabecalhoArquivoEntrada(String[] dados) throws CabecalhoException {
		if (dados[0].equalsIgnoreCase("agencia") && dados[1].equalsIgnoreCase("conta")) {
			if (dados[2].equalsIgnoreCase("saldo") && dados[3].equalsIgnoreCase("status")) {
				return true;
			}
		}
		throw new CabecalhoException();
	}

}
