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
				<th>Nombre y Apellidos</th>
				<th>NIF</th>
				<th>IBAN</th>
				<th>Idiomas</th>
				<th>Salario</th>
				<th>Usuario</th>
				<th>Contraseña</th>
				<th>Opciones</th>

			</tr>
		</thead>

		<tbody>
			<c:forEach items="${azafatos}" var="azafato">
				<tr>
					<td>
					</td>
					<td><c:out value="${azafato.nombre} ${azafato.apellidos}"/></td>
					<td><c:out value="${azafato.nif}" /></td>
					<td><c:out value="${azafato.iban}" /></td>
					<td><c:forEach items="${azafato.idiomas}" var="idioma" varStatus="loop">
							<c:out value="${idioma.idioma}"/>
							<c:if test="${!loop.last}">, </c:if>
						</c:forEach>
					</td>
					<td><c:out value="${azafato.salario}" /></td>
					<td><c:out value="${azafato.user.username}"/></td>
					<td><c:out value="${azafato.user.password}"/></td>
					<td>
						<a href="<spring:url value="/azafatos/${azafato.id}/edit" htmlEscape="true" />">Editar azafato</a>
						<a href="<spring:url value="/azafatos/${azafato.id}/delete" htmlEscape="true" />">Eliminar azafato</a>
					</td>
				</tr>
				
			</c:forEach>
		</tbody>

	</table>
	<a href="<spring:url value="/azafatos/new" htmlEscape="true" />">Nuevo</a>
				
</aerolineasAAAFC:layout>