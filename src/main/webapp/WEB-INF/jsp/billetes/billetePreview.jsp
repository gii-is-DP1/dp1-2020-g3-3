<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="billetes">

	<table id="tablaBillete" class="table table-striped">
		<thead>
			<tr>
				<th class="centrado">Trayecto de billete</th>
				<th class="centrado">Fecha de salida</th>
				<th class="centrado">Fecha de llegada</th>
				<th class="centrado">Fecha de reserva</th>
				<th class="centrado">Asiento</th>
				<th class="centrado">Coste total</th>
			</tr>
		</thead>

		<tbody class="centrado">

			<tr>
				<td><c:out
						value="${billete.getAsiento().getVuelo().getAeropuertoOrigen().getCodigoIATA()} - 
						${billete.getAsiento().getVuelo().getAeropuertoDestino().getCodigoIATA()}" /></td>
				<td><c:out value="${fechaSalida}" /></td>
				<td><c:out value="${fechaLlegada}" /></td>
				<td><c:out value="${fechaReserva}" /></td>
				<td><c:out
						value="${billete.getAsiento().getNombre()} - ${billete.getAsiento().getClase().toString()}" /></td>
				<td><fmt:formatNumber value="${billete.getCoste()}" maxFractionDigits="2"/></td>
			</tr>

		</tbody>
	</table>

	<c:choose>
		<c:when test="${numEquipajes > 0}">
			<table id="tablaEquipajes" class="table table-striped"
				style="margin-top: 4.5%;">
				<thead>
					<tr>
						<th class="centrado">Tipo</th>
						<th class="centrado">Precio</th>
						<th class="centrado">Medidas</th>
						<th class="centrado">Peso</th>
					</tr>
				</thead>

				<tbody class="centrado">
					<c:forEach items="${equipajes}" var="equipaje" varStatus="loop">
						<tr>
							<td><c:out value="${equipaje.getEquipajeBase().getName()}" /></td>
							<td><c:out value="${equipaje.getEquipajeBase().getPrecio()}" /></td>
							<td><c:out
									value="${equipaje.getEquipajeBase().getDimensiones()}" /></td>
							<td><c:out value="${equipaje.getPeso()}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<h2 class="centrado" style="margin-bottom: 2%;">Vaya...parece
				que no has añadido ningún equipaje todavía.</h2>
		</c:otherwise>
	</c:choose>


	<c:choose>
		<c:when test="${numEquipajes < 3}">
			<div class="centrado">
				<a class="btn btn-default"
					href="/billetes/${billete.getId()}/equipajes/new">Añadir
					equipaje</a>
			</div>
		</c:when>
	</c:choose>


	<c:choose>
		<c:when test="${numMenus > 0}">
			<h2 class="centrado" style="margin-top: 4%;">Si su vuelo es
				superior a 5 horas o su plaza no es del tipo económica, sus menús
				serán gratis.</h2>
			<table id="tablaMenus" class="table table-striped">
				<thead>
					<tr>
						<th class="centrado">Primer plato</th>
						<th class="centrado">Segundo plato</th>
						<th class="centrado">Postre</th>
					</tr>
				</thead>
				<tbody class="centrado">
					<c:forEach items="${menus}" var="menu" varStatus="loop">
						<tr>
							<td><c:out value="${menu.getPlato1().toString()}" /></td>
							<td><c:out value="${menu.getPlato2().toString()}" /></td>
							<td><c:out value="${menu.getPlato3().toString()}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<h2 class="centrado" style="margin-bottom: 2%; margin-top: 4%;">Vaya...parece
				que no has añadido ningún menú todavía.</h2>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${numMenus < 3}">
			<div class="centrado">
				<a class="btn btn-default"
					href="/billetes/${billete.getId()}/menus/new">Añadir menú</a>
			</div>
		</c:when>
	</c:choose>
</aerolineasAAAFC:layout>