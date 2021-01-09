package org.springframework.samples.aerolineasAAAFC.service.exceptions;

public class PlatosNoValidosException extends Exception{
    
	private static final long serialVersionUID = 1L;
	private String message;
	
	public PlatosNoValidosException(int code) {
		if(code == 1)
			this.message = "Ha escogido muy pocos platos.";
		else if(code == 2)
			this.message = "Ha escogido un plato inexistente.";
		else if(code == 3)
			this.message = "Ha escogido demasiados platos primeros, segundos o postres.";
	}
	
	public String toString() {
		return this.message;
	}	
	
}
