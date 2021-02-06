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
    
	<div style="float: right;">
	<form action="/vuelos/ofertas" method="get">
		<label for="fecha">Fecha: </label>
   		<input name="fecha" id="fecha" type="date"/>
   		<button type="submit" class="btn btn-default">Buscar Vuelos</button>
   	</form>
	</div >
	<table id="tablaVuelos" class="table table-striped centrado" >
        <thead>
        <tr>
        	<th class="centrado" width="3%">ID</th>
            <th class="centrado" width="25%">Tipo de Avión</th>
            <th class="centrado" width="25%">Billetes clase Economica</th>
            <th class="centrado" width="25%">Billetes clase Ejecutiva</th>
            <th class="centrado" width="25%">Billetes de primera clase</th>
            
           
        </tr>
        </thead>
        <tbody>
			<c:choose>
				<c:when test="${vuelosOferta.size() > 0 }">
					<c:forEach items="${vuelosOferta}" var="vuelo">
						<tr>
							<td onclick="javascript:location.href='/vuelos/${vuelo.id}'"
								onmouseover="" style="cursor: pointer;"><b><c:out
										value="${vuelo.id}" /></b></td>

							<td><c:out value="${vuelo.avion.tipoAvion}" /></td>
							<td><c:out value="${vuelo.avion.plazasEconomica}" /></td>
							<td><c:out value="${vuelo.avion.plazasEjecutiva}" /></td>
							<td><c:out value="${vuelo.avion.plazasPrimera}" /></td>

						</tr>
						<tr>
							<td><c:out value="cantidad" /></td>

							<td></td>
							<td><c:out value="50" /></td>
							<td><c:out value="100" /></td>
							<td><c:out value="200" /></td>

						</tr>
						<tr>
							<td>Total posible:</td>
							<td></td>
							<td></td>
							<c:set var="total" value="${vuelo.avion.plazasEconomica*50 +vuelo.avion.plazasEjecutiva*100+vuelo.avion.plazasPrimera*200  }" />
							<td><c:out value="${total}" /></td>
							<td></td>
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