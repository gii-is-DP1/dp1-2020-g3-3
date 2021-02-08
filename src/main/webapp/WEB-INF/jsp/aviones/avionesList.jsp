<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- Archivo .jsp que muestra el listado de todos los aviones, a su vez se
puede acceder a la ficha de cada avión (por id) para editarlo o borrarlo -->

<aerolineasAAAFC:layout pageName="aviones">
    <h2 class="centrado">Aviones</h2>

    <table id="tablaAviones" class="table table-striped centrado">
        <thead>
        <tr>
            <th class="centrado">ID Avión</th>
            <th class="centrado">Tipo de avión</th>
            <th class="centrado">Horas de vuelo acumuladas</th>
            <th class="centrado">Fecha de fabricación</th>
            <th class="centrado">Última revisión</th>
            <th class="centrado">Disponibilidad</th>
            <sec:authorize 	access="hasAnyAuthority('admin','personalOficina')"> <th width="15%" class="centrado">Opciones</th> </sec:authorize>
        </tr>
        </thead>
        <tbody>
			<c:choose>
				<c:when test="${aviones.size() > 0 }">
					<c:forEach items="${aviones}" var="avion">
						<tr>
							<td onclick="javascript:location.href='/aviones/${avion.id}'"
								onmouseover="" style="cursor: pointer;"><b><c:out
										value="${avion.id}" /></b></td>
							<td><c:out value="${avion.tipoAvion}" /></td>
							<td><c:out value="${avion.horasAcumuladas}" /></td>
							<td><c:out value="${avion.fechaFabricacion}" /></td>
							<td><c:out value="${avion.fechaRevision}" /></td>
							<td>
							<c:choose>
									<c:when test="${avion.disponibilidad==true}">
        					Sí 
        					<br />
									</c:when>
									<c:otherwise>
        					No 
        					<br />
									</c:otherwise>
								</c:choose>
							</td>
							<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')">
								<td><spring:url value="/aviones/{avionId}/edit"
										var="avionUrl">
										<spring:param name="avionId" value="${avion.id}" />
									</spring:url> <a href="${fn:escapeXml(avionUrl)}" class="btn btn-default">Editar</a>
									<spring:url value="/aviones/${avion.id}/delete" var="avionUrl"></spring:url>
									<a href="${fn:escapeXml(avionUrl)}" class="btn btn-default">Eliminar</a>
								</td>
							</sec:authorize>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="centrado">
						<td colspan="10"><h3>¡Lo sentimos! No se encuentra ningún avión.</h3></td>
					</tr>
				</c:otherwise>
			</c:choose>
			<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')">
				<tr>
					<td></td><td></td><td></td><td></td><td></td><td></td>
					<td><a href="<spring:url value="/aviones/new" htmlEscape="true"/>"class="btn btn-default">Nuevo Avión</a></td>
				</tr>
			</sec:authorize>
			<sec:authorize 	access="hasAnyAuthority('admin','personalOficina','personalControl')">
				<tr>
					<td></td><td></td><td></td><td></td><td></td><td></td>
					<td><a href="<spring:url value="/aviones/estadoAviones" htmlEscape="true"/>"class="btn btn-default centrado">Ver estado aviones</a></td>
				</tr>
			</sec:authorize>
		</tbody>
    </table>

	<div class="panel-footer centrado">
		<h3>Mostrando página ${number+1} de ${totalPages}</h3>
		<ul class="pagination" style="margin: 0px;">
			<c:forEach begin="0" end="${totalPages-1}" var="page">
				<li class="page-item"><a
					href="aviones?page=${page}&size=${size}" class="page-link">${page+1}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</aerolineasAAAFC:layout>