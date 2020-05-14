package br.com.fapen.estoque.enums;

public enum CondicaoPagtoEnum {
	DINHEIRO("Dinheiro"), 
	DEBITO("Cartão de Débito"), 
	CREDITO("Cartão de Crédito");

	private final String displayValue;

	private CondicaoPagtoEnum(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}
}
