<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="oficinistas">

    <h2>Información del Personal de Oficina</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre y apellidos</th>
            <td><b><c:out value="${personalOficina.nombre} ${personalOficina.apellidos}"/></b></td>
        </tr>
        <tr>
            <th>NIF</th>
            <td><c:out value="${personalOficina.nif}"/></td>
        </tr>
        <tr>
            <th>IBAN</th>
            <td><c:out value="${personalOficina.iban}"/></td>
        </tr>
        
        
        <tr>
            <th>Salario</th>
            <td><c:out value="${personalOficina.salario}"/></td>
        </tr>
        
    </table>

  	<spring:url value="/oficinistas/{pOficinaId}/edit" var="editUrl">
        <spring:param name="pOficinaId" value="${personalOficina.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Oficinista</a>


    <br/>
    <br/>
  


</aerolineasAAAFC:layout>
