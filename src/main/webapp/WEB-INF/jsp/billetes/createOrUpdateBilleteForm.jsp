<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="billetes">
    <h2>
        <c:if test="${billete['new']}">Nuevo </c:if> Billete
    </h2>
    <form:form modelAttribute="avion" class="form-horizontal" id="add-billete-form">
        <div class="form-group has-feedback">
            <aerolineasAAAFC:inputField label="Precio de venta" name="coste"/>
            <aerolineasAAAFC:inputField label="Asiento" name="asiento"/>
            <aerolineasAAAFC:inputField label="Fecha de reserva" type="date" name="fechaReserva"/>
            <aerolineasAAAFC:inputField label="Tipo de clase" name="clase"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${billete['new']}">
                        <button class="btn btn-default" type="submit">A�adir billete</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar billete</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</aerolineasAAAFC:layout>