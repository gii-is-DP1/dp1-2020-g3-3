<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<aerolineasAAAFC:layout pageName="aeropuertos">

    <h2>Find Aeropuertos</h2>

    
    <form:form modelAttribute="aeropuerto" action="/aeropuertos" method="get" class="form-horizontal"
               id="search-owner-form">
        <div class="form-group">
            <div class="control-group" id="codigoIATA">
                <label class="col-sm-2 control-label">Código IATA </label>
                <div class="col-sm-10">
                    <form:input class="form-control" path="codigoIATA" size="9" maxlength="9" minlength="9" type="text"/>
                    <span class="help-inline"><form:errors path="*"/></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Buscar Aeropuerto</button>
            </div>
        </div>

    </form:form>

    <br/> 
    <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/aeropuertos/new" htmlEscape="true"/>'>Añadir Aeropuerto</a>
	</sec:authorize>
	
</aerolineasAAAFC:layout>
