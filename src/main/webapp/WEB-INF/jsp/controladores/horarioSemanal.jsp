<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="controladores">

	<style>
	.ver {
  		position: relative;
  		display: inline-block;
  		cursor: pointer;
	}
	.ver .mas {
  		visibility: visible;
  		color: black;
  		z-index: 1;
	}
	</style>
	
	<script>
	function detalles(){
		var detalle = document.getElementById("detalle");
		detalle.classList.toggle("show");
	}
	</script>

	<h2>Vuelos de la semana del <c:out value = "${diaS}"/> día <c:out value="${diaM}"/>/<c:out value="${mes}"/>/<c:out value="${anyo}"/></h2>


	<table id="tablaVuelos" class="table table-striped">
		<thead>
			<tr>
				<th>ID Vuelo</th>
				<th>Avión</th>
				<th>Fecha de salida</th>
				<th>Aeropuerto de salida</th>
				<th>Detalles</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vuelos}" var="vuelo">
				<tr>
					<td><c:out value="${vuelo.id}" /></td>
					<td><c:out value="${vuelo.avion.tipoAvion}" /></td>
					<td><aerolineasAAAFC:localDateTime date="${vuelo.fechaSalida}" pattern="dd-MM-yyyy HH:mm" /></td>
					<td><c:out value="${vuelo.aeropuertoOrigen.nombre}" /></td>
					<td>
						<div class="ver" onclick="detalles()">Ver más
						<span class="mas" id="detalle">
							<b>Más detalles del vuelo</b> <br>
							<em>Lugar de salida:</em> <c:out value="${vuelo.aeropuertoOrigen.localizacion}"/><br>
							<em>Lugar de llegada:</em> <c:out value="${vuelo.aeropuertoDestino.localizacion}"/><br>
							<em>Pilotos:</em> <c:forEach items="${vuelo.personalControl}" var="control"> <c:out value="${control.nombre} ${control.apellidos}"></c:out> , </c:forEach> <br>
							<em>Azafatos:</em> <c:forEach items="${vuelo.azafatos}" var="azafato"> <c:out value="${azafato.nombre} ${azafato.apellidos}"></c:out> , </c:forEach> <br>					
						</span>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</aerolineasAAAFC:layout>