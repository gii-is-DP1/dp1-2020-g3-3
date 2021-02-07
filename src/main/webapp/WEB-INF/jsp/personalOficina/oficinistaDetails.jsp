<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<aerolineasAAAFC:layout pageName="personalOficina">

    <h2>Información del Personal de Oficina</h2>
	<sec:authorize 	access="hasAnyAuthority('admin')">
		<div style="float:right; padding-bottom: 1%;">
   	 	<a href="/personalOficina" class="btn btn-default" >Volver</a>
   	 	</div>
	</sec:authorize>

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
	<sec:authorize 	access="hasAuthority('admin')">
	  	<spring:url value="/personalOficina/{pOficinaId}/edit" var="editUrl">
	        <spring:param name="pOficinaId" value="${personalOficina.id}"/>
	    </spring:url>
	    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Oficinista</a>
	</sec:authorize>
    <br/>
    <br/>
    <h2>Vuelos gestionados</h2>

    <table class="table table-striped">
    
	    <thead>
	        <tr>
	            <th>Ruta</th>
	            <th>Fecha de salida</th>
	            <th>Fecha de llegada</th>
	        </tr>
	        </thead>
	        <tbody>
		        <c:forEach var="vuelo" items="${personalOficina.vuelos}">
		            <tr>
		            	<td>
		                    <b onclick="javascript:location.href='/aeropuertos/${vuelo.aeropuertoOrigen.id}'" onmouseover="" style="cursor: pointer;"><c:out value="${vuelo.aeropuertoOrigen.codigoIATA}"/></b> -
		                    <b onclick="javascript:location.href='/aeropuertos/${vuelo.aeropuertoDestino.id}'" onmouseover="" style="cursor: pointer;"><c:out value="${vuelo.aeropuertoDestino.codigoIATA}"/></b>
		                </td>
		                <td>
		                    <aerolineasAAAFC:localDateTime date="${vuelo.fechaSalida}" pattern="yyyy-MM-dd HH:mm"/>
		                </td>
		                <td>  
		                    <aerolineasAAAFC:localDateTime date="${vuelo.fechaLlegada}" pattern="yyyy-MM-dd HH:mm"/>
		                </td>
		            </tr>
		        </c:forEach>
        </tbody>
    </table>


</aerolineasAAAFC:layout>