<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Pagina en la que se mostraran los vuelos y se permitira acceder a modificarlos -->

<aerolineasAAAFC:layout pageName="vuelos">
    <h2>Vuelos en ${mes}/${año}</h2>
    
    <table id="tablaVuelos" class="table table-striped">
        <thead>
        <tr>
        	<th>ID</th>
            <th width="10%" style="text-align: center;">Fecha de salida</th>
            <th width="10%" style="text-align: center;">Fecha de llegada</th>
            <th width="15%" style="text-align: center;">Aeropuerto de Salida</th>
            <th width="15%" style="text-align: center;">Aeropuerto de Llegada</th>
            <th width="10%" style="text-align: center;">Tipo de Avión</th>
            <th width="20%" style="text-align: center;">Azafatos</th>
            <th width="20%" style="text-align: center;">Personal de Control</th>
           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vuelos}" var="vuelo">
            <tr>
          		<td onclick="javascript:location.href='/vuelos/${vuelo.id}'" onmouseover="" style="cursor: pointer;"><c:out value="${vuelo.id}"/></td>
				<td><aerolineasAAAFC:localDateTime date="${vuelo.fechaSalida}" pattern="dd-MM-yyy HH:mm"/></td>
                <td><aerolineasAAAFC:localDateTime date="${vuelo.fechaLlegada}" pattern="dd-MM-yyy HH:mm"/></td>
				<td><c:out value="${vuelo.aeropuertoOrigen.nombre}"/></td>
				<td><c:out value="${vuelo.aeropuertoDestino.nombre}"/></td>
				<td><c:out value="${vuelo.avion.tipoAvion}"/></td>
				<td><c:forEach items="${vuelo.personalControl}" var="pControl">
						<c:out value="${pControl.nombre} ${pControl.apellidos}"/>
						<br>
					</c:forEach>
				</td>
             	<td><c:forEach items="${vuelo.azafatos}" var="azafato">
						<c:out value="${azafato.nombre} ${azafato.apellidos}"/>
						<br>
					</c:forEach>
				</td>
                
                
			<tr>	
        </c:forEach>
        </tbody>
    </table>
    <a href="<spring:url value="/vuelos/new" htmlEscape="true" />">Nuevo</a>
</aerolineasAAAFC:layout>