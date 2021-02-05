<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="azafatos">

    <h2>
        <c:if test="${azafato['new']}">Nuevo </c:if> Azafato
    </h2>
    <form:form modelAttribute="azafato" class="form-horizontal" id="add-azafato-form">
    	<input type="hidden" name= "id" value= "${azafato.id}"/>
    	<input type="hidden" name= "version" value= "${azafato.version}"/>
        <div class="form-group has-feedback">
        
			<aerolineasAAAFC:inputField label="Nombre" name="nombre" />
			<aerolineasAAAFC:inputField label="Apellidos" name="apellidos" />
			<aerolineasAAAFC:inputField label="NIF" name="nif" />
            <aerolineasAAAFC:inputField label="Salario" name="salario"/> 
            <aerolineasAAAFC:selectField label="Idiomas" name="idiomas" names="${idioma_types}"/>
            <aerolineasAAAFC:inputField label="IBAN" name="iban" />
            <aerolineasAAAFC:inputField label="Username" name="user.username" />
			<aerolineasAAAFC:inputField label="Password" name="user.password" type="password" />
			
			<form:errors> </form:errors>
			
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${azafato['new']}">
                        <button class="btn btn-default" type="submit">Añadir azafato</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar azafato</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    
</aerolineasAAAFC:layout>