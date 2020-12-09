<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="vuelos">
	<h2>
		<c:if test="${vuelo['new']}">Nuevo </c:if>
		Vuelo
	</h2>
	<form:form modelAttribute="vuelo" class="form-horizontal"
		id="add-vuelo-form">
		<div class="form-group has-feedback">
			<aerolineasAAAFC:inputField label="Fecha de Salida"
				name="fechaSalida" type="datetime-local" />
			<aerolineasAAAFC:inputField label="Fecha de Llegada"
				name="fechaLlegada" type="datetime-local" />
			<aerolineasAAAFC:inputField label="Aeropuerto Salida"
				name="aeropuertoOrigen" type="number" />
			<aerolineasAAAFC:inputField label="Aeropuerto Llegada"
				name="aeropuertoDestino" type="number" />	
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