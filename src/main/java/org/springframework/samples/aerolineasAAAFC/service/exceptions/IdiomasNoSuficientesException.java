package org.springframework.samples.aerolineasAAAFC.service.exceptions;

public class IdiomasNoSuficientesException extends Exception{
    
	private static final long serialVersionUID = 1L;
	private String message;
	
	public IdiomasNoSuficientesException(String message) {
		this.message = "Lo sentimos pero no son suficientes idiomas para registrar.";
	}
	
	public String toString() {
		return this.message;
	}
	
}
