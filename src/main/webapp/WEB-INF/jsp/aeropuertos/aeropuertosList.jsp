<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- Pagina en la que se mostraran los aeropuertos y se permitira acceder a modificarlos -->

<aerolineasAAAFC:layout pageName="aeropuertos">
	<h2 class="centrado">Aeropuertos</h2>

	<table id="tablaAeropuertos" class="table table-striped centrado">
		<thead>
			<tr>
				
				<th width="24%" class="centrado">Nombre</th>
				<th width="15%" class="centrado">Localización</th>
				<th width="10%" class="centrado">códigoIATA</th>
				<th width="10%" class="centrado">Teléfono</th>
				<th width="10%" class="centrado">Vuelos Salida</th>
				<th width="10%" class="centrado">Vuelos Llegada</th>
				<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')"> <th width="15%" class="centrado">Opciones</th> </sec:authorize>
			</tr>
		</thead>

		<tbody>
			<c:choose>
				<c:when test="${aeropuertos.size() > 0 }">
					<c:forEach items="${aeropuertos}" var="aeropuerto">
						<tr>
							<td
								onclick="javascript:location.href='/aeropuertos/${aeropuerto.id}'"
								onmouseover="" style="cursor: pointer;"><b><c:out
										value="${aeropuerto.nombre}" /></b></td>
							<td><c:out value="${aeropuerto.localizacion}" /></td>
							<td><c:out value="${aeropuerto.codigoIATA}" /></td>
							<td><c:out value="${aeropuerto.telefono}" /></td>
							<td><c:forEach items="${aeropuerto.vuelosSalida}"
									var="vuelo" varStatus="loop">
									<c:out value="${vuelo.id}" />
									<c:if test="${!loop.last}">, </c:if>
								</c:forEach></td>
							<td><c:forEach items="${aeropuerto.vuelosLlegada}"
									var="vuelo" varStatus="loop">
									<c:out value="${vuelo.id}" />
									<c:if test="${!loop.last}">, </c:if>
								</c:forEach></td>
								
							<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')">
								<td>
								
									<spring:url value="/aeropuertos/${aeropuerto.id}/edit" var="aeropuertoUrl"></spring:url> 
									<a href="${fn:escapeXml(aeropuertoUrl)}"class="btn btn-default">Editar</a> 
	
									<spring:url value="/aeropuertos/${aeropuerto.id}/delete" var="aeropuertoUrl"></spring:url> 
									<a href="${fn:escapeXml(aeropuertoUrl)}" class="btn btn-default">Eliminar</a>
								
								</td>
							</sec:authorize>

						</tr>

					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="centrado">
						<td colspan="10"><h3>¡Lo sentimos! No se encuentra ningún aeropuerto.</h3></td>
					</tr>
				</c:otherwise>
			</c:choose>
			<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')">
				<tr>
					<td></td><td></td><td></td><td></td><td></td><td></td>
					
					<td>
						
							<a href="<spring:url value="/aeropuertos/new" htmlEscape="true"/>" class="btn btn-default">Nuevo aeropuerto</a>
						
					</td>
				</tr>
			</sec:authorize>
		</tbody>
		
		
	</table>
	
	
		 <div class="panel-footer centrado">
	 <h3>Mostrando página ${number+1} de ${totalPages}</h3>
            <ul class="pagination" style="margin: 0px;">
                <c:forEach begin="0" end="${totalPages-1}" var="page">
                    <li class="page-item">
                        <a href="aeropuertos?page=${page}&size=${size}" class="page-link">${page+1}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
				
</aerolineasAAAFC:layout>