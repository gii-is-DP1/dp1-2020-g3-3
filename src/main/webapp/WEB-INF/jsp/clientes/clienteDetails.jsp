<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<aerolineasAAAFC:layout pageName="clientes">

    <h2>Información del Cliente</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre y apellidos</th>
            <td><b><c:out value="${cliente.nombre} ${cliente.apellidos}"/></b></td>
        </tr>
        <tr>
            <th>NIF</th>
            <td><c:out value="${cliente.nif}"/></td>
        </tr>
        <tr>
            <th>Dirección de Facturación</th>
            <td><c:out value="${cliente.direccionFacturacion}"/></td>
        </tr>
        <tr>
            <th>IBAN</th>
            <td><c:out value="${cliente.iban}"/></td>
        </tr>
        
        <tr>
            <th>Fecha de Nacimiento</th>
            <td><aerolineasAAAFC:localDate date="${cliente.fechaNacimiento}" pattern="dd-MM-yyy"/></td>
        </tr>
        
        <tr>
            <th>Email</th>
            <td><c:out value="${cliente.email}"/></td>
        </tr>
        
        <tr>
            <th>Usuario</th>
            <td><c:out value="${cliente.user.username}"/></td>
        </tr>
    </table>

  	<spring:url value="{clienteId}/edit" var="editUrl">
        <spring:param name="clienteId" value="${cliente.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Cliente</a>

	<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')">
    <br/>
    <br/>
    <h2>Billetes</h2>
    
	<a href="<spring:url value="/clientes/${cliente.id}/compras" htmlEscape="true" />" class="btn btn-default">Ver billetes</a>
	</sec:authorize>
	<br/>
    <br/>
    
	<h2>Historial de vuelos</h2>

    <table class="table table-striped">
        <thead>
	        <tr>
	            <th>Ruta</th>
	            <th>Fecha de salida</th>
	            <th>Fecha de llegada</th>
	        </tr>
	        </thead>
	        <tbody>
		        <c:forEach var="billete" items="${cliente.billetes}">
		            <tr>
		            	<td>
		                    <b><c:out value="${billete.asiento.vuelo.aeropuertoOrigen.codigoIATA}"/></b> -
		                    <b><c:out value="${billete.asiento.vuelo.aeropuertoDestino.codigoIATA}"/></b>
		                </td>
		                <td>
		                    <aerolineasAAAFC:localDateTime date="${billete.asiento.vuelo.fechaSalida}" pattern="yyyy-MM-dd HH:mm"/>
		                </td>
		                <td>  
		                    <aerolineasAAAFC:localDateTime date="${billete.asiento.vuelo.fechaLlegada}" pattern="yyyy-MM-dd HH:mm"/>
		                </td>
		            </tr>
		        </c:forEach>
        </tbody>
    </table>

</aerolineasAAAFC:layout>
