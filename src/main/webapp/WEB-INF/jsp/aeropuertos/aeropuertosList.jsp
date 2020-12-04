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
				<th>Localizacion</th>
				<th>codigoIATA</th>
				<th>Telefono</th>

			</tr>
		</thead>

		<tbody>
			<c:forEach items="${aeropuertos}" var="aeropuerto">
				<tr>
					<td>
						<%--- <spring:url value="/aeropuerto/{aeropuertoId}" var="aeropuertoURL">
                        <spring:param name="aeropuertoId" value="${aeropuerto.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(aeropuertoUrl)}">--%> 
                   <%-- <c:out value="${aeropuerto.id}" />--%>
					</td>
					<td><c:out value="${aeropuerto.nombre}" /></td>
					<td><c:out value="${aeropuerto.localizacion}" /></td>
					<td><c:out value="${aeropuerto.codigoIATA}" /></td>
					<td><c:out value="${aeropuerto.telefono}" /></td>
				<tr>
				<td>
				<a href="<spring:url value="/aeropuertos/${aeropuerto.id}/edit" htmlEscape="true" />">Editar</a>
				</td>
				<td>
				<a href="<spring:url value="/aeropuertos/${aeropuerto.id}/delete" htmlEscape="true" />">Eliminar</a>
				</td>
				</tr>
				
			</c:forEach>
		</tbody>

	</table>
	<a href="<spring:url value="/aeropuertos/new" htmlEscape="true" />">Nuevo</a>
				
</aerolineasAAAFC:layout>