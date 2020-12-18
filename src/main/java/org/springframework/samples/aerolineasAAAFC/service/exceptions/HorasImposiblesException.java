package org.springframework.samples.aerolineasAAAFC.service.exceptions;

public class HorasImposiblesException extends Exception{

	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public HorasImposiblesException(String errorMessage) {
		this.message = message;
	}
	
	public String toString() {
		return this.message;
	}
}
