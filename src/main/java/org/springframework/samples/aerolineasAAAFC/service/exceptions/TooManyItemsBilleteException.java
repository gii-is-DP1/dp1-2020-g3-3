package org.springframework.samples.aerolineasAAAFC.service.exceptions;

public class TooManyItemsBilleteException extends Exception{
    
	private static final long serialVersionUID = 1L;
	private String message;
	
	public TooManyItemsBilleteException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return this.message;
	}
	
}
