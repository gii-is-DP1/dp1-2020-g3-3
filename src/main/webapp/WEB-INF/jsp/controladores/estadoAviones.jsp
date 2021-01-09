<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="controladores">

	<h2>Estado del avión</h2>


	<table id="tablaVuelos" class="table table-striped">
		<thead>
			<tr>
				<th width="25%">Id avión</th>
				<th width="25%">Fecha fabricación</th>
				<th width="25%">Horas acumuladas</th>
				<th width="25%">Estacionamiento</th> 
				<th width="25%">Disponibilidad</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vuelos}" var="vuelo">
				<tr>
					<td><c:out value="${vuelo.avion_id}" /></td>
					<td><aerolineasAAAFC:localDateTime date="${vuelo.fechaFabricacion}" pattern="dd-MM-yyyy HH:mm" /></td>
					<td><aerolineasAAAFC:localDateTime date="${vuelo.horasAcumuladas}" pattern="dd-MM-yyy HH:mm" /></td>
					<td><c:out value="${vuelo.aeropuertoDestino}" /></td>
					<td><c:out value="${vuelo.disponibilidad}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</aerolineasAAAFC:layout>
