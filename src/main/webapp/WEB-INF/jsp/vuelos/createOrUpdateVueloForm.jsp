<%@page import="org.springframework.samples.aerolineasAAAFC.repository.PersonalOficinaRepository"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="vuelos">
	
	<h2> <c:if test="${vuelo['new']}">Nuevo </c:if> Vuelo 
	<c:if test="${not vuelo['new']}">
	<c:out value="${vuelo.aeropuertoOrigen.codigoIATA}"/> - <c:out value="${vuelo.aeropuertoDestino.codigoIATA}"/>. Con coste base de <c:out value="${vuelo.coste}"/> y <c:out value="${vuelo.horasVuelo}"/> horas de Vuelo.
	</c:if>
	</h2>
	
	<form:form modelAttribute="vuelo" class="form-horizontal" id="add-vuelo-form">
		<input type="hidden" name= "id" value= "${vuelo.id}"/>
		<c:if test="${vuelo['new']}"><input type="hidden" name="horasVuelo" value="0"/></c:if>
		<c:if test="${not vuelo['new']}"><input type="hidden" name="horasVuelo" value="${vuelo.horasVuelo}"/></c:if>
		<c:if test="${not vuelo['new']}"><input type="hidden" name= "coste" value= "${vuelo.coste}"/></c:if>
		<c:if test="${not vuelo['new']}"><input type="hidden" name= "aeropuertoOrigen" value= "${vuelo.aeropuertoOrigen}"/></c:if>
		<c:if test="${not vuelo['new']}"><input type="hidden" name= "aeropuertoDestino" value= "${vuelo.aeropuertoDestino}"/></c:if>
		
		<div class="form-group has-feedback">
			<aerolineasAAAFC:inputField label="Fecha de Salida" name="fechaSalida" type="datetime-local" placeholder="yyyy-MM-ddTHH:mm" pattern="^\d\d\d\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])T(00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9])$" />

			<aerolineasAAAFC:inputField label="Fecha de Llegada" name="fechaLlegada" type="datetime-local" placeholder="yyyy-MM-ddTHH:mm" pattern="^\d\d\d\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])T(00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9])$"/>
			<c:if test="${vuelo['new']}">	
			<aerolineasAAAFC:selectFieldN label="Aeropuerto de Origen" name="aeropuertoOrigen" names="${aeropuertos}"/>
			<aerolineasAAAFC:selectFieldN label="Aeropuerto de Destino" name="aeropuertoDestino" names="${aeropuertos}"/>
			</c:if>		
			<aerolineasAAAFC:selectFieldN label="Avion (capacidad de pasajeros)" name="avion" names="${aviones}"/>
			 
			<h2 class="centrado">Personal asignado</h2> 
			<br/>
			<aerolineasAAAFC:selectField label="Personal de Oficina" name="personalOficina" names="${personalOficina}"/>
			<aerolineasAAAFC:selectField label="Personal Control (minimo 2 para menos de 8 horas de vuelo, 3 para más)" name="personalControl" names="${personalControl}"/>
			<aerolineasAAAFC:selectField label="Azafatos (1 cada 50 asientos)" name="azafatos" names="${azafatos}"/>
			<br/> 
			<c:if test="${vuelo['new']}"><aerolineasAAAFC:inputField label="Coste" name="coste" /></c:if>
			
			<form:errors></form:errors>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${vuelo['new']}">
						<button class="btn btn-default" type="submit">Añadir vuelo</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Actualizar vuelo</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</aerolineasAAAFC:layout>