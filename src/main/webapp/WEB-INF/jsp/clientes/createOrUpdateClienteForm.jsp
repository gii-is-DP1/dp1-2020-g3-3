<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="clientes">
	<h2>
		<c:if test="${cliente['new']}">Nuevo </c:if>Cliente
	</h2>
	<form:form modelAttribute="cliente" class="form-horizontal"
		id="add-cliente-form">
		<div class="form-group has-feedback">
		
			<aerolineasAAAFC:inputField label="Nombre" name="nombre" />
			<aerolineasAAAFC:inputField label="Apellidos" name="apellidos" />
			<aerolineasAAAFC:inputField label="NIF" name="nif" />
			<aerolineasAAAFC:inputField label="Direccion de Facturación" name="direccionFacturacion" />
			<aerolineasAAAFC:inputField label="IBAN" name="iban" />
			<aerolineasAAAFC:inputField label="Fecha de Nacimiento" name="fechaNacimiento" type="date"/>
			<aerolineasAAAFC:inputField label="Email" name="email" />
			<aerolineasAAAFC:inputField label="Usuario (debe ser igual al DNI)" name="user.username" />
			<aerolineasAAAFC:inputField label="Contraseña" name="user.password" type="password" />
			<aerolineasAAAFC:inputField label="Confirmar Contraseña" name="user.matchingPassword" type="password"></aerolineasAAAFC:inputField>
			
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
