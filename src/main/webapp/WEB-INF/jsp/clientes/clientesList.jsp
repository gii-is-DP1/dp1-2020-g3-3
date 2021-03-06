<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- Pagina en la que se mostraran los clientes y se permitir� eliminarlos -->

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
				<th class="centrado">Direcci�n de Facturaci�n</th>
				<th class="centrado">IBAN</th>
				<th class="centrado">Fecha de Nacimiento</th>
				<th class="centrado">Email</th>
				<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')"><th class="centrado" width="15%">Opciones</th></sec:authorize>


			</tr>
		</thead>

		<tbody class="centrado">
			<c:choose>
				<c:when test="${clientes.size() > 0 }">
					<c:forEach items="${clientes}" var="cliente">
						<tr>
						
							<td onclick="javascript:location.href='/clientes/${cliente.id}'" onmouseover="" style="cursor: pointer;"><b><c:out value="${cliente.nombre} ${cliente.apellidos}"/></b></td>
							<td><c:out value="${cliente.nif}"/></td>
							<td><c:out value="${cliente.direccionFacturacion}"/></td>
							<td><c:out value="${cliente.iban}"/></td>
							<td><aerolineasAAAFC:localDate date="${cliente.fechaNacimiento}" pattern="dd-MM-yyy"/></td>
							<td><c:out value="${cliente.email}"/></td>
							<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')">
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
							</sec:authorize>
						
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="centrado">
					<c:if test="${not empty msg}"><td colspan="10"><h3><c:out value="${msg} (${param.nif})"/></h3></td></c:if>
					<c:if test="${empty msg}"><td colspan="10"><h3>�Lo sentimos! No se encuentra ning�n cliente.</h3></td></c:if>
					</tr>
				</c:otherwise>
			</c:choose>
			<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')">
				<tr>
					<td></td><td></td><td></td><td></td><td></td><td></td>
					<td><a href="<spring:url value="/clientes/new" htmlEscape="true"/>" class="btn btn-default">Nuevo Cliente</a></td>
				</tr>
			</sec:authorize>
		</tbody>

	</table>
	 
	 <div class="panel-footer centrado">
	 <h3>Mostrando p�gina ${number+1} de ${totalPages}</h3>
            <ul class="pagination" style="margin: 0px;">
                <c:forEach begin="0" end="${totalPages-1}" var="page">
                    <li class="page-item">
                        <a href="clientes?page=${page}&size=${size}" class="page-link">${page+1}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
				
</aerolineasAAAFC:layout>