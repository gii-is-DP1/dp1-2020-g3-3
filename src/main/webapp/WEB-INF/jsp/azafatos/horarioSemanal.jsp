<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="azafatos">
	<style>
/*  	table#tablaVuelos th:nth-child(5){  */
/*  		visibility: hidden;  */
/*  	}  */
	
/*  	table#tablaVuelos td:nth-child(5){  */
/*  		visibility: hidden; */
/*  	} */
 	#columnaT{ 
 		visibility: visible; 
 	}
/*  	#columna.columnas{  */
/*  		visibility: hidden;  */
/*  	}  */
	</style>
	
<%-- 	<spring:url value="/resources/scripts/hideAndShowDetails.js" var="hideAndShowDetails"/> --%>
<%-- 	<script src="${hideAndShowDetails}"></script> --%>

	<script>
	function openDetalles() {
		document.getElementById("columnaT").style.visibility = "visible";
		var columnas = document.getElementsByClassName("columna");
		for(var i=0; i<columnas.length; i++){
			columnas.item(i).toggle();
		}
		document.getElementById("abrir").style.visibility = "hidden";
		document.getElementById("cerrar").style.visibility = "visible";
	}

	function closeDetalles(){
		document.getElementById("columnaT").style.visibility = "hidden";
		var columnas = document.getElementsByClassName("columna");
		for(var i=0; i<columnas.length; i++){
			columnas.item(i).toggle();
		}
		document.getElementById("abrir").style.visibility = "visible";
		document.getElementById("cerrar").style.visibility = "hidden";
	}
	</script>
	
	<script>
	var columnas = document.getElementsByClassName("columna");
	console.log(columnas);
	console.log(columnas[0]);
	console.log(columnas[1]);
	</script>
	
	
	<h1 style="text-align:center;">Vuelos de la semana del <c:out value = "${diaS}"/> día <c:out value="${diaM}"/>/<c:out value="${mes}"/>/<c:out value="${anyo}"/></h1>
	<div style="padding: 5% 0% 0% 43%">
 	<form action="/azafatos/${azafatoId}/semana" method="get">
			<label for="fecha">Fecha: </label> <input name="fecha" id="fecha" type="date" />
			<button type="submit" class="btn btn-default">Buscar</button>
		</form>
	</div>
	
	<a href="javascript:void(0)" onclick="openDetalles()" id="abrir" class="abre" style="float:right; visibility:hidden;">Ver detalles</a>
	<a href="javascript:void(0)" onclick="closeDetalles()" id="cerrar" class="cierra" style="float:right; visibility:visible;">Cerrar detalles</a>

	<table id="tablaVuelos" class="table table-striped">
		<thead>
			<tr>
				<th>ID Vuelo</th>
				<th>Avión</th>
				<th>Fecha de salida</th>
				<th>Ruta</th>
				<th id="columnaT">Detalles</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vuelos}" var="vuelo" varStatus="bucle">
				<tr>
					<td><c:out value="${vuelo.id}" /></td>
					<td><c:out value="${vuelo.avion.tipoAvion}" /></td>
					<td><aerolineasAAAFC:localDateTime date="${vuelo.fechaSalida}" pattern="dd-MM-yyyy HH:mm" /></td>
					<td><c:out value="${vuelo.aeropuertoOrigen.codigoIATA} - ${vuelo.aeropuertoDestino.codigoIATA}" /></td>
					<td id="columna${bucle.index+1}" class="columna" style="visibility:visible;">
						<div>
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
	
	<script>
	
	</script>
</aerolineasAAAFC:layout>