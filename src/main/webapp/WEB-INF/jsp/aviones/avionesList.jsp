<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<!-- Archivo .jsp que muestra el listado de todos los aviones, a su vez se
puede acceder a la ficha de cada avi�n (por id) para editarlo o borrarlo -->

<aerolineasAAAFC:layout pageName="aviones">
    <h2>Aviones</h2>

    <table id="tablaAviones" class="table table-striped">
        <thead>
        <tr>
            <th>ID Avi�n</th>
            <th>Tipo de avi�n</th>
            <th>Capacidad de pasajeros</th>
            <th>Peso m�ximo de equipaje</th>
            <th>Horas de vuelo acumuladas</th>
            <th>Fecha de fabricaci�n</th>
            <th>Disponibilidad</th>
            <th>�ltima revisi�n</th>
            <th>Plazas clase econ�mica</th>
            <th>Plazas clase ejecutiva</th>
            <th>Plazas primera clase</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="avion">
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
                    <c:out value="${avion.capacidadPasajero}"/>
                </td>
                <td>
                    <c:out value="${avion.pesoMaximoEquipaje}"/>
                </td>
                <td>
                    <c:out value="${avion.fechaFabricacion}"/>
                </td>
                <td>
                    <c:choose>
    					<c:when test="${avion.disponibilidad==True}">
        					S� 
        					<br />
    					</c:when>    
    					<c:otherwise>
        					No 
        					<br />
    					</c:otherwise>
					</c:choose>
                </td>
                <td>
                    <c:out value="${avion.fechaRevision}"/>
                </td>
                <td>
                    <c:out value="${avion.plazasEconomica}"/>
                </td>
                <td>
                    <c:out value="${avion.plazasEjecutiva}"/>
                </td>
                <td>
                    <c:out value="${avion.plazasPrimera}"/>
                </td>    
            </tr>
        </c:forEach>
        </tbody>
    </table>
</aerolineasAAAFC:layout>