<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<!-- Archivo .jsp que muestra el listado de todos los aviones, a su vez se
puede acceder a la ficha de cada avión (por id) para editarlo o borrarlo -->

<aerolineasAAAFC:layout pageName="aviones">
    <h2 class="centrado">Aviones</h2>

    <table id="tablaAviones" class="table table-striped centrado">
        <thead>
        <tr>
            <th class="centrado">ID Avión</th>
            <th class="centrado">Tipo de avión</th>
            <th class="centrado">Horas de vuelo acumuladas</th>
            <th class="centrado">Fecha de fabricación</th>
            <th class="centrado">Última revisión</th>
            <th class="centrado">Disponibilidad</th>
            <th class="centrado" width="15%">Opciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${aviones}" var="avion">
            <tr>
                <td onclick="javascript:location.href='/aviones/${avion.id}'" onmouseover="" style="cursor: pointer;">
                    <b><c:out value="${avion.id}"/></b>
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
                	<spring:url value="/aviones/{avionId}/edit" var="avionUrl">
        			<spring:param name="avionId" value="${avion.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(avionUrl)}" class="btn btn-default">Editar</a>
    				<spring:url value="/aviones/${avion.id}/delete" var="avionUrl"></spring:url>
    				<a href="${fn:escapeXml(avionUrl)}" class="btn btn-default">Eliminar</a>
                </td>     
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="<spring:url value="/aviones/new" htmlEscape="true"/>" class="btn btn-default">Nuevo Avión</a>
</aerolineasAAAFC:layout>