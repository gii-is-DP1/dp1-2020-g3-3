<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<!-- Pagina en la que se mostraran los aeropuertos y se permitira acceder a modificarlos -->

<aerolineasAAAFC:layout pageName="aeropuertos">
	<h2 class="centrado">Aeropuertos</h2>

	<table id="tablaAeropuertos" class="table table-striped">
		<thead>
			<tr>
				
				<th width="24%" class="centrado">Nombre</th>
				<th width="15%" class="centrado">Localización</th>
				<th width="10%" class="centrado">códigoIATA</th>
				<th width="10%" class="centrado">Teléfono</th>
				<th width="10%" class="centrado">Vuelos Salida</th>
				<th width="10%" class="centrado">Vuelos Llegada</th>
				<th width="15%" class="centrado">Opciones</th>
			</tr>
		</thead>

		<tbody class="centrado">
			<c:forEach items="${aeropuertos}" var="aeropuerto">
				<tr>
					<td onclick="javascript:location.href='/aeropuertos/${aeropuerto.id}'" onmouseover="" style="cursor: pointer;"><b><c:out value="${aeropuerto.nombre}" /></b></td>
					<td><c:out value="${aeropuerto.localizacion}" /></td>
					<td><c:out value="${aeropuerto.codigoIATA}" /></td>
					<td><c:out value="${aeropuerto.telefono}" /></td>
					<td>
						<c:forEach items="${aeropuerto.vuelosSalida}" var="vuelo" varStatus="loop">
							<c:out value="${vuelo.id}"/>
							<c:if test="${!loop.last}">, </c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${aeropuerto.vuelosLlegada}" var="vuelo" varStatus="loop">
							<c:out value="${vuelo.id}"/>
							<c:if test="${!loop.last}">, </c:if>
						</c:forEach>
					</td>
					
					<td>
						<spring:url value="/aeropuertos/{aeropuertoId}/edit" var="aeropuertoUrl">
		        			<spring:param name="aeropuertoId" value="${aeropuerto.id}"/>
		    				</spring:url>
		    				<a href="${fn:escapeXml(aeropuertoUrl)}" class="btn btn-default">Editar</a>
		    				<spring:url value="/aeropuertos/${aeropuerto.id}/delete" var="aeropuertoUrl"></spring:url>
		    				<a href="${fn:escapeXml(aeropuertoUrl)}" class="btn btn-default">Eliminar</a>
					</td>
				
				</tr>
				
			</c:forEach>
		</tbody>

	</table>
	<a href="<spring:url value="/aeropuertos/new" htmlEscape="true" />">Nuevo aeropuerto</a>
				
</aerolineasAAAFC:layout>