package org.springframework.samples.aerolineasAAAFC.service.exceptions;

public class HorasMaximasVueloException extends Exception{

	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public HorasMaximasVueloException(String errorMessage) {
		this.message = message;
	}
	
	public String toString() {
		return this.message;
	}
}
