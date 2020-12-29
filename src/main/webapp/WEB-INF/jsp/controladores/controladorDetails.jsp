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
            <td><b><c:out value="${personalControl.nombre} ${personalControl.apellidos}"/></b></td>
        </tr>
        <tr>
            <th>NIF</th>
            <td><c:out value="${personalControl.nif}"/></td>
        </tr>
        <tr>
            <th>IBAN</th>
            <td><c:out value="${personalControl.iban}"/></td>
        </tr>
        
       <tr>
            <th>Rol</th>
            <td>
               <c:if test="${personalControl.rol eq 'PILOTO'}">
                	<c:out value="Piloto"/>
                </c:if>
                <c:if test="${personalControl.rol eq 'INGENIERO_DE_VUELO'}">
                	<c:out value="Ingeniero de vuelo"/>
                </c:if>
                <c:if test="${personalControl.rol eq 'COPILOTO'}">
                	<c:out value="Copiloto"/>
                </c:if>
            </td>
        </tr>
        
        <tr>
            <th>Salario</th>
            <td><c:out value="${personalControl.salario}"/></td>
        </tr>
        
    </table>

  	<spring:url value="{pControlId}/edit" var="editUrl">
        <spring:param name="pControlId" value="${personalControl.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Controlador</a>


    <br/>
    <br/>
  


</aerolineasAAAFC:layout>
