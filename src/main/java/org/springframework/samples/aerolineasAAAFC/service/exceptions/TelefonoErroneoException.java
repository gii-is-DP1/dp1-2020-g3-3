package org.springframework.samples.aerolineasAAAFC.service.exceptions;

public class TelefonoErroneoException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public TelefonoErroneoException(String message) {
		this.message = "Lo sentimos pero el teléfono introducido no es correcto.";
	}
	
	public String toString() {
		return this.message;
	}
	
}
