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
    </table>

  	<spring:url value="{aeropuertoId}/edit" var="editUrl">
        <spring:param name="aeropuertoId" value="${aeropuerto.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Aeropuerto</a>

    <br/>
    <br/>
    
    <h2>Vuelos de Salida</h2>
	  <table class="table table-striped">
	  <tr>
			<th width="10%" bgcolor="#34302d" style="color: white; text-align: center;">Id:</th>
			<td style="padding-left:15px;">
		        <c:forEach var="vuelo" items="${aeropuerto.vuelosSalida}" varStatus="loop">
		          <b onclick="javascript:location.href='/vuelos/${vuelo.id}'" onmouseover="" style="cursor: pointer;">
		          <c:out value="${vuelo.id}"/>
		          </b>
		          <c:if test="${!loop.last}">, </c:if>
		        </c:forEach>
		   </td>
	   </tr>
	   </table>  
	   
 	<br/>
    <br/>
    
    <h2>Vuelos de Llegada</h2>
	  <table class="table table-striped">
	  <tr>
			<th width="10%" bgcolor="#34302d" style="color: white; text-align: center;">Id:</th>
			<td style="padding-left:15px;">
		        <c:forEach var="vuelo" items="${aeropuerto.vuelosLlegada}" varStatus="loop">
		          <b onclick="javascript:location.href='/vuelos/${vuelo.id}'" onmouseover="" style="cursor: pointer;">
		          <c:out value="${vuelo.id}"/>
		          </b>
		          <c:if test="${!loop.last}">, </c:if>
		        </c:forEach>
		   </td>
	   </tr>
	   </table>  
	<spring:url value="/aeropuertos" var="url"></spring:url>
    <a href="${fn:escapeXml(url)}" class="btn btn-default" style="float:right;">Volver</a>
</aerolineasAAAFC:layout>
