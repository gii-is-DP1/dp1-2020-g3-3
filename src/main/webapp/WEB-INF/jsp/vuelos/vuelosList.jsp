<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>


<!-- Pagina en la que se mostraran los vuelos y se permitira acceder a modificarlos -->

<aerolineasAAAFC:layout pageName="vuelos">
    
    <c:if test="${empty param.fecha}"><h2 style="float: left;">Vuelos de este mes </h2></c:if>
    <c:if test="${not empty param.fecha}"><h2 style="float: left;">Vuelos del ${param.fecha} </h2></c:if>
    
	<div style="float: right;">
	<form action="/vuelos" method="get">
		<label for="fecha">Fecha: </label>
   		<input name="fecha" id="fecha" type="month" pattern="^(19|2[0-1])\d{2}-(0[1-9]|1[0-2])$" placeholder="yyyy-mm"/>
   		<button type="submit" class="btn btn-default">Buscar Vuelos</button>
   	</form>
	</div>
	<table id="tablaVuelos" class="table table-striped">
        <thead>
        <tr>
        	<th width="3%">ID</th>
            <th width="13%">Fecha de Salida</th>
            <th width="13%">Fecha de Llegada</th>
            <th width="5%">Aeropuerto de Salida</th>
            <th width="5%">Aeropuerto de Llegada</th>
            <th width="20%">Tipo de Avión</th>
            <th width="35%">Personal de Control</th>
            
           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vuelos}" var="vuelo">
            <tr>
            	<td onclick="javascript:location.href='/vuelos/${vuelo.id}'" onmouseover="" style="cursor: pointer;"><b><c:out value="${vuelo.id}"/></b></td>
				<td><aerolineasAAAFC:localDateTime date="${vuelo.fechaSalida}" pattern="dd-MM-yyyy HH:mm"/></td>
                <td><aerolineasAAAFC:localDateTime date="${vuelo.fechaLlegada}" pattern="dd-MM-yyy HH:mm"/></td>
				<td><c:out value="${vuelo.aeropuertoOrigen.codigoIATA}"/></td>
				<td><c:out value="${vuelo.aeropuertoDestino.codigoIATA}"/></td>
                <td><c:out value="${vuelo.avion.tipoAvion}"/></td>
                <td>
                	<c:forEach var="pControl" items="${vuelo.personalControl}" varStatus="loop">
                		<c:out value="${pControl.nombre} ${pControl.apellidos}"/>
                		<c:if test="${!loop.last}">, </c:if>
                	</c:forEach>
                </td>
            <tr>
					
			</tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="<spring:url value="/vuelos/new" htmlEscape="true" />">Nuevo</a>
</aerolineasAAAFC:layout>