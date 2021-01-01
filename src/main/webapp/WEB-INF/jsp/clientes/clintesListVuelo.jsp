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

				<th>Nombre y apellidos</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${clientes}" var="cliente">
				<tr>
				
					<td onclick="javascript:location.href='/clientes/${cliente.id}'" onmouseover="" style="cursor: pointer;"><b><c:out value="${cliente.nombre} ${cliente.apellidos}"/></b></td>
					<td><c:out value="${cliente.nombre}"/></td>

				
				</tr>
			</c:forEach>
		</tbody>

	</table>

				
</aerolineasAAAFC:layout>