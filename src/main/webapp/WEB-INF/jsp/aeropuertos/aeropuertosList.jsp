<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<!-- Pagina en la que se mostraran los aeropuertos y se permitira acceder a modificarlos -->

<aerolineasAAAFC:layout pageName="aeropuertos">
	<h2>Aeropuertos</h2>

	<table id="tablaAeropuertos" class="table table-striped">
		<thead>
			<tr>
			<th></th>
				<th>Nombre</th>
				<th>Localizaci�n</th>
				<th>c�digoIATA</th>
				<th>Tel�fono</th>
				<th>Vuelos Salida</th>
				<th>Vuelos Llegada</th>

			</tr>
		</thead>

		<tbody>
			<c:forEach items="${aeropuertos}" var="aeropuerto">
				<tr>
					<td>
					</td>
					<td><c:out value="${aeropuerto.nombre}" /></td>
					<td><c:out value="${aeropuerto.localizacion}" /></td>
					<td><c:out value="${aeropuerto.codigoIATA}" /></td>
					<td><c:out value="${aeropuerto.telefono}" /></td>
					<td><c:out value="${aeropuerto.vuelosSalida}" /></td>
					<td><c:out value="${aeropuerto.vuelosLlegada}" /></td>
					
				<tr>
				<td>
				<a href="<spring:url value="/aeropuertos/${aeropuerto.id}/edit" htmlEscape="true" />">Editar aeropuerto</a>
				</td>
				<td>
				<a href="<spring:url value="/aeropuertos/${aeropuerto.id}/delete" htmlEscape="true" />">Eliminar aeropuerto</a>
				</td>
				</tr>
				
			</c:forEach>
		</tbody>

	</table>
	<a href="<spring:url value="/aeropuertos/new" htmlEscape="true" />">Nuevo aeropuerto</a>
				
</aerolineasAAAFC:layout>