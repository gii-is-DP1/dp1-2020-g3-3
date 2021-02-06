<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="vuelos">
	
	<div class="centrado">
		<img class="imagen" alt="AAAFC logo" src="/resources/images/logoT.png" style="width: 50%;">
	</div>
	
	<h1 class="centrado">Portal de Vuelos</h1>
	
	<div class="centrado" style="padding-bottom: 1%">
	
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

	<table id="tablaVuelos" class="table table-striped centrado">
		<thead>
			<tr>
				<th class="centrado">Origen</th>
				<th class="centrado">Destino</th>
				<th class="centrado">Fecha salida</th>
				<th class="centrado">Fecha llegada</th>
				<th class="centrado">Precio</th>
				<th></th>
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
					<td><a href="<spring:url value="javascript:location.href='/billetes/${vuelo.id}/new'" htmlEscape="true"/>" class="btn btn-default">Comprar billete</a>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panel-footer centrado">
		<h3>Mostrando página ${number+1} de ${totalPages}</h3>
		<ul class="pagination" style="margin: 0px;">
			<c:forEach begin="0" end="${totalPages-1}" var="page">
				<li class="page-item"><a
					href="home?fecha=${param.fecha}&precio=${param.precio}&origen=${param.origen}&destino=${param.destino}&page=${page}&size=${size}" class="page-link">${page+1}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</aerolineasAAAFC:layout>