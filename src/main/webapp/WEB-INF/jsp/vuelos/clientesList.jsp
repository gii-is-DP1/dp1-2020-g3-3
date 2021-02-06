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
		  	<c:choose>
				<c:when test="${clientes.size() > 0 }">
					<c:set var="count" value="1" scope="page" />
					<c:forEach items="${clientes}" var="cliente">
						<tr>
							
							<td><c:out value="${count}"/></td>
							<c:set var="count" value="${count + 1}" scope="page"/>
							<td onclick="javascript:location.href='/clientes/${cliente.id}'" onmouseover="" style="cursor: pointer;"><b><c:out value="${cliente.nombre} ${cliente.apellidos}"/></b></td>
						
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="centrado">
						<c:if test="${empty msg}"><td colspan="10"><h3>¡Lo sentimos! No se encuentra ningún cliente.</h3></td></c:if>
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
					href="clientes?page=${page}&size=${size}" class="page-link">${page+1}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</aerolineasAAAFC:layout>