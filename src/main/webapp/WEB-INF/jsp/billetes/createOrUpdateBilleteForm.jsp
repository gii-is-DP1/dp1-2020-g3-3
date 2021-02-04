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
   	
    <form:form modelAttribute="billete" class="form-horizontal" id="add-billete-form">
   		<input type="hidden" name= "id" value= "${billete.id}"/>
    	<input type="hidden" name= "version" value= "${billete.version}"/>
        <div class="form-group has-feedback">
            <aerolineasAAAFC:inputField label="Precio de venta" name="coste"/>
            <%--<aerolineasAAAFC:inputField label="Asiento" name="asiento"/> --%>
            <select name="asiento">
            	<c:forEach items="${asientos}" var="asiento">
            		<option value="${asiento.id}">${asiento.nombre}</option>
            	</c:forEach>
            </select>
            <input type="hidden" name= "fechaReserva" value= "${vuelo.fechaSalida}"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${billete['new']}">
                        <button class="btn btn-default" type="submit">Añadir billete</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar billete</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</aerolineasAAAFC:layout>