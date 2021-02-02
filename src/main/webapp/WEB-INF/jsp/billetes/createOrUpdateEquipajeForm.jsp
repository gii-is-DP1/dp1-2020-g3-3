<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="equipajes">
	<h2>
		<c:if test="${equipaje['new']}">Nuevo </c:if>
		equipaje
	</h2>
	<form:form modelAttribute="equipaje" class="form-horizontal"
		id="add-equipaje-form">
		<input type="hidden" name= "id" value= "${equipaje.id}"/>
    	<input type="hidden" name= "version" value= "${equipaje.version}"/>
		<div class="form-group has-feedback">
			<aerolineasAAAFC:inputField label="Peso" name="peso" />
			<aerolineasAAAFC:selectField label="Medidas" name="equipajeBase"
				names="${equipajes}"/>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Añadir
					equipaje</button>

			</div>
		</div>
	</form:form>
</aerolineasAAAFC:layout>