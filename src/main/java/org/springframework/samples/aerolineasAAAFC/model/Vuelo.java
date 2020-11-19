package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
* Entidad simple que representa un señor Vuelo 
* PD: No ha habido ningun tipo de error en la frase anterior
*/

@MappedSuperclass
@Entity
@Table(name = "vuelos")
public class Vuelo extends IdEntity{
	
	//Atributos
		@Column(name = "fechaVuelo")
		@NotEmpty
		protected Date fechaVuelo;
		
		@Column(name = "horaSalida")
		@NotEmpty
		protected Date horaSalida;
		
		@Column(name = "horaLlegada")
		@NotEmpty
		protected Date horaLlegada;
		
		@Column(name = "precio")
		@NotEmpty
		protected Double precio;
		
		@Column(name = "codigoIATAOrigen")
		@NotEmpty
		protected String codigoIATAOrigen;
		
		@Column(name = "codigoIATADestino")
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

		@Override
		public String toString() {
			return "Vuelo [fechaVuelo=" + fechaVuelo + ", horaSalida=" + horaSalida + ", horaLlegada=" + horaLlegada
					+ ", precio=" + precio + ", codigoIATAOrigen=" + codigoIATAOrigen + ", codigoIATADestino="
					+ codigoIATADestino + "]";
		}
		
		
		
		
}
