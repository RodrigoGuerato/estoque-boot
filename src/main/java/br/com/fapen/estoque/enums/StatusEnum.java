package br.com.fapen.estoque.enums;

public enum StatusEnum {
	EM_DIGITACAO("Em Digitação"), 
	RECEBIDO("Recebido"), 
	CANCELADO("Cancelado");

	private final String displayValue;

	private StatusEnum(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}
}
