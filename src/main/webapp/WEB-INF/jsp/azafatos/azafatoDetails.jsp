<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="azafatos">

    <h2>Información del Personal de Control</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre y apellidos</th>
            <td><b><c:out value="${azafato.nombre} ${azafato.apellidos}"/></b></td>
        </tr>
        <tr>
            <th>NIF</th>
            <td><c:out value="${azafato.nif}"/></td>
        </tr>
        <tr>
            <th>IBAN</th>
            <td><c:out value="${azafato.iban}"/></td>
        </tr>
        <tr>
            <th>Salario</th>
            <td><c:out value="${azafato.salario}"/></td>
        </tr>
        <tr>
        	<th>Idiomas</th>
        	<td><c:forEach items="${azafato.idiomas}" var="idioma" varStatus="loop">
					<c:out value="${idioma.idioma}"/>
					<c:if test="${!loop.last}">, </c:if>
				</c:forEach>
			</td>
        </tr>
    </table>

  	<spring:url value="/azafatos/{azafatoId}/edit" var="editUrl">
        <spring:param name="azafatoId" value="${azafato.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Azafato</a>


    <br/>
    <br/>
  


</aerolineasAAAFC:layout>
