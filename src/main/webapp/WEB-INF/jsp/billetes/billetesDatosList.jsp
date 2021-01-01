<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="billetes">
    <h2>Billetes</h2>

    <table id="tablaBilletes" class="table table-striped">
        <thead>
        <tr>
            <th>ID Billete</th>
            <th>CLiente</th>
            <th>Precio</th>
            <th>Enbarque</th>
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
                    <c:out value="${billete.cliente.nombre}"/>
                </td>
                <td>
                    <c:out value="${billete.coste}"/>
                </td>
                <td>
                    <c:out value="${billete.vuelo.aeropuertoOrigen}"/>
                </td>
                <td>

    				<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar billete</a>
                </td>     
            </tr>
        </c:forEach>
        </tbody>
    </table>
</aerolineasAAAFC:layout>