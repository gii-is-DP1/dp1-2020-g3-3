<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>


<!-- Pagina en la que se mostraran un vuelo en concreto-->

<aerolineasAAAFC:layout pageName="vuelos">
    <h2>Información del vuelo</h2>

    <table class="table table-striped">
        <tr>
            <th>Fecha de salida</th>
            <td><aerolineasAAAFC:localDateTime date="${vuelo.fechaSalida}" pattern="dd-MM-yyyy HH:mm"/></td>
            <th>Fecha de llegada</th>
            <td><aerolineasAAAFC:localDateTime date="${vuelo.fechaLlegada}" pattern="dd-MM-yyy HH:mm"/></td>
            <th>Aeropuerto de Salida</th>
            <td><c:out value="${vuelo.aeropuertoOrigen.nombre}"/></td>
            <th>Aeropuerto de Llegada</th>
            <td><c:out value="${vuelo.aeropuertoDestino.nombre}"/></td>
            <th>Coste del vuelo</th>
            <td><c:out value="${vuelo.coste}"/></td>
        </tr>
    </table>
    
     <spring:url value="{vueloId}/edit" var="editUrl">
        <spring:param name="vueloId" value="${vuelo.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Vuelo</a>
    
    <br/>
    <br/>
    
    <h2>Avión</h2>
    <table class="table table-striped">
    <thead>
    	<tr>
			<th width="25%">Tipo de Avión</th>
			<th width="25%">Capacidad de Pasajeros</th>
			<th width="25%">Peso Máximo de Equipaje</th>
			<th width="25%">Fecha Última Revisión</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td onclick="javascript:location.href='/aviones/${vuelo.avion.id}'" onmouseover="" style="cursor: pointer;">
				<b><c:out value="${vuelo.avion.tipoAvion}"/></b>
			</td>
			<td><c:out value="${vuelo.avion.capacidadPasajero}"/></td>
			<td><c:out value="${vuelo.avion.pesoMaximoEquipaje}"/></td>
			<td><c:out value="${vuelo.avion.fechaRevision}"/></td>
		</tr>
	</tbody>
	</table>
	
    <h2>Personal de Control</h2>
    <table class="table table-striped">
    <thead>
    	<tr>
			<th width="40%">Nombre y Apellidos</th>
			<th width="30%">NIF</th>
			<th width="30%">Rol</th>
		</tr>
	</thead>
	<tbody>
        <c:forEach var="pControl" items="${vuelo.personalControl}">
            <tr>
            	<td onclick="javascript:location.href='/controladores/${pControl.id}'" onmouseover="" style="cursor: pointer;">
            		<b><c:out value="${pControl.nombre} ${pControl.apellidos} "/></b>
            	</td>
                <td><c:out value="${pControl.nif}"/></td>
                <td>
                	<c:if test="${pControl.rol eq 'PILOTO'}">
                		<c:out value="Piloto"/>
                	</c:if>
                	<c:if test="${pControl.rol eq 'INGENIERO_DE_VUELO'}">
                		<c:out value="Ingeniero de vuelo"/>
                	</c:if>
                	<c:if test="${pControl.rol eq 'COPILOTO'}">
                		<c:out value="Copiloto"/>
                	</c:if>
                </td>
            </tr>
        </c:forEach>
   </tbody>
   </table>  
      
   <h2>Azafatos</h2> 
   <table class="table table-striped"> 
   <thead>
    	<tr>
			<th width="40%">Nombre y Apellidos</th>
			<th width="30%">NIF</th>
			<th width="30%">Idiomas</th>
		</tr>
	</thead>
	<tbody>
        <c:forEach var="azafato" items="${vuelo.azafatos}">
            <tr>
            	<td onclick="javascript:location.href='/azafatos/${azafato.id}'" onmouseover="" style="cursor: pointer;">
            		<b><c:out value="${azafato.nombre} ${azafato.apellidos} "/></b>
            	</td>
            	<td><c:out value="${azafato.nif}"/></td>
                <td>
                    <c:forEach items="${azafato.idiomas}" var="idioma" varStatus="loop">
						<c:out value="${idioma.idioma}"/>
						<c:if test="${!loop.last}">, </c:if>
					</c:forEach>
				</td>
            </tr>
        </c:forEach>
    </table> 
    
</aerolineasAAAFC:layout>