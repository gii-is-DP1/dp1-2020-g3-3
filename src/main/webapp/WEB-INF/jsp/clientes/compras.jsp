<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="clientes">

	<h2>Historial de compra de <c:out value="${cliente.nombre} ${cliente.apellidos}"/> (cliente con ID: <c:out value="${cliente.id}"/>)</h2>


	<table id="tablaBilletes" class="table table-striped">
		<thead>
			<tr>
				<th>ID Billete</th>
				<th>Coste</th>
				<th>Asiento</th>
				<th>Equipaje facturado</th>
			</tr>
		</thead>
		<tbody>
		<c:choose>
			<c:when test="${billetes.size() > 0 }">
				<c:forEach items="${billetes}" var="billete">
					<tr>
						<td><c:out value="${billete.id}" /></td>
						<td><c:out value="${billete.coste}" /></td>
						<td><c:out value="${billete.asiento.nombre}" /></td>
						<td>
							<c:set var="total" value="${0}"/>
							<c:forEach items="${billete.equipajes}" var="equipaje">
	       						<c:set var="total" value="${total + 1}" />
							</c:forEach>
							<c:out value="${total}"/> piezas
						</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="centrado">
					<td colspan="10"><h3>¡Lo sentimos! No se encuentra ningún billete para el cliente ${cliente.nombre} ${cliente.apellidos}<c:out value=""/>.</h3></td>
				</tr>
			</c:otherwise>
			</c:choose>
		</tbody>
	</table>

	<div class="panel-footer centrado">
		<h3>Mostrando página ${number+1} de ${totalPages}</h3>
		<ul class="pagination" style="margin: 0px;">
			<c:forEach begin="0" end="${totalPages-1}" var="page">
				<li class="page-item"><a
					href="compras?page=${page}&size=${size}" class="page-link">${page+1}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</aerolineasAAAFC:layout>