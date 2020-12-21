<%@page import="org.springframework.samples.aerolineasAAAFC.repository.PersonalOficinaRepository"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>
<%@ page
	import="org.springframework.samples.aerolineasAAAFC.service.AeropuertoService"%>
<%@ page
	import="org.springframework.samples.aerolineasAAAFC.repository.PersonalOficinaRepository"%>	
<%@ page
	import="org.springframework.samples.aerolineasAAAFC.model.Aeropuerto"%>
<%@ page
	import="org.springframework.samples.aerolineasAAAFC.model.PersonalOficina"%>
<%@ page
	import="java.util.List"%>
<%@ page
	import="java.util.Arrays"%>
<%@ page import="java.util.Iterator"%>

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

			<label>Aeropuerto de Salida</label> <br /> 
			<select
				name="aeropuertoOrigen.id">
				<c:forEach items="${aeropuertos}" var="aeropuerto">
					<option value="${aeropuerto.id}">${aeropuerto.nombre}</option>
				</c:forEach>
			</select> <br /> <label>Aeropuerto de Llegada</label> <br /> <select
				name="aeropuertoDestino.id">
				<c:forEach items="${aeropuertos}" var="aeropuerto">
					<option value="${aeropuerto.id}">${aeropuerto.nombre}</option>
				</c:forEach>
			</select> <br /> <label>Avion asignado</label> <br /> <select name="avion.id">
				<c:forEach items="${aviones}" var="avion">
					<option value="${avion.id}">${avion.tipoAvion}</option>
				</c:forEach>
			</select> 
			<br/> 
			
			
			<label>Personal asignado</label> 
			<br/> 
			<select	name="pOficina.id" size="3" multiple="multiple">
				<c:forEach items="${todoPersonal}" var="persona">
					<c:set var="is" value="false"/>	
					
					<c:forEach items="${pOficina}" var="actual">
						<c:if test = "${persona.id eq actual.id}">
							<c:set var="is" value="true"/>	
						</c:if>			
					</c:forEach>
					<option value="${persona.id}" >${persona.nombre}<c:if test="${is eq true}">selected</c:if></option>	
				</c:forEach>
			</select>
			
			<%-- 
			<select	name="pOficina.id" multiple="multiple">
			<c:forEach items="${todoPersonal}" var="persona">
					<option value="${persona.id}">${persona.nombre}</option>
			</c:forEach>
			</select>--%>

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