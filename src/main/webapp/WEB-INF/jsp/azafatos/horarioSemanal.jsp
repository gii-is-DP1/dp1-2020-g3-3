<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="azafatos">
	
	<script>
	function toggleDetalles(param) {
		if(document.getElementById(param).style.visibility == "visible"){
			document.getElementById(param).style.visibility = "hidden";
		}else{
			document.getElementById(param).style.visibility = "visible";
		}
	}
	</script>
	
	<h1 style="text-align:center;">Vuelos de la semana del <c:out value = "${diaS}"/> día <c:out value="${diaM}"/>/<c:out value="${mes}"/>/<c:out value="${anyo}"/></h1>
	<div style="padding: 5% 0% 0% 43%">
 	<form action="/azafatos/${azafatoId}/semana" method="get">
			<label for="fecha">Fecha: </label> <input name="fecha" id="fecha" type="date" />
			<button type="submit" class="btn btn-default">Buscar</button>
		</form>
	</div>


	<table id="tablaVuelos" class="table table-striped">
		<thead>
			<tr>
				<th>ID Vuelo</th>
				<th>Avión</th>
				<th>Fecha de salida</th>
				<th>Ruta</th>
				<th>Detalles</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vuelos}" var="vuelo" varStatus="bucle">
				<tr>
					<td><c:out value="${vuelo.id}" /></td>
					<td><c:out value="${vuelo.avion.tipoAvion}" /></td>
					<td><aerolineasAAAFC:localDateTime date="${vuelo.fechaSalida}" pattern="dd-MM-yyyy HH:mm" /></td>
					<td><c:out value="${vuelo.aeropuertoOrigen.codigoIATA} - ${vuelo.aeropuertoDestino.codigoIATA}" /></td>
					<td><a href="javascript:void(0)" onclick="toggleDetalles('columna${bucle.index+1}')" class="btn btn-default">Ver detalles</a></td>
					<td>
						<div id="columna${bucle.index+1}" style="visibility:hidden;">
							<b>Más detalles del vuelo</b> <br>
							<em>Lugar de salida:</em> <c:out value="${vuelo.aeropuertoOrigen.localizacion}"/><br>
							<em>Lugar de llegada:</em> <c:out value="${vuelo.aeropuertoDestino.localizacion}"/><br>
							<em>Pilotos:</em> <c:forEach items="${vuelo.personalControl}" var="control" varStatus="loop"> <c:out value="${control.nombre} ${control.apellidos}"></c:out><c:if test="${!loop.last}">, </c:if></c:forEach>. <br>
							<em>Azafatos:</em> <c:forEach items="${vuelo.azafatos}" var="azafato" varStatus="loop"> <c:out value="${azafato.nombre} ${azafato.apellidos}"></c:out><c:if test="${!loop.last}">, </c:if></c:forEach>. <br>					
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<spring:url value="/azafatos/{azafatoId}/horario" var="editUrl">
    <spring:param name="azafatoId" value="${azafatoId}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default" style="float:right;">Volver</a>

</aerolineasAAAFC:layout>