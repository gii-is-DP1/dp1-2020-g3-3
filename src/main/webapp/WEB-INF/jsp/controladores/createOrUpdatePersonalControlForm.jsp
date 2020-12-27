<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="controladores">
    <h2>
        <c:if test="${personalControl['new']}">Nuevo </c:if> Controlador
    </h2>
    <form:form modelAttribute="controlador" class="form-horizontal" id="add-controlador-form">
        <div class="form-group has-feedback">
            <aerolineasAAAFC:inputField label="Nombre" name="nombre"/>
            <aerolineasAAAFC:inputField label="Apellidos" name="apellidos"/>
            <aerolineasAAAFC:inputField label="NIF" name="nif"/>
            <aerolineasAAAFC:inputField label="IBAN" name="iban"/>
             <aerolineasAAAFC:inputField label="Rol" name="rol"/>
            <aerolineasAAAFC:inputField label="Salario" name="salario"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${personalControl['new']}">
                        <button class="btn btn-default" type="submit">Añadir controlador</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar controlador</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</aerolineasAAAFC:layout>