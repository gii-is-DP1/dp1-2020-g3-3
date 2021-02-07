<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>


<!-- Pagina en la que se mostraran los vuelos y se permitira acceder a modificarlos -->

<aerolineasAAAFC:layout pageName="vuelos">
    
    <c:if test="${empty param.fecha}"><h2 class="centrado">Vuelos de este mes </h2></c:if>
    <c:if test="${not empty param.fecha}"><h2  class="centrado">Vuelos del ${param.fecha} </h2></c:if>
    
	<div style="float: right;">
	<form action="/vuelos" method="get">
		<label for="fecha">Fecha: </label>
   		<input name="fecha" id="fecha" type="date"/>
   		<button type="submit" class="btn btn-default">Buscar Vuelos</button>
   	</form>
	</div>
	<table id="tablaVuelos" class="table table-striped centrado">
        <thead>
        <tr>
        	<th class="centrado" width="3%" >ID</th>
        	<th class="centrado" width="10%">Ruta</th>
            <th class="centrado" width="13%">Fecha de Salida</th>
            <th class="centrado" width="13%">Fecha de Llegada</th>
            <th class="centrado" width="20%">Tipo de Avión</th>
            <th class="centrado" width="35%">Personal de Control</th>
            
           
        </tr>
        </thead>
        <tbody>
			<c:choose>
				<c:when test="${vuelos.size() > 0 }">
					<c:forEach items="${vuelos}" var="vuelo">
						<tr>
							<td onclick="javascript:location.href='/vuelos/${vuelo.id}'"
								onmouseover="" style="cursor: pointer;"><b><c:out
										value="${vuelo.id}" /></b></td>
							<td><b
								onclick="javascript:location.href='/aeropuertos/${vuelo.aeropuertoOrigen.id}'"
								onmouseover="" style="cursor: pointer;"><c:out
										value="${vuelo.aeropuertoOrigen.codigoIATA}" /></b> - <b
								onclick="javascript:location.href='/aeropuertos/${vuelo.aeropuertoDestino.id}'"
								onmouseover="" style="cursor: pointer;"><c:out
										value="${vuelo.aeropuertoDestino.codigoIATA}" /></b></td>
							<td><aerolineasAAAFC:localDateTime
									date="${vuelo.fechaSalida}" pattern="dd-MM-yyyy HH:mm" /></td>
							<td><aerolineasAAAFC:localDateTime
									date="${vuelo.fechaLlegada}" pattern="dd-MM-yyy HH:mm" /></td>
							<td><c:out value="${vuelo.avion.tipoAvion}" /></td>
							<td><c:forEach var="pControl"
									items="${vuelo.personalControl}" varStatus="loop">
									<c:out value="${pControl.nombre} ${pControl.apellidos}" />
									<c:if test="${!loop.last}">, </c:if>
								</c:forEach></td>
						<tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="centrado">
						<td colspan="10"><h3>¡Lo sentimos! No se encuentra ningún vuelo en estas fechas.</h3></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
    </table>
    <a href="<spring:url value="/vuelos/new" htmlEscape="true" />" class="btn btn-default">Nuevo vuelo</a>
    
    <spring:url value="/vuelos/historial" var="url"></spring:url>
    <a href="${fn:escapeXml(url)}" class="btn btn-default" style="float:right;">Historial de Vuelos</a>

	<div class="panel-footer centrado">
		<h3>Mostrando página ${number+1} de ${totalPages}</h3>
		<ul class="pagination" style="margin: 0px;">
			<c:forEach begin="0" end="${totalPages-1}" var="page">
				<li class="page-item"><a
					href="vuelos?page=${page}&size=${size}" class="page-link">${page+1}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</aerolineasAAAFC:layout>