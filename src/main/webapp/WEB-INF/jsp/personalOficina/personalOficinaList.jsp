<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<!-- Archivo .jsp que muestra el listado de todos los oficinistas -->

<aerolineasAAAFC:layout pageName="personalOficina">
    <h2 class="centrado">Personal de oficina</h2>
	<div style="float:right; padding-bottom: 1%;" >
	<form:form action="/personalOficinafind" method="get">
           <label>NIF </label>
           <input name="nif" pattern="^\d{8}[a-zA-Z]$" type="text"/>
           <button type="submit" class="btn btn-default">Encontrar Oficinista</button>

    </form:form>
    </div>
    <table id="tablaPersonalOficina" class="table table-striped centrado">
        <thead>
        <tr>
        	<th class="centrado">Nombre y apellidos</th>
            <th class="centrado">NIF</th>
            <th class="centrado">IBAN</th>
            <th class="centrado">Salario</th>
            <th class="centrado" width="15%">Opciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${personalOficina}" var="oficinista">
            <tr>
                <td onclick="javascript:location.href='/personalOficina/${oficinista.id}'" onmouseover="" style="cursor: pointer;">
                	<b><c:out value="${oficinista.nombre} ${oficinista.apellidos}"/></b>
                </td>
                <td>
                    <c:out value="${oficinista.nif}"/>
                </td>
                <td>
                    <c:out value="${oficinista.iban}"/>
                </td>
                <td>
                    <c:out value="${oficinista.salario}"/>
                </td>
                <td>
                	<spring:url value="/personalOficina/{oficinistaId}/edit" var="url">
        				<spring:param name="oficinistaId" value="${oficinista.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(url)}" class="btn btn-default">Editar</a>
    				
    				<spring:url value="/personalOficina/${oficinista.id}/delete" var="personalOficinaUrl"></spring:url>
    				<a href="${fn:escapeXml(personalOficinaUrl)}" class="btn btn-default">Eliminar</a>
                </td>   
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="<spring:url value="/personalOficina/new" htmlEscape="true"/>" class="btn btn-default">Nuevo oficinista</a>

	<div class="panel-footer centrado">
		<h3>Mostrando página ${number+1} de ${totalPages}</h3>
		<ul class="pagination" style="margin: 0px;">
			<c:forEach begin="0" end="${totalPages-1}" var="page">
				<li class="page-item"><a
					href="personalOficina?page=${page}&size=${size}" class="page-link">${page+1}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</aerolineasAAAFC:layout>