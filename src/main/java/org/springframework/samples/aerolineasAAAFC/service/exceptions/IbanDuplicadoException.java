package org.springframework.samples.aerolineasAAAFC.service.exceptions;

public class IbanDuplicadoException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public IbanDuplicadoException(String message) {
		this.message = "Lo sentimos pero este IBAN ya está registrado.";
	}
	
	public String toString() {
		return this.message;
	}
}
