package org.springframework.samples.aerolineasAAAFC.service.exceptions;

public class MenuPriceException extends Exception{
    
	private static final long serialVersionUID = 1L;
	private String message;
	
	public MenuPriceException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return this.message;
	}
	
}
