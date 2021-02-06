<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<!-- Archivo .jsp que muestra el listado de todos los aviones, a su vez se
puede acceder a la ficha de cada avión (por id) para editarlo o borrarlo -->

<aerolineasAAAFC:layout pageName="billetes">
    <h2>Billetes</h2>

    <table id="tablaBilletes" class="table table-striped">
        <thead>
        <tr>
            <th>ID Billete</th>
            <th>Precio de venta</th>
            <th>Asiento</th>
            <th>Fecha de reserva</th>
            <th>Clase</th>
            <th> </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="billete">
            <tr>
                <td>
                    <spring:url value="/billetes/{billeteId}" var="billeteUrl">
                        <spring:param name="billeteId" value="${billete.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(billeteUrl)}"><c:out value="${billete.id}"/></a>
                </td>
                <td>
                    <c:out value="${billete.coste}"/>
                </td>
                <td>
                    <c:out value="${billete.asiento}"/>
                </td>
                <td>
                    <c:out value="${billete.fechaReserva}"/>
                </td>
                <td>
                    <c:out value="${billete.clase}"/>
                </td>
                <td>
                	<spring:url value="{billeteId}/edit" var="billeteUrl">
        			<spring:param name="billeteId" value="${billete.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar billete</a>
                </td>     
            </tr>
        </c:forEach>

			<tr>
				<td></td><td></td><td></td><td></td><td></td><td></td>
				<td><a href="<spring:url value="/aviones/new" htmlEscape="true"/>"class="btn btn-default">Nuevo Avión</a></td>
			</tr>
        </tbody>
    </table>
</aerolineasAAAFC:layout>