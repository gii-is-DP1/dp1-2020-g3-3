<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="azafatos">

	<h2>Horario de este mes</h2>


	<table id="tablaVuelos" class="table table-striped">
		<thead>
			<tr>
				<th width="25%">Fecha de Salida</th>
				<th width="25%">Fecha de Llegada</th>
				<th width="25%">Aeropuerto de Salida</th>
				<th width="25%">Aeropuerto de Llegada</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vuelos}" var="vuelo">
				<tr>
					<td><aerolineasAAAFC:localDateTime date="${vuelo.fechaSalida}" pattern="dd-MM-yyyy HH:mm" /></td>
					<td><aerolineasAAAFC:localDateTime date="${vuelo.fechaLlegada}" pattern="dd-MM-yyy HH:mm" /></td>
					<td><c:out value="${vuelo.aeropuertoOrigen.codigoIATA}" /></td>
					<td><c:out value="${vuelo.aeropuertoDestino.codigoIATA}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</aerolineasAAAFC:layout>
