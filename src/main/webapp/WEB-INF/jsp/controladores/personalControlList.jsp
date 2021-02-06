<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<!-- .jsp para mostrar el listado de todos los controladores -->

<aerolineasAAAFC:layout pageName="controladores">
    <h2 class="centrado">Personal de Control</h2>

    <table id="tablaPersonalControl" class="table table-striped centrado">
        <thead>
        <tr>
        
            <th class="centrado">Nombre y Apellidos</th>
            <th class="centrado">NIF</th>
            <th class="centrado">IBAN</th>
            <th class="centrado">Rol</th>
            <th class="centrado">Salario</th>
            <th class="centrado" width="15%">Opciones</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${personalControl}" var="personalControl">
            <tr>
                
                <td onclick="javascript:location.href='/controladores/${personalControl.id}'" onmouseover="" style="cursor: pointer;">
                <b><c:out value="${personalControl.nombre} ${personalControl.apellidos}"/></b>
                </td>
                <td>
                    <c:out value="${personalControl.nif}"/>
                </td>
                <td>
                    <c:out value="${personalControl.iban}"/>
                </td>
                 <td>
                    <c:if test="${personalControl.rol eq 'PILOTO'}">Piloto</c:if>
                    <c:if test="${personalControl.rol eq 'COPILOTO'}">Copiloto</c:if>
                    <c:if test="${personalControl.rol eq 'INGENIERO_DE_VUELO'}">Ingenierio de vuelo</c:if>
                </td>
                <td>
                    <c:out value="${personalControl.salario}"/>
                </td>
                <td>						
                	<spring:url value="/controladores/{personalControlId}/edit" var="personalControlUrl">
        				<spring:param name="personalControlId" value="${personalControl.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(personalControlUrl)}" class="btn btn-default">Editar</a>
    				
    				<spring:url value="/controladores/${personalControl.id}/delete" var="personalControlUrl"></spring:url>
    				<a href="${fn:escapeXml(personalControlUrl)}" class="btn btn-default">Eliminar</a>
                </td>   
            </tr>
        </c:forEach>
        </tbody>
    </table>
        <a href="<spring:url value="/controladores/new" htmlEscape="true"/>" class="btn btn-default">Nuevo controlador</a>
    
</aerolineasAAAFC:layout>