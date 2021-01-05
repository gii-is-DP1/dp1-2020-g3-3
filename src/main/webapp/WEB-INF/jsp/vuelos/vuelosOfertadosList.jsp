<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>



<aerolineasAAAFC:layout pageName="vuelos">
    
    <c:if test="${empty param.fecha}"><h2 style="float: left;">Vuelos ofertados este mes </h2></c:if>
    <c:if test="${not empty param.fecha}"><h2 style="float: left;">Vuelos ofertados del ${param.fecha} </h2></c:if>
    
	<div style="float: right;">
	<form action="/vuelos" method="get">
		<label for="fecha">Fecha: </label>
   		<input name="fecha" id="fecha" type="month" pattern="^(19|2[0-1])\d{2}-(0[1-9]|1[0-2])$" placeholder="yyyy-mm"/>
   		<button type="submit" class="btn btn-default">Buscar Vuelos</button>
   	</form>
	</div >
	<table id="tablaVuelos" class="table table-striped" >
        <thead>
        <tr>
        	<th width="3%">ID</th>
            <th width="50%">Tipo de Avi�n</th>
            <th width="50%">Billetes clase Economica</th>
            <th width="50%">Billetes clase Ejecutiva</th>
            <th width="50%">Billetes de primera clase</th>
            
           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vuelosOferta}" var="vuelo">
            <tr>
            	<td onclick="javascript:location.href='/vuelos/${vuelo.id}'" onmouseover="" style="cursor: pointer;"><b><c:out value="${vuelo.id}"/></b></td>

                <td><c:out value="${vuelo.avion.tipoAvion}"/></td>
                <td><c:out value="${vuelo.avion.plazasEconomica}"/></td>
                <td><c:out value="${vuelo.avion.plazasEjecutiva}"/></td>
                <td><c:out value="${vuelo.avion.plazasPrimera}"/></td>
                
            <tr>
					
			</tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="<spring:url value="/vuelos/new" htmlEscape="true" />">Nuevo</a>
</aerolineasAAAFC:layout>