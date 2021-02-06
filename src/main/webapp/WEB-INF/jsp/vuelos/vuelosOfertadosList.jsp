<%@page import="java.sql.ResultSet"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>



<aerolineasAAAFC:layout pageName="vuelos">
    
    <c:if test="${empty param.fecha}"><h2 class="centrado">Vuelos ofertados este mes </h2></c:if>
    <c:if test="${not empty param.fecha}"><h2 class="centrado">Vuelos ofertados del ${param.fecha} </h2></c:if>
    
	<div style="float: right; padding-bottom: 1%;">
	<form action="/vuelos/ofertas" method="get">
		<label for="fecha">Fecha: </label>
   		<input name="fecha" id="fecha" type="date"/>
   		<button type="submit" class="btn btn-default">Buscar Vuelos</button>
   	</form>
	</div >
	<table id="tablaVuelos" class="table table-striped centrado">
        <thead>
        <tr>
        	<th class="centrado" width="10%">ID de Vuelo</th>
            <th class="centrado" width="10%">Ruta</th>
            <th class="centrado" width="20%">Fecha Salida y Llegada</th>
            <th class="centrado" width="17%">Billetes clase Economica</th>
            <th class="centrado" width="17%">Billetes clase Ejecutiva</th>
            <th class="centrado" width="17%">Billetes de primera clase</th>
            <th class="centrado" width="20%">Total posible</th>
            
           
        </tr>
        </thead>
        <tbody>
			<c:choose>
				<c:when test="${vuelosOferta.size() > 0 }">
					<c:forEach items="${vuelosOferta}" var="vuelo">
						<tr>
							<td onclick="javascript:location.href='/vuelos/${vuelo.id}'"onmouseover="" style="cursor: pointer;">
								<b><c:out value="${vuelo.id}" /></b>
							</td>
							<td><c:out value="${vuelo.aeropuertoOrigen.codigoIATA} - ${vuelo.aeropuertoDestino.codigoIATA}" /></td>
							<td><aerolineasAAAFC:localDateTime date="${vuelo.fechaSalida}" pattern="dd-MM-yyyy HH:mm"/> 
							- 
							<aerolineasAAAFC:localDateTime date="${vuelo.fechaLlegada}" pattern="dd-MM-yyyy HH:mm"/></td>
							<td><c:out value="${vuelo.avion.plazasEconomica}" /></td>
							<td><c:out value="${vuelo.avion.plazasEjecutiva}" /></td>
							<td><c:out value="${vuelo.avion.plazasPrimera}" /></td>
							<td></td>
						</tr>
						<tr>
							<td><b>Precio de cada billete</b></td>
							<td></td>
							<td></td>
							<td>
								<c:set var="eco" value="${vuelo.coste}"/>
								<c:out value="${eco}" />
							</td>
							<td>
								<c:set var="eje" value="${vuelo.coste * 1.25}"/>
								<c:out value="${eje}" />
							</td>
							<td>
								<c:set var="prim" value="${vuelo.coste * 1.75}"/>
								<c:out value="${prim}"/>
							</td>
							<c:set var="total" value="${vuelo.avion.plazasEconomica*eco + vuelo.avion.plazasEjecutiva*eje + vuelo.avion.plazasPrimera*prim}" />
							<td><c:out value="${total}" /></td>
							
						</tr>

					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="centrado">
						<td colspan="10"><h3>¡Lo sentimos! No hay vuelos ofertados para estas fechas.</h3></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
    </table>
    <a href="<spring:url value="/vuelos/new" htmlEscape="true" />">Nuevo</a>

	<div class="panel-footer centrado">
		<h3>Mostrando página ${number+1} de ${totalPages}</h3>
		<ul class="pagination" style="margin: 0px;">
			<c:forEach begin="0" end="${totalPages-1}" var="page">
				<li class="page-item"><a
					href="ofertas?page=${page}&size=${size}" class="page-link">${page+1}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</aerolineasAAAFC:layout>