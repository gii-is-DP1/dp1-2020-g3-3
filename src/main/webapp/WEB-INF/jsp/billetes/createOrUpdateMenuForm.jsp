<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="menus">
	<h2 class="centrado">Nuevo menú</h2>
	<form:form modelAttribute="menu" class="form-horizontal"
		id="add-menu-form">
    	<input type="hidden" name= "version" value= "${menu.version}"/>
		<div class="form-group has-feedback">
			<div class="container">
				<h3>Escoja un primer plato</h3>
				<div class="radio-toolbar">
					<ul class="ks-cboxtags">
						<c:forEach items="${platos_primeros}" var="platos"
							varStatus="loop">
							<li><form:radiobutton class="radioType"
									label="${platos.name}" path="plato1" value="${platos.name}" /></li>
						</c:forEach>
					</ul>
				</div>
				<h3>Escoja un segundo plato</h3>
				<div class="radio-toolbar">
					<ul class="ks-cboxtags">
						<c:forEach items="${platos_segundos}" var="platos"
							varStatus="loop">
							<li><form:radiobutton class="radioType"
									label="${platos.name}" path="plato2" value="${platos.name}" /></li>
						</c:forEach>
					</ul>
				</div>

				<h3>Escoja un postre</h3>
				<div class="radio-toolbar">
					<ul class="ks-cboxtags">
						<c:forEach items="${postres}" var="platos" varStatus="loop">
							<li><form:radiobutton class="radioType"
									label="${platos.name}" path="plato3" value="${platos.name}" /></li>
						</c:forEach>

					</ul>
				</div>
			</div>

		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10 centrado" style="margin-left:6%;">
				<button class="btn btn-default" type="submit">Añadir menú</button>
			</div>
		</div>
	</form:form>
</aerolineasAAAFC:layout>