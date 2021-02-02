<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="personalOficina">
    <h2>
        <c:if test="${personalOficina['new']}">Nuevo </c:if> Oficinista
    </h2>
    <form:form modelAttribute="personalOficina" class="form-horizontal" id="add-oficinista-form">
    	<input type="hidden" name= "id" value= "${personalOficina.id}"/>
    	<input type="hidden" name= "version" value= "${personalOficina.version}"/>
        <div class="form-group has-feedback">
            <aerolineasAAAFC:inputField label="Nombre" name="nombre"/>
            <aerolineasAAAFC:inputField label="Apellidos" name="apellidos"/>
            <aerolineasAAAFC:inputField label="NIF" name="nif"/>
            <aerolineasAAAFC:inputField label="IBAN" name="iban"/>
            <aerolineasAAAFC:inputField label="Salario" name="salario"/>
			<aerolineasAAAFC:inputField label="Username" name="user.username" />
			<aerolineasAAAFC:inputField label="Password" name="user.password" type="password" />
			
			<form:errors> </form:errors>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${personalOficina['new']}">
                        <button class="btn btn-default" type="submit">Añadir oficinista</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar oficinista</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</aerolineasAAAFC:layout>