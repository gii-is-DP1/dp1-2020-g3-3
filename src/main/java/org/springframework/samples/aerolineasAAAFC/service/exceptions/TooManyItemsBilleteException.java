package org.springframework.samples.aerolineasAAAFC.service.exceptions;

public class TooManyItemsBilleteException extends Exception{
    
	private static final long serialVersionUID = 1L;
	private String message;
	private String causeF;
	
	public TooManyItemsBilleteException(String message, String causeF) {
		this.message = message;
		this.causeF = causeF;
	}
	
	public String toString() {
		return this.message;
	}
	
	public String getCauseF() {
		return causeF;
	}	
	
}
