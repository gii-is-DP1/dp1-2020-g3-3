<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>


<aerolineasAAAFC:layout pageName="clientes">
	<h2>Clientes</h2>

	<table id="tablaClientes" class="table table-striped">
		<thead>
			<tr>
				<th>Numero</th>
				<th>Nombre y apellidos</th>
			</tr>
		</thead>

		<tbody>
			<c:set var="count" value="1" scope="page" />
			<c:forEach items="${clientes}" var="cliente">
				<tr>
					
					<td><c:out value="${count}"/></td>
					<c:set var="count" value="${count + 1}" scope="page"/>
					<td onclick="javascript:location.href='/clientes/${cliente.id}'" onmouseover="" style="cursor: pointer;"><b><c:out value="${cliente.nombre} ${cliente.apellidos}"/></b></td>
				
				</tr>
			</c:forEach>
		</tbody>

	</table>

				
</aerolineasAAAFC:layout>