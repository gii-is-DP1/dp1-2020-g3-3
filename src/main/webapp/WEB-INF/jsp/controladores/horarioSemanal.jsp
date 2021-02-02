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
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script>
	$(document).ready(function(){
	    $("#withcomponent").toggle();
	    showAndHideUl = function()
	    {
	        $("#${vuelo.id}sin").toggle();
	        $("#${vuelo.id}sin").toggleClass("component");
	        $("#${vuelo.id}con").toggle();
	    }

	});
//	$( "#${vuelo.id}" ).click(function() {
//		  $( "#vuelo${vuelo.id}" ).toggle();
//	});
//	function detalles(){
//		if ( document.getElementById("vuelo${vuelo.id}").style.visibility === "visible" ) {
//			document.getElementById("vuelo${vuelo.id}").style.visibility = "hidden";
//		} else if ( document.getElementById("vuelo${vuelo.id}").style.visibility === "hidden" ) {
//			document.getElementById("vuelo${vuelo.id}").style.visibility = "visible";
//		}
	}
	</script>

	<h2>Vuelos de la semana del <c:out value = "${diaS}"/> día <c:out value="${diaM}"/>/<c:out value="${mes}"/>/<c:out value="${anyo}"/></h2>
	<div style="padding: 5% 0% 0% 43%">
 	<form action="/controladores/${pControlId}/semana" method="get">
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
						<button onclick="showAndHideUl()" id="preview">Ver</button>
						<div id="${vuelo.id}sin"> 
						
						</div>
						<div id="${vuelo.id}con">
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

</aerolineasAAAFC:layout>