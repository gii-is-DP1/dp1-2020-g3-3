<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<!-- Pagina en la que se mostraran los vuelos y se permitira acceder a modificarlos -->

<aerolineasAAAFC:layout pageName="vuelos">
    <h2>Aviones</h2>

    <table id="tablaVuelos" class="table table-striped">
        <thead>
        <tr>
            <th>ID Vuelo</th>
            <th>Fecha del vuelo</th>
            <th>Hora de salida</th>
            <th>Hora de llegada</th>
            <th>Coste del vuelo</th>
           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="vuelo">
            <tr>
                <td>
                    <spring:url value="/vuelo/{vueloId}" var="vueloURL">
                        <spring:param name="vueloId" value="${vuelo.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(vueloUrl)}"><c:out value="${avion.id}"/></a>
                </td>
                <td>
                    <c:out value="${vuelo.fechaVuelo}"/>
                </td>
                <td>
                    <c:out value="${vuelo.horaSalida}"/>
                </td>
                <td>
                    <c:out value="${vuelo.horaLlegada}"/>
                </td>
                <td>
                    <c:out value="${vuelo.coste}"/>
                </td>
           </tr>
        </c:forEach>
        </tbody>
    </table>
</aerolineasAAAFC:layout>