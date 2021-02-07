<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<aerolineasAAAFC:layout pageName="aviones">

    <h2> Informaci�n t�cnica sobre el avi�n con ID: <c:out value="${avion.id}"/></h2>


    <table class="table table-striped">
        <tr>
            <th>Tipo</th>
            <td><b><c:out value="${avion.tipoAvion}"/></b></td>
        </tr>
        <tr>
            <th>Horas de vuelo acumuladas</th>
            <td><c:out value="${avion.horasAcumuladas}"/></td>
        </tr>
        <tr>
            <th>Fecha de fabricaci�n</th>
            <td><c:out value="${avion.fechaFabricacion}"/></td>
        </tr>
        <tr>
            <th>�ltima revisi�n</th>
            <td><c:out value="${avion.fechaRevision}"/></td>
        </tr>
        <tr>
            <th>Disponibilidad</th>
            <td>
                <c:choose>
    				<c:when test="${avion.disponibilidad==true}">
        				S� 
        				<br />
    				</c:when>    
    				<c:otherwise>
        				No 
        				<br />
    				</c:otherwise>
				</c:choose>
            </td>
        </tr>
    </table>
    
    <h2> Informaci�n sobre capacidad de pasajeros</h2>
    <table class="table table-striped">
    	<tr>
            <th>N�mero total de pasajeros</th>
            <td><c:out value="${avion.capacidadPasajero}"/></td>
        </tr>
        <tr>
            <th>Plazas clase econ�mica</th>
            <td><c:out value="${avion.plazasEconomica}"/></td>
        </tr>
        <tr>
            <th>Plazas clase ejecutiva</th>
            <td><c:out value="${avion.plazasEjecutiva}"/></td>
        </tr>
        <tr>
            <th>Plazas primera clase</th>
            <td><c:out value="${avion.plazasPrimera}"/></td>
        </tr>
    </table>
	<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')">
	    <spring:url value="/aviones/{avionId}/edit" var="avionUrl">
	        <spring:param name="avionId" value="${avion.id}"/>
	    </spring:url>
	    <a href="${fn:escapeXml(avionUrl)}" class="btn btn-default" style="float: right;">Editar avi�n</a>
	</sec:authorize>

    <br/>
    <br/>

    <h2>Vuelos asociados</h2>

    <table class="table table-striped">
    
	    <thead>
	        <tr>
	            <th>Ruta</th>
	            <th>Fecha de salida</th>
	            <th>Fecha de llegada</th>
	        </tr>
	        </thead>
	        <tbody>
		        <c:forEach var="vuelo" items="${avion.vuelos}">
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
	<spring:url value="/aviones" var="url"></spring:url>
    <a href="${fn:escapeXml(url)}" class="btn btn-default" style="float:right;">Volver</a>

</aerolineasAAAFC:layout>