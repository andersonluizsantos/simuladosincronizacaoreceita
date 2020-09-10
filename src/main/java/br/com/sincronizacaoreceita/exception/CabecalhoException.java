package br.com.sincronizacaoreceita.exception;

public class CabecalhoException extends MinhaException {
	private static final long serialVersionUID = 1L;

	public CabecalhoException() {
		super("Esse cabeçalho do arquivo CSV não é válido. A primeira linha do arquivo deve seguir o padrão [agencia;contas;saldo;status]");
	}
}
