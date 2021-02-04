<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- Archivo .jsp que muestra el listado de todos los aviones, a su vez se
puede acceder a la ficha de cada avión (por id) para editarlo o borrarlo -->

<aerolineasAAAFC:layout pageName="aviones">
    <h2>Aviones</h2>

    <table id="tablaAviones" class="table table-striped">
        <thead>
        <tr>
            <th>ID Avión</th>
            <th>Tipo de avión</th>
            <th>Horas de vuelo acumuladas</th>
            <th>Fecha de fabricación</th>
            <th>Última revisión</th>
            <sec:authorize access="hasAuthority('personalControl')">
            	<th>Horas Acumuladas</th>
            	<th>Disponibilidad</th>
            	<th>Fecha de fabricación</th>
            </sec:authorize>
            <th> </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${aviones}" var="avion">
            <tr>
                <td>
                    <spring:url value="/aviones/{avionId}" var="avionUrl">
                        <spring:param name="avionId" value="${avion.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(avionUrl)}"><c:out value="${avion.id}"/></a>
                </td>
                <td>
                    <c:out value="${avion.tipoAvion}"/>
                </td>
                <td>
                    <c:out value="${avion.horasAcumuladas}"/>
                </td>
                <td>
                    <c:out value="${avion.fechaFabricacion}"/>
                </td>
                <td>
                    <c:out value="${avion.fechaRevision}"/>
                </td>
                <sec:authorize access="hasAuthority('personalControl')">
                <td>
                    <c:choose>
    					<c:when test="${avion.disponibilidad==true}">
        					Sí 
        					<br />
    					</c:when>    
    					<c:otherwise>
        					No 
        					<br />
    					</c:otherwise>
					</c:choose>
                </td>
               
                <td>
                    <c:out value="${avion.horasAcumuladas}"/>
                </td>
            	<td>
            		<c:out value="${avion.disponibilidad}"/>
            	</td>
            	<td>
            		<c:out value="${avion.fechaFabricacion}"/>
            	</td>
            	</sec:authorize>
                <td>
                	<spring:url value="/aviones/{avionId}/edit" var="avionUrl">
        			<spring:param name="avionId" value="${avion.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(avionUrl)}" class="btn btn-default">Editar avión</a>
                </td>     
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <br>
    <table id="tablaAeropuertos" class="table table-striped">
        <thead>
        <tr>
            <th>Estacionamiento</th>
        </tr>
        </thead>
        <tbody>
            
           <c:forEach items="${aeropuertosDestino}" var="aeropuerto">
           <tr>
                <td>
            		<c:out value="${aeropuerto.nombre}"/>
            	</td>
            </tr>
        	</c:forEach>
        	
        </tbody>
    </table>
</aerolineasAAAFC:layout>