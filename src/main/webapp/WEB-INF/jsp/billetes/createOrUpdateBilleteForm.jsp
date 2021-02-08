<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="billetes">
	<h2 class="centrado">
		Compra de Billete
		<c:out
			value="${vuelo.aeropuertoOrigen.codigoIATA} - ${vuelo.aeropuertoDestino.codigoIATA}"></c:out>
	</h2>

	<form:form modelAttribute="billete" class="form-horizontal centrado"
		id="add-billete-form">
		<div class="form-group has-feedback">


			<c:choose>
				<c:when test="${nAsientos >0}">
					<c:choose>
						<c:when test="${descuento}">
							<h3 class="centrado">¡Rápido, este billete está al 25% de
								descuento!</h3>
							<p class="centrado">
								Precio de plaza económica:<span
									style="text-decoration: line-through;color:red;"> <c:out
										value="${vuelo.coste} EUR"></c:out></span>
								<c:out value="${vuelo.coste * 0.75}"></c:out>
								EUR
							</p>
							<p class="centrado">
								Precio de plaza en primera clase:<span
									style="text-decoration: line-through;color:red;"> <c:out
										value="${vuelo.coste * 1.25} EUR"></c:out></span>
								<c:out value="${vuelo.coste * 0.75 *1.25}"></c:out>
								EUR
							</p>
							<p class="centrado">
								Precio de plaza económica: <span
									style="text-decoration: line-through;color:red;"> <c:out
										value="${vuelo.coste * 1.75} EUR"></c:out></span>
								<c:out value="${vuelo.coste * 0.75 *1.75}"></c:out>
								EUR
							</p>
						</c:when>
						<c:otherwise>
							<c:out value="Precio de plaza económica: ${vuelo.coste} EUR"></c:out>
							<br>
							<c:out value="Precio de plaza ejecutiva: ${vuelo.coste*1.25} EUR"></c:out>
							<br>
							<c:out
								value="Precio de plaza en primera clase: ${vuelo.coste*1.75} EUR"></c:out>
							<br>
							<br>
						</c:otherwise>
					</c:choose>


					<select name="asiento">
						<c:forEach items="${asientos}" var="asiento">
							<option value="${vuelo.id},${asiento.nombre}">${asiento.nombre}${'  '}${asiento.clase}</option>
						</c:forEach>
					</select>
				</c:when>
				<c:otherwise>
					<c:out value="¡Lo sentimos! No quedan plazas disponibles"></c:out>
				</c:otherwise>
			</c:choose>
			<input type="hidden" name="coste" value="1.0" /> <input
				type="hidden" name="cliente" value="${cliente.id}" /> <input
				type="hidden" name="fechaReserva" value="${fechaReserva}" />
		</div>
		<br>
		<c:choose>
			<c:when test="${nAsientos >0}">
				<div class="form-group" style="margin-left: -21%;">
					<div class="col-sm-offset-2 col-sm-10">
						<button class="btn btn-default" type="submit">Comprar
							billete</button>
					</div>
				</div>
			</c:when>
		</c:choose>
	</form:form>
</aerolineasAAAFC:layout>