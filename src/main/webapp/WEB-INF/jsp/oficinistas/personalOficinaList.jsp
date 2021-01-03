<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<!-- Archivo .jsp que muestra el listado de todos los oficinistas -->

<aerolineasAAAFC:layout pageName="oficinistas">
    <h2>Personal de oficina</h2>

    <table id="tablaPersonalOficina" class="table table-striped">
        <thead>
        <tr>
        	<th>ID Oficinista</th>
            <th>Nombre</th>
            <th>Apellidos</th>
            <th>NIF</th>
            <th>IBAN</th>
            <th>Salario</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${oficinistas}" var="oficinista">
            <tr>
                <td>
                    <spring:url value="/oficinistas/{pOficinaId}" var="pOficinaUrl">
                        <spring:param name="pOficinaId" value="${oficinista.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(pOficinaUrl)}"><c:out value="${oficinista.id}"/></a>
                </td>
                <td>
                    <c:out value="${oficinista.nombre}"/>
                </td>
                <td>
                    <c:out value="${oficinista.apellidos}"/>
                </td>
                <td>
                    <c:out value="${oficinista.nif}"/>
                </td>
                <td>
                    <c:out value="${oficinista.iban}"/>
                </td>
                <td>
                    <c:out value="${oficinista.salario}"/>
                </td>
                <td>
                	<spring:url value="/oficinistas/{pOficinaId}/edit" var="pOficinaUrl">
        			<spring:param name="pOficinaId" value="${oficinista.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(pOficinaUrl)}" class="btn btn-default">Editar oficinista</a>
                </td>   
            </tr>
        </c:forEach>
        </tbody>
    </table>
</aerolineasAAAFC:layout>