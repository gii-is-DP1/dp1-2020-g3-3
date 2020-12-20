<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, clientes, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<spring:url value="/resources/images/logo.png" var="logo" />
			<a class="logo" href="<spring:url value="/" htmlEscape="true" />"><span>
					<img alt="AAAFC logo" src="${logo}" style="height: 50px;">
			</span></a>
		</div>
	</div>

	<div class="main_header">
		<spring:url value="/resources/images/logo.png" var="logo" />
		<a class="logo" href="<spring:url value="/" htmlEscape="true" />"><span>
				<img class="imagen" alt="AAAFC logo" src="${logo}" width="55"
				height="16.5">
		</span></a>
		<div class="header-right">
			<a href="#">Cupcake</a> <a href="#">Magdalena</a> <a href="#">Navidad</a>
		</div>

	</div>

</nav>
