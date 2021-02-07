<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="aeropuertos">
    <h2>
        <c:if test="${aeropuerto['new']}">Nuevo </c:if> Aeropuerto
    </h2>
    <form:form modelAttribute="aeropuerto" class="form-horizontal" id="add-aeropuerto-form">
        <input type="hidden" name= "id" value= "${aeropuerto.id}"/>
    	<input type="hidden" name= "version" value= "${aeropuerto.version}"/>
        <div class="form-group has-feedback">
            <aerolineasAAAFC:inputField label="Nombre del aeropuerto" name="nombre"/>
            <aerolineasAAAFC:inputField label="Localización" name="localizacion"/>
            <aerolineasAAAFC:inputField label="Código IATA" name="codigoIATA"/>
            <aerolineasAAAFC:inputField label="Teléfono" name="telefono"/>
            <aerolineasAAAFC:inputField label="Vuelos Salida" name="vuelosSalida"/>
            <aerolineasAAAFC:inputField label="Vuelos Llegada" name="vuelosLlegada"/>
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${aeropuerto['new']}">
                        <button class="btn btn-default" type="submit">Añadir aeropuerto</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar aeropuerto</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</aerolineasAAAFC:layout>