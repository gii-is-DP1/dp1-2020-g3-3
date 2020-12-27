<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="controladores">

    <h2>Información del Personal de Control</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre y apellidos</th>
            <td><b><c:out value="${controladores.nombre} ${controladores.apellidos}"/></b></td>
        </tr>
        <tr>
            <th>NIF</th>
            <td><c:out value="${controladores.nif}"/></td>
        </tr>
        <tr>
            <th>IBAN</th>
            <td><c:out value="${controladores.iban}"/></td>
        </tr>
        
       <tr>
            <th>Rol</th>
            <td>
                <c:choose>
    				<c:when test="${controladores.rol==true}">
        				Piloto 
        				<br />
    				</c:when>    
    				<c:otherwise>
        				Copiloto
        				Ingeniero de Vuelo 
        				<br />
    				</c:otherwise>
				</c:choose>
            </td>
        </tr>
        
        <tr>
            <th>Salario</th>
            <td><c:out value="${controladores.salario}"/></td>
        </tr>
        
    </table>

  	<spring:url value="{pControlId}/edit" var="editUrl">
        <spring:param name="pControlId" value="${controladores.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Controlador</a>


    <br/>
    <br/>
  


</aerolineasAAAFC:layout>
