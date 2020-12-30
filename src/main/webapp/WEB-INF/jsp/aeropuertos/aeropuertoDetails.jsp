<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="aeropuertos">

    <h2>Información del Aeropuerto</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre </th>
            <td><c:out value="${aeropuerto.nombre}"/></td>
        </tr>
        <tr>
            <th>Localizacion</th>
            <td><c:out value="${aeropuerto.localizacion}"/></td>
        </tr>
        <tr>
            <th>Código IATA</th>
            <td><c:out value="${aeropuerto.codigoIATA}"/></td>
        </tr>
        <tr>
            <th>Teléfono</th>
            <td><c:out value="${aeropuerto.telefono}"/></td>
        </tr>
        <tr>
            <th>Vuelo Salida</th>
            <td><c:out value="${aeropuerto.vuelosSalida}"/></td>
        </tr>    
         <tr>
            <th>Vuelo llegada</th>
            <td><c:out value="${aeropuerto.vuelosLlegada}"/></td>
        </tr> 
        
    </table>

  	<spring:url value="{aeropuertoId}/edit" var="editUrl">
        <spring:param name="aeropuertoId" value="${aeropuerto.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Aeropuerto</a>


    <br/>
    <br/>
  


</aerolineasAAAFC:layout>
