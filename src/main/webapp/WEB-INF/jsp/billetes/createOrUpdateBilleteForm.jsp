<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="billetes">
	<h2 class="centrado">Nuevo Billete</h2>

	<form:form modelAttribute="billete" class="form-horizontal"
		id="add-billete-form">
		<div class="form-group has-feedback">


		<c:choose>
			<c:when test="${nAsientos >0}">
			<c:out value="Precio de plaza económica: ${vuelo.coste} "></c:out>
			<br>
			<c:out value="Precio de plaza ejecutiva: ${vuelo.coste*1.25} "></c:out>
			<br>
			<c:out value="Precio de plaza en primera clase: ${vuelo.coste*1.75}"></c:out>
			<br>
			<select name="asiento">
				<c:forEach items="${asientos}" var="asiento">
					<option value="${vuelo.id},${asiento.nombre}">${asiento.nombre}${'  '}${asiento.clase}</option>
				</c:forEach>
			</select> 
			</c:when>
			<c:otherwise>
				<c:out value="No quedan plazas disponibles"></c:out>
			</c:otherwise>
		</c:choose>
			<input type="hidden" name="coste" value="1.0" /> 
			<input type="hidden" name="cliente"
			value="${cliente.id}" /> 
			<input type="hidden" name="fechaReserva"
			value="${fechaReserva}" />
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Añadir
					billete</button>
			</div>
		</div>
	</form:form>
</aerolineasAAAFC:layout>