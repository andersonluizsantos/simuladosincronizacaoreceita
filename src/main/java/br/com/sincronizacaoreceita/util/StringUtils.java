package br.com.sincronizacaoreceita.util;

public class StringUtils {

	public static double convertValue(String value) {
		return Double.parseDouble(value.replace(",", "."));
	}

	public static String getFileExtension(String filename) {
		if (filename.contains("."))
			return filename.substring(filename.lastIndexOf(".") + 1);
		else
			return "";
	}

	public static String getFileName(String filename) {
		if (filename.contains("."))
			return filename.substring(0, filename.lastIndexOf("."));
		else
			return "";
	}
}
