<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<!-- DUDA: ¿deberíamos poner un choose para distinguir el formulario de alta 
(fecha de revision=fecha fabricacion y horas acumuladas=0) del de update? -->

<aerolineasAAAFC:layout pageName="aviones">
    <h2>
        <c:if test="${avion['new']}">Nuevo </c:if> Avión
    </h2>
    <form:form modelAttribute="avion" class="form-horizontal" id="add-avion-form">
        <div class="form-group has-feedback">
            <aerolineasAAAFC:inputField label="Tipo de avión" name="tipoAvion"/>
            <aerolineasAAAFC:inputField label="Capacidad de pasajeros" name="capacidadPasajero"/>
            <aerolineasAAAFC:inputField label="Peso máximo de equipaje" name="pesoMaximoEquipaje"/>
            <aerolineasAAAFC:inputField label="Horas acumuladas" name="horasAcumuladas"/>
            <aerolineasAAAFC:inputField label="Fecha de fabricación" type="date" name="fechaFabricacion"/>
            <aerolineasAAAFC:inputField label="Disponibilidad" name="disponibilidad"/>
            <aerolineasAAAFC:inputField label="Fecha de revisión" type="date" name="fechaRevision"/>
            <aerolineasAAAFC:inputField label="Plazas clase económica" name="plazasEconomica"/>
            <aerolineasAAAFC:inputField label="Plazas clase ejecutiva" name="plazasEjecutiva"/>
            <aerolineasAAAFC:inputField label="Plazas primera clase" name="plazasPrimera"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${avion['new']}">
                        <button class="btn btn-default" type="submit">Añadir avión</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar avión</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</aerolineasAAAFC:layout>