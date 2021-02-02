<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, clientes, vets or error"%>

<nav class="navbar navbar-default" role="navigation"
	style="border-top: 4px solid #c0c0c0;">

	<div class="main_container">
		<div class="main_header">

			<div id="mySidenav" class="sidenav">
				<a href="javascript:void(0)" class="closebtn"
					onclick="close3BarMenu()">&times;</a> <a href="#">Cosa11</a> <a
					href="#">Cosa33</a> <a href="#">Cosa22</a> <a href="#">Cosa11</a>
			</div>

			<span class=burger_button onclick="open3BarMenu()">&#9776;</span>

			<spring:url value="/resources/images/logo.png" var="logo" />
			<a class="logo" href="<spring:url value="/" htmlEscape="true" />"><span>
					<img class="imagen" alt="AAAFC logo" src="${logo}" width="55"
					height="16.5">
			</span></a>

			<div class="header-left">
				<a href="#">Cosa1</a> <a href="#">Cosa2</a> <a href="#">Cosa3</a>
			</div>

			<div class="header-right">
				<sec:authorize access="!isAuthenticated()">
					<a href="<c:url value="/login" />">Entra</a>
					<a href="<c:url value="/clientes/new" />">Regístrate</a>
				</sec:authorize>

				<sec:authorize access="isAuthenticated()">
					<a href="urlParaPerfilDeUsuario"> <span
						class="glyphicon glyphicon-user"></span>  <sec:authentication
							property="name" />
					</a>

					<a href="<c:url value="/logout" />"> <span
						class="glyphicon glyphicon-off"></span> Cerrar sesión
					</a>
				</sec:authorize>

			</div>

		</div>
	</div>

</nav>
