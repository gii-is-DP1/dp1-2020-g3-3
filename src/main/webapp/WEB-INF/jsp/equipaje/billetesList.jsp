<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<!-- Archivo .jsp que muestra todos los equipajes -->

<aerolineasAAAFC:layout pageName="equipajes">
    <h2>Equipajes</h2>

    <table id="tablaEquipajes" class="table table-striped">
        <thead>
        <tr>
            <th>ID Billete</th>
            <th>Dimensiones</th>
            <th>Peso</th>
            <th>Precio</th>
            <th> </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="equipajes">
            <tr>
                <td>
                    <spring:url value="/equipajes/{billeteId}" var="billeteUrl">
                        <spring:param name="billeteId" value="${billete.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(billeteUrl)}"><c:out value="${billete.id}"/></a>
                </td>
                <td>
                    <c:out value="${equipaje.dimensiones}"/>
                </td>
                <td>
                    <c:out value="${equipaje.peso}"/>
                </td>
                <td>
                    <c:out value="${equipaje.precio}"/>
                </td>
                <td>
                	<spring:url value="{billeteId}/edit" var="billeteUrl">
        			<spring:param name="billeteId" value="${billete.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar equipaje</a>
                </td>     
            </tr>
        </c:forEach>
        </tbody>
    </table>
</aerolineasAAAFC:layout>