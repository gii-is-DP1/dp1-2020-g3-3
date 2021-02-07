<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<aerolineasAAAFC:layout pageName="controladores">

    <h2>Información del Personal de Control</h2>
	<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')">
		<div style="float:right; padding-bottom: 1%;">
   	 	<a href="/controladores" class="btn btn-default" >Volver</a>
   	 	</div>
	</sec:authorize>

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
    
	<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')">
	  	<spring:url value="/controladores/{pControlId}/edit" var="editUrl">
	        <spring:param name="pControlId" value="${personalControl.id}"/>
	    </spring:url>
	    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default" style="float:left;">Editar Controlador</a>
	</sec:authorize>
	
	<spring:url value="/controladores/{pControlId}/horario" var="editUrl">
        <spring:param name="pControlId" value="${personalControl.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default" style="float:right;">Horario</a>

    <br/>
    <br/>
  
  	<h2>Vuelos en los que formó parte de la tripulación</h2>

        <table class="table table-striped">
    
	    <thead>
	        <tr>
	            <th>Ruta</th>
	            <th>Fecha de salida</th>
	            <th>Fecha de llegada</th>
	        </tr>
	        </thead>
	        <tbody>
		        <c:forEach var="vuelo" items="${personalControl.vuelos}">
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
