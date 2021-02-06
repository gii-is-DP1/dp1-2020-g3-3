<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<!-- Pagina en la que se mostraran los azafatos y se permitira acceder a modificarlos -->

<aerolineasAAAFC:layout pageName="azafatos">
	<h2 class="centrado">Azafatos</h2>
	<div style="float:right; padding-bottom: 1%;" >
	<form:form action="/azafatosfind" method="get">
           <label>NIF </label>
           <input name="nif" pattern="^\d{8}[a-zA-Z]$" type="text"/>
           <button type="submit" class="btn btn-default">Encontrar Azafato</button>

    </form:form>
    </div>
	<table id="tablaAzafatos" class="table table-striped centrado">
		<thead>
			<tr>
				<th class="centrado">Nombre y Apellidos</th>
				<th class="centrado">NIF</th>
				<th class="centrado">IBAN</th>
				<th class="centrado">Idiomas</th>
				<th class="centrado">Salario</th>
				<th class="centrado">Usuario</th>
				<th class="centrado" width="15%">Opciones</th>

			</tr>
		</thead>

		<tbody>
		<c:choose>
			<c:when test="${azafatos.size() > 0 }">
				<c:forEach items="${azafatos}" var="azafato">
					<tr>
						<td onclick="javascript:location.href='/azafatos/${azafato.id}'" onmouseover="" style="cursor: pointer;">
							<b><c:out value="${azafato.nombre} ${azafato.apellidos}"/></b>
						</td>
						<td><c:out value="${azafato.nif}" /></td>
						<td><c:out value="${azafato.iban}" /></td>
						<td><c:forEach items="${azafato.idiomas}" var="idioma" varStatus="loop">
								<c:out value="${idioma.idioma}"/>
								<c:if test="${!loop.last}">, </c:if>
							</c:forEach>
						</td>
						<td><c:out value="${azafato.salario}" /></td>
						<td><c:out value="${azafato.user.username}"/></td>
						<td>
							<spring:url value="/azafatos/{azafatoId}/edit" var="azafatoUrl">
	        			<spring:param name="azafatoId" value="${azafato.id}"/>
	    				</spring:url>
	    				<a href="${fn:escapeXml(azafatoUrl)}" class="btn btn-default">Editar</a>
	    				<spring:url value="/azafatos/${azafato.id}/delete" var="azafatoUrl"></spring:url>
	    				<a href="${fn:escapeXml(azafatoUrl)}" class="btn btn-default">Eliminar</a>
						</td>
					</tr>
					
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="centrado">
					<c:if test="${not empty msg}"><td colspan="10"><h3><c:out value="${msg} (${param.nif})"/></h3></td></c:if>
					<c:if test="${empty msg}"><td colspan="10"><h3>¡Lo sentimos! No se encuentra ningún azafato.</h3></td></c:if>
				</tr>
			</c:otherwise>
		</c:choose>
		<tr>
			<td></td><td></td><td></td><td></td><td></td><td></td>
			<td><a href="<spring:url value="/azafatos/new" htmlEscape="true"/>" class="btn btn-default">Nuevo azafato</a></td>
		</tr>
		</tbody>

	</table>

	<div class="panel-footer centrado">
		<h3>Mostrando página ${number+1} de ${totalPages}</h3>
		<ul class="pagination" style="margin: 0px;">
			<c:forEach begin="0" end="${totalPages-1}" var="page">
				<li class="page-item"><a
					href="azafatos?page=${page}&size=${size}" class="page-link">${page+1}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</aerolineasAAAFC:layout>