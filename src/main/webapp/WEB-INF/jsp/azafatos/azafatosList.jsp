<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<!-- Pagina en la que se mostraran los azafatos y se permitira acceder a modificarlos -->

<aerolineasAAAFC:layout pageName="azafatos">
	<h2>Azafatos</h2>

	<table id="tablaAzafatos" class="table table-striped">
		<thead>
			<tr>
			<th></th>
				<th>Nombre</th>
				<th>Localizacion</th>
				<th>codigoIATA</th>
				<th>Telefono</th>

			</tr>
		</thead>

		<tbody>
			<c:forEach items="${azafatos}" var="azafato">
				<tr>
					<td>
					</td>
					<td><c:out value="${azafato.nombre}" /></td>
					<td><c:out value="${azafato.localizacion}" /></td>
					<td><c:out value="${azafato.codigoIATA}" /></td>
					<td><c:out value="${azafato.telefono}" /></td>
				<tr>
				<td>
				<a href="<spring:url value="/azafatos/${azafato.id}/edit" htmlEscape="true" />">Editar</a>
				</td>
				<td>
				<a href="<spring:url value="/azafatos/${azafato.id}/delete" htmlEscape="true" />">Eliminar</a>
				</td>
				</tr>
				
			</c:forEach>
		</tbody>

	</table>
	<a href="<spring:url value="/azafatos/new" htmlEscape="true" />">Nuevo</a>
				
</aerolineasAAAFC:layout>