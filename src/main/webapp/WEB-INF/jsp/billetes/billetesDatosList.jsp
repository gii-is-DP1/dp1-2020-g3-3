<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="billetes">
    
    <c:if test="${empty param.apellidos}"><h2 style="float: left;">Billetes de este mes </h2></c:if>
    <c:if test="${not empty param.apellidos}"><h2 style="float: left;">Billetes de ${param.apellidos} </h2></c:if>
    
    <div style="float: right;">
	<form action="/billetes/datos" method="get">
		<label for="apellidos">Apellidos: </label>
   		<input name="apellidos" id="apellidos" type="text" />
   		<button type="submit" class="btn btn-default">Buscar por apellidos</button>
   	</form>
	</div>
    
    
  

    <table id="tablaBilletes" class="table table-striped">
        <thead>
        <tr>
            <th>ID Billete</th>
            <th>CLiente</th>
            <th>Importe</th>
            <th>Direccion de facturacion</th>
            <th> </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${billetes}" var="billete">
            <tr>
                <td>
         			<c:out value="${billete.id}"/>
                </td>
                <td>
                    <c:out value="${billete.cliente.nombre}"/>
                </td>
                <td>
                    <c:out value="${billete.coste}"/>
                </td>
                <td>
                    <c:out value="${billete.cliente.direccionFacturacion}"/>
                </td>
                <td>

                </td>     
            </tr>
        </c:forEach>
        </tbody>
    </table>
        <p>${exception.message}</p>
</aerolineasAAAFC:layout>