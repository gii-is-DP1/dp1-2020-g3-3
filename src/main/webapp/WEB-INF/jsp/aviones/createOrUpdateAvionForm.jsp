<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<!-- DUDA: �deber�amos poner un choose para distinguir el formulario de alta 
(fecha de revision=fecha fabricacion y horas acumuladas=0) del de update? -->

<aerolineasAAAFC:layout pageName="aviones">
    <h2>
        <c:if test="${avion['new']}">Nuevo </c:if> Avi�n
    </h2>
    <form:form modelAttribute="avion" class="form-horizontal" id="add-avion-form">
      	<input type="hidden" name= "id" value= "${avion.id}"/>
    	<input type="hidden" name= "version" value= "${avion.version}"/>
        <div class="form-group has-feedback">
            <aerolineasAAAFC:inputField label="Tipo de avi�n" name="tipoAvion"/>
            <aerolineasAAAFC:inputField label="Capacidad de pasajeros" name="capacidadPasajero" type="number"/>
            <aerolineasAAAFC:inputField label="Horas acumuladas" name="horasAcumuladas" type="number"/>
            <aerolineasAAAFC:inputField label="Fecha de fabricaci�n" type="date" name="fechaFabricacion"/>
            <aerolineasAAAFC:inputField label="Disponibilidad" name="disponibilidad"/>
            <aerolineasAAAFC:inputField label="Fecha de revisi�n" type="date" name="fechaRevision"/>
            <aerolineasAAAFC:inputField label="Plazas clase econ�mica" name="plazasEconomica" type="number"/>
            <aerolineasAAAFC:inputField label="Plazas clase ejecutiva" name="plazasEjecutiva" type="number"/>
            <aerolineasAAAFC:inputField label="Plazas primera clase" name="plazasPrimera" type="number"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${avion['new']}">
                        <button class="btn btn-default" type="submit">A�adir avi�n</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar avi�n</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</aerolineasAAAFC:layout>