<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<!-- .jsp para mostrar el listado de todos los controladores -->

<aerolineasAAAFC:layout pageName="controladores">
    <h2>Personal de Control</h2>

    <table id="tablaPersonalControl" class="table table-striped">
        <thead>
        <tr>
        	<th>ID Controlador</th>
            <th>Nombre</th>
            <th>Apellidos</th>
            <th>NIF</th>
            <th>IBAN</th>
            <th>Rol</th>
            <th>Salario</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${controladores}" var="personalControl">
            <tr>
                <td>
                    <spring:url value="/controladores/{pControlId}" var="pControlUrl">
                        <spring:param name="pControlId" value="${personalControl.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(pControlUrl)}"><c:out value="${personalControl.id}"/></a>
                </td>
                <td>
                    <c:out value="${personalControl.nombre}"/>
                </td>
                <td>
                    <c:out value="${personalControl.apellidos}"/>
                </td>
                <td>
                    <c:out value="${personalControl.nif}"/>
                </td>
                <td>
                    <c:out value="${personalControl.iban}"/>
                </td>
                 <td>
                    <c:out value="${personalControl.rol}"/>
                </td>
                <td>
                    <c:out value="${personalControl.salario}"/>
                </td>
                <td>
                    <spring:url value="/controladores/{pControlId}/edit" var="pControlUrl">
        			<spring:param name="pControlId" value="${personalControl.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(pControlUrl)}" class="btn btn-default">Editar controlador</a>
                </td>   
            </tr>
        </c:forEach>
        </tbody>
    </table>
        <a href="<spring:url value="/controladores/new" htmlEscape="true" />">Nuevo controlador</a>
    
</aerolineasAAAFC:layout>