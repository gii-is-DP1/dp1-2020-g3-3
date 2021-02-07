<%@page import="org.springframework.samples.aerolineasAAAFC.repository.PersonalOficinaRepository"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="vuelos">
	
	<h2> <c:if test="${vuelo['new']}">Nuevo </c:if> Vuelo </h2>
	
	<form:form modelAttribute="vuelo" class="form-horizontal" id="add-vuelo-form">
		<input type="hidden" name= "id" value= "${vuelo.id}"/>
		
		<div class="form-group has-feedback">
			<aerolineasAAAFC:inputField label="Fecha de Salida" name="fechaSalida" type="datetime-local" placeholder="yyyy-MM-ddTHH:mm" pattern="^\d\d\d\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])T(00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9])$" />

			<aerolineasAAAFC:inputField label="Fecha de Llegada" name="fechaLlegada" type="datetime-local" placeholder="yyyy-MM-ddTHH:mm" pattern="^\d\d\d\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])T(00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9])$"/>
			<div class="form-group">
				<label class="col-sm-2 control-label">Aeropuerto de Salida</label>
				<div class="col-sm-10">
					<select name="aeropuertoOrigen" id="aeropuertoOrigen">
						<c:forEach items="${aeropuertos}" var="codigo">
							<option value="${codigo.id}"><c:out value="${codigo.codigoIATA}"/></option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">Aeropuerto de Llegada</label>
				<div class="col-sm-10">
					<select name="aeropuertoDestino" id="aeropuertoDestino">
						<c:forEach items="${aeropuertos}" var="codigo">
							<option value="${codigo.id}"><c:out value="${codigo.codigoIATA}"/></option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">Avión (Capacidad de pasajeros)</label>
				<div class="col-sm-10">
					<select name="avion" id="avion">
						<c:forEach items="${aviones}" var="avion">
							<option value="${avion.id}"><c:out value="${avion.id} - ${avion.tipoAvion} (${avion.capacidadPasajero})"/></option>
						</c:forEach>
					</select>
				</div>
			</div>
			<br/> 
			
			
			<h2 class="centrado">Personal asignado</h2> 
			<br/>
			<div class="form-group">
				<label class="col-sm-2 control-label">Personal Oficina</label>
				<div class="col-sm-10">
					<select name="personalOficina" id="personalOficina" multiple>
						<c:forEach items="${personalOficina}" var="personalOficina">
							<option value="${personalOficina.id}"><c:out
									value="${personalOficina.nombre} ${personalOficina.apellidos}" /></option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">Personal Control</label>
				<div class="col-sm-10">
					<select name="personalControl" id="personalControl" multiple>
						<c:forEach items="${personalControl}" var="personalControl">
							<option value="${personalControl.id}"><c:out
									value="${personalControl.nombre} ${personalControl.apellidos} - ${personalControl.rol}" /></option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">Azafatos (1 cada 50 asientos)</label>
				<div class="col-sm-10">
					<select name="azafatos" id="azafatos" multiple>
						<c:forEach items="${azafatos}" var="azafatos">
							<option value="${azafatos.id}"><c:out
									value="${azafatos.nombre} ${azafatos.apellidos}" /></option>
						</c:forEach>
					</select>
				</div>
			</div>

			<br/> 
			<aerolineasAAAFC:inputField label="Coste" name="coste" />
			
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