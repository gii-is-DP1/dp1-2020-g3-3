<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="vuelos">
	
	<h1 style="text-align:center;">Portal de Vuelos</h1>
<!-- 	<div style="padding: 5% 0% 0% 43%"> -->
<!--  	<form action="/home" method="get"> -->
<!-- 			<label for="fecha">Fecha: </label> <input name="fecha" id="fecha" type="date" /> -->
<!-- 			<label for="precio">Precio: </label> <input name="precio" id="precio" type="number"/> -->
<%-- 			<label for="origen">Origen: </label> <input name="origen" id="origen" type='<aerolineasAAAFC:selectField label="Origen" name="origen" names="${codigos}"></aerolineasAAAFC:selectField>'/> --%>
<%-- 			<label for="destino">Destino: </label> <input name="destino" id="destino" type='<aerolineasAAAFC:selectField label="Destino" name="destino" names="${codigos}"></aerolineasAAAFC:selectField>'/> --%>
<!-- 			<button type="submit" class="btn btn-default">Filtrar</button> -->
<!-- 		</form> -->
<!-- 	</div> -->


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
					<td></td>
<%-- 					<td><c:out value="${min(vuelo.billetes.coste)}" /></td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<script>
	
	</script>
</aerolineasAAAFC:layout>