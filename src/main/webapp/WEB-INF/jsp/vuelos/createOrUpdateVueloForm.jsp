<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>
<%@ page
	import="org.springframework.samples.aerolineasAAAFC.repository.AeropuertoRepository"%>
<%@ page
	import="org.springframework.samples.aerolineasAAAFC.model.Aeropuerto"%>
<%@ page import="org.springframework.stereotype.Service"%>
<%@ page import="java.util.List"%>

<aerolineasAAAFC:layout pageName="vuelos">

	<h2>
		<c:if test="${vuelo['new']}">Nuevo </c:if>
		Vuelo
	</h2>
	<form:form modelAttribute="vuelo" class="form-horizontal"
		id="add-vuelo-form">
		<div class="form-group has-feedback">
			<form action="some.jsp">
				<select name="item">

					<c:forEach items="${aeropuertos.aeropuertosList}" var="aeropuerto">
						<option value="${aeropuerto.nombre}">${aeropuerto.nombre}</option>
						<option value="2">2</option>
						<option value="3">3</option>
					</c:forEach>

				</select>
			</form>
			<aerolineasAAAFC:inputField label="Aeropuerto de salida"
				name="aeropuertoOrigen" />
			<aerolineasAAAFC:inputField label="Aeropuerto de llegada"
				name="aeropuertoDestino" />
			<aerolineasAAAFC:inputField label="Fecha de salida"
				name="fechaSalida" />
			<aerolineasAAAFC:inputField label="Fecha de llegada"
				name="fechaLlegada" />
			<aerolineasAAAFC:inputField label="Coste" name="coste" />
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${vuelo['new']}">
						<button class="btn btn-default" type="submit">Añadir
							vuelo</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Actualizar
							vuelo</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</aerolineasAAAFC:layout>