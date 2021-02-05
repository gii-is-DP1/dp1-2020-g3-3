<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="vuelos">
	<h2 class="centrado">Platos en el vuelo ID${vuelo.getId()} 
	trayecto ${vuelo.getAeropuertoOrigen().getCodigoIATA()}-${vuelo.getAeropuertoDestino().getCodigoIATA()}</h2>

	<table id="tablaPlatos" class="table table-striped">
		<thead>
			<tr>
				<th>Nombre de plato</th>
				<th>Número de unidades</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${numeroPlatosInVuelo.keySet()}" var="plato">
				<tr>
					
					<td><c:out value="${plato}"/></td>
					<td><c:out value="${numeroPlatosInVuelo.get(plato)}"/></td>
				
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<h3 class="centrado">Total de menús: <c:out value="${numMenus}"/> </h3>	
</aerolineasAAAFC:layout>