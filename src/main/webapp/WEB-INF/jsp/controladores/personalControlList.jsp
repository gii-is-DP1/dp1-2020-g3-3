<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- .jsp para mostrar el listado de todos los controladores -->

<aerolineasAAAFC:layout pageName="controladores">
    <h2 class="centrado">Personal de Control</h2>
    
	<div style="float:right; padding-bottom: 1%;" >
	<form:form action="/controladoresfind" method="get">
           <label>NIF </label>
           <input name="nif" pattern="^\d{8}[a-zA-Z]$" type="text"/>
           <button type="submit" class="btn btn-default">Encontrar Controlador</button>
    </form:form>
    </div>
    
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
        <c:choose>
			<c:when test="${personalControl.size() > 0 }">
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
		    				
		    				<sec:authorize 	access="hasAuthority('admin')">	
			    				<spring:url value="/controladores/${personalControl.id}/delete" var="personalControlUrl"></spring:url>
			    				<a href="${fn:escapeXml(personalControlUrl)}" class="btn btn-default">Eliminar</a>
		    				</sec:authorize>
		                </td>   
		            </tr>
		        </c:forEach>
       		</c:when>
			<c:otherwise>
				<tr class="centrado">
					<c:if test="${not empty msg}"><td colspan="10"><h3><c:out value="${msg} (${param.nif})"/></h3></td></c:if>
					<c:if test="${empty msg}"><td colspan="10"><h3>¡Lo sentimos! No se encuentra ningún controlador.</h3></td></c:if>
				</tr>
			</c:otherwise>
		</c:choose>
		<tr>
			<td></td><td></td><td></td><td></td><td></td>
			<td><a href="<spring:url value="/controladores/new" htmlEscape="true"/>" class="btn btn-default">Nuevo controlador</a></td>
		</tr>
        </tbody>
    </table>
        
    	 <div class="panel-footer centrado">
	 <h3>Mostrando página ${number+1} de ${totalPages}</h3>
            <ul class="pagination" style="margin: 0px;">
                <c:forEach begin="0" end="${totalPages-1}" var="page">
                    <li class="page-item">
                        <a href="controladores?page=${page}&size=${size}" class="page-link">${page+1}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
</aerolineasAAAFC:layout>