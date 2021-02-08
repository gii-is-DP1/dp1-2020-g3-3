<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, clientes, vets or error"%>

<nav class="navbar navbar-default" role="navigation"
	style="border-top: 4px solid #c0c0c0;">

	<div class="main_container">
		<div class="main_header">
	
			<div id="mySidenav" class="sidenav">
				<a href="javascript:void(0)" class="closebtn"
					onclick="close3BarMenu()">&times;</a> 
					<a href="/aeropuertos">Aeropuertos</a> 
					<a href="/aviones">Aviones</a> 
					<a href="/vuelos">Vuelos</a> 
			</div>
			<sec:authorize access="hasAnyAuthority('admin','azafato','personalControl','personalOficina')">
				<span class=burger_button onclick="open3BarMenu()">&#9776;</span>
			</sec:authorize>
			<spring:url value="/resources/images/logo.png" var="logo" />
			<a class="logo" href="<spring:url value="/" htmlEscape="true" />"><span>
					<img class="imagen" alt="AAAFC logo" src="${logo}" width="55"
					height="16.5">
			</span></a>

			<div class="header-left">
				<sec:authorize access="!isAuthenticated()">
					<a href="https://github.com/gii-is-DP1/dp1-2020-g3-3">Sobre Nosotros</a> 
				</sec:authorize>
				<sec:authorize access="hasAnyAuthority('admin','azafato','personalControl','personalOficina')">
					<a href="/clientes">Clientes</a>
					<a href="/billetes/datos">Billetes</a>
				</sec:authorize>
				<sec:authorize access="hasAnyAuthority('admin','personalOficina')">
					<a href="/azafatos">Azafatos</a>
					<a href="/controladores">Controladores</a>
				</sec:authorize>
				<sec:authorize access="hasAnyAuthority('admin')">
					<a href="/personalOficina">Personal de Oficina</a>
				</sec:authorize>
				<sec:authorize access="hasAnyAuthority('cliente','azafato','personalControl','personalOficina')">
					<a href="/users/miPerfil">Mi perfil</a>
				</sec:authorize>	
			</div>

			<div class="header-right">
				<sec:authorize access="!isAuthenticated()">
					<a href="<c:url value="/login" />">Entra</a>
					<a href="<c:url value="/user/new" />">Regístrate</a>
				</sec:authorize>

				<sec:authorize access="isAuthenticated()">
					<a href="/users/miPerfil"> <span
						class="glyphicon glyphicon-user"></span>  
				<sec:authentication property="name" />
					</a>

					<a href="<c:url value="/logout" />"> <span
						class="glyphicon glyphicon-off"></span> Cerrar sesión
					</a>
				</sec:authorize>

			</div>

		</div>
	</div>

</nav>
