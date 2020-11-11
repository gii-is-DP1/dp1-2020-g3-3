package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

/**
* Entidad simple que representa un señor Vuelo 
* PD: No ha habido ningun tipo de error en la frase anterior
*/

@MappedSuperclass
public class Vuelo extends IdEntity{
	
	//Atributos
	
		@NotEmpty
		protected Date fechaVuelo;
		
		@NotEmpty
		protected Date horaSalida;
		
		@NotEmpty
		protected Date horaLlegada;
		
		@NotEmpty
		protected Double precio;
		
		@NotEmpty
		protected String codigoIATAOrigen;
		
		@NotEmpty
		protected String codigoIATADestino;

		
		//Getters and Setters
		public Date getFechaVuelo() {
			return fechaVuelo;
		}

		public void setFechaVuelo(Date fechaVuelo) {
			this.fechaVuelo = fechaVuelo;
		}

		public Date getHoraSalida() {
			return horaSalida;
		}

		public void setHoraSalida(Date horaSalida) {
			this.horaSalida = horaSalida;
		}

		public Date getHoraLlegada() {
			return horaLlegada;
		}

		public void setHoraLlegada(Date horaLlegada) {
			this.horaLlegada = horaLlegada;
		}

		public Double getPrecio() {
			return precio;
		}

		public void setPrecio(Double precio) {
			this.precio = precio;
		}

		public String getCodigoIATAOrigen() {
			return codigoIATAOrigen;
		}

		public void setCodigoIATAOrigen(String codigoIATAOrigen) {
			this.codigoIATAOrigen = codigoIATAOrigen;
		}

		public String getCodigoIATADestino() {
			return codigoIATADestino;
		}

		public void setCodigoIATADestino(String codigoIATADestino) {
			this.codigoIATADestino = codigoIATADestino;
		}
		
		
}
