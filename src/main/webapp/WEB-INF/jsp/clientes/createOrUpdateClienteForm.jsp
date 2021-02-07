<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<aerolineasAAAFC:layout pageName="clientes">
	<h2>
		<c:if test="${cliente['new']}"> Nuevo</c:if> Cliente
	</h2>
	<form:form modelAttribute="cliente" class="form-horizontal"
		id="add-cliente-form">
		<input type="hidden" name= "clienteId" value= "${cliente.id}"/>
    	<input type="hidden" name= "version" value= "${cliente.version}"/>
    	<c:if test="${not cliente['new']}"><input type="hidden" name= "nif" value= "${cliente.nif}"/></c:if>
    	<c:if test="${not cliente['new']}"><input type="hidden" name= "fechaNacimiento" value= "${cliente.fechaNacimiento}"/></c:if>
    	<c:if test="${not cliente['new']}"><input type="hidden" name= "user.username" value= "${cliente.user.username}"/></c:if>
    	<c:if test="${not cliente['new']}"><input type="hidden" name= "user.password" value= "${cliente.user.password}"/></c:if>	
		<div class="form-group has-feedback">
		
			<aerolineasAAAFC:inputField label="Nombre" name="nombre" />
			<aerolineasAAAFC:inputField label="Apellidos" name="apellidos" />
			<c:if test="${cliente['new']}"><aerolineasAAAFC:inputField label="NIF" name="nif" /></c:if>
			<aerolineasAAAFC:inputField label="Direccion de Facturación" name="direccionFacturacion" />
			<aerolineasAAAFC:inputField label="IBAN" name="iban" pattern="^ES\\s\\d{22}$"/>
			<c:if test="${cliente['new']}"><aerolineasAAAFC:inputField label="Fecha de Nacimiento" name="fechaNacimiento" type="date"/></c:if>
			<aerolineasAAAFC:inputField label="Email" name="email" />
			<c:if test="${cliente['new']}"><aerolineasAAAFC:inputField label="Usuario (debe ser igual al DNI)" name="user.username"/></c:if>
			<sec:authorize 	access="hasAnyAuthority('admin','personalOficina')">
				<c:if test="${not cliente['new']}"><aerolineasAAAFC:inputField label="Contraseña" name="user.password" type="password" id="contraseña"/></c:if>	
			</sec:authorize>
			<c:if test="${cliente['new']}"><aerolineasAAAFC:inputField label="Contraseña" name="user.password" type="password" id="contraseña"/></c:if>
			
			
			<form:errors> </form:errors>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${cliente['new']}">
						<button class="btn btn-default" type="submit">Añadir Cliente</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Actualizar Cliente</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</aerolineasAAAFC:layout>
