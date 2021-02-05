<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<!-- Pagina en la que se mostraran los clientes y se permitirá eliminarlos -->

<aerolineasAAAFC:layout pageName="clientes">
	<h2 class="centrado">Clientes</h2>
	
	<div style="float:right; padding-bottom: 1%;" >
	<form:form action="/clientesfind" method="get">
           <label>NIF </label>
           <input name="nif" pattern="^\d{8}[a-zA-Z]$" type="text"/>
           <button type="submit" class="btn btn-default">Encontrar Cliente</button>

    </form:form>
    </div>

	<table id="tablaClientes" class="table table-striped" >
		<thead>
			<tr>

				<th class="centrado">Nombre y apellidos</th>
				<th class="centrado">NIF</th>
				<th class="centrado">Dirección de Facturación</th>
				<th class="centrado">IBAN</th>
				<th class="centrado">Fecha de Nacimiento</th>
				<th class="centrado">Email</th>
				<th class="centrado" width="15%">Opciones</th>


			</tr>
		</thead>

		<tbody class="centrado">
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