package org.springframework.samples.aerolineasAAAFC.deprecated;

public class NifDuplicadoException extends Exception{

	private static final long serialVersionUID = 1L;
	private String message;
	
	public NifDuplicadoException(String message) {
		this.message = "Lo sentimos pero este NIF ya está registrado.";
	}
	
	public String toString() {
		return this.message;
	}
	
}
