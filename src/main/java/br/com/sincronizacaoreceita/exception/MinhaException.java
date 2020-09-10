package br.com.sincronizacaoreceita.exception;

public class MinhaException extends Exception {
	private static final long serialVersionUID = 6328023540538016846L;
	private String msg;

	public MinhaException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMessage() {
		return msg;
	}
}
