<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="billetes">

	<h2 class="centrado">Under Construction...</h2>
	<table id="tablaPlatos" class="table table-striped">
		<thead>
			<tr>
				<th>Trayecto de billete</th>
				<th>Fecha de salida</th>
				<th>Fecha de reserva</th>
				<th>Asiento</th>
			</tr>
		</thead>

		<tbody>

			<tr>

				<td><c:out value="1" /></td>
				<td><c:out value="1" /></td>
				<td><c:out value="1" /></td>
				<td><c:out value="1" /></td>

			</tr>

		</tbody>
	</table>

</aerolineasAAAFC:layout>