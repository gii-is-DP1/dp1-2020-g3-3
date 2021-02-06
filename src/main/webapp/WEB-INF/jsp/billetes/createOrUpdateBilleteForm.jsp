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

			<select name="asiento">
				<c:forEach items="${asientos}" var="asiento">
					<option value="${asiento.id}">${asiento.nombre}</option>
				</c:forEach>
			</select> <input type="hidden" name="coste" value="0.0" /> <input
				type="hidden" name="equipajes" value="" /> <input type="hidden"
				name="menus" value="" /> <input type="hidden" name="cliente"
				value="${cliente}" /> <input type="hidden" name="fechaReserva"
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