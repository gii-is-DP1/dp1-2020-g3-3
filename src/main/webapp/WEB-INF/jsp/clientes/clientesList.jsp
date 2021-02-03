<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<!-- Pagina en la que se mostraran los clientes y se permitir� eliminarlos -->

<aerolineasAAAFC:layout pageName="clientes">
	<h2>Clientes</h2>

	<table id="tablaClientes" class="table table-striped">
		<thead>
			<tr>

				<th>Nombre y apellidos</th>
				<th>NIF</th>
				<th>Direcci�n de Facturaci�n</th>
				<th>IBAN</th>
				<th>Fecha de Nacimiento</th>
				<th>Email</th>
				<th width="15%">Opciones</th>


			</tr>
		</thead>

		<tbody>
			<c:forEach items="${clientes}" var="cliente">
				<tr>
				
					<td onclick="javascript:location.href='/clientes/${cliente.id}'" onmouseover="" style="cursor: pointer;"><b><c:out value="${cliente.nombre} ${cliente.apellidos}"/></b></td>
					<td><c:out value="${cliente.nif}"/></td>
					<td><c:out value="${cliente.direccionFacturacion}"/></td>
					<td><c:out value="${cliente.iban}"/></td>
					<td><aerolineasAAAFC:localDate date="${cliente.fechaNacimiento}" pattern="dd-MM-yyy"/></td>
					<td><c:out value="${cliente.email}"/></td>
					<td>
						<spring:url value="/clientes/{clienteId}/edit" var="clientesUrl">
	        				<spring:param name="clienteId" value="${cliente.id}"/>
	    				</spring:url>
	    				<a href="${fn:escapeXml(clientesUrl)}" class="btn btn-default">Editar</a>
	    				
	    				<spring:url value="/clientes/{clienteId}/delete" var="clientesUrl">
	        				<spring:param name="clienteId" value="${cliente.id}"/>
	    				</spring:url>
	    				<a href="${fn:escapeXml(clientesUrl)}" class="btn btn-default">Eliminar</a>
					</td>
				
				</tr>
			</c:forEach>
		</tbody>

	</table>
	<a href="<spring:url value="/clientes/new" htmlEscape="true" />">Nuevo Cliente</a>
				
</aerolineasAAAFC:layout>