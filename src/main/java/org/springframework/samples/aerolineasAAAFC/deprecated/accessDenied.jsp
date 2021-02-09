<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout>

	<div class="centrado">
		<img class="imagen" alt="AAAFC logo" src="/resources/images/logoT.png"
			style="width: 50%;">
	</div>

	<h2>¡Lo sentimos! Usted no tiene permisos para acceder a esta página.</h2>

	Pulse <a href="<c:url value="/" /> ">aquí</a> para volver a la página de inicio
	
</aerolineasAAAFC:layout>