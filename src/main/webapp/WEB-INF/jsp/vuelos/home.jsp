<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="vuelos">
	
	<h1 style="text-align:center;">Portal de Vuelos</h1>
	<div style="text-align:center;">
 	<form action="/home" method="get">
			<label for="fecha">Fecha: </label> <input name="fecha" id="fecha" size="16" type="date" min="${hoy}" value="${hoy}"/>
			<label for="precio">Precio: </label> <input name="precio" id="precio" type="number" min="0" max="9999" value="9999"/>
			<label for="origen">Origen: </label>
					<select name="origen" id="origen">
						<option value=""> - </option>
						<c:forEach items="${codigos}" var="codigo">
							<option value="${codigo}"><c:out value="${codigo}"/></option>
						</c:forEach>
					</select>
			<label for="destino">Destino: </label>
					<select name="destino" id="destino">
						<option value=""> - </option>
						<c:forEach items="${codigos}" var="codigo">
							<option value="${codigo}"><c:out value="${codigo}"/></option>
						</c:forEach>
					</select>
			<button type="submit" class="btn btn-default">Filtrar</button>
		</form>
	</div>


	<table id="tablaVuelos" class="table table-striped">
		<thead>
			<tr>
				<th>Origen</th>
				<th>Destino</th>
				<th>Fecha salida</th>
				<th>Fecha llegada</th>
				<th>Precio</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vuelos}" var="vuelo">
				<tr>
					<td><c:out value="${vuelo.aeropuertoOrigen.nombre} - ${vuelo.aeropuertoOrigen.codigoIATA}" /></td>
					<td><c:out value="${vuelo.aeropuertoDestino.nombre} - ${vuelo.aeropuertoDestino.codigoIATA}" /></td>
					<td><aerolineasAAAFC:localDateTime date="${vuelo.fechaSalida}" pattern="dd-MM-yyyy HH:mm" /></td>
					<td><aerolineasAAAFC:localDateTime date="${vuelo.fechaLlegada}" pattern="dd-MM-yyyy HH:mm" /></td>
					<td>A partir de <c:out value="${vuelo.coste}" /> euros</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<script>
	
	</script>
</aerolineasAAAFC:layout>