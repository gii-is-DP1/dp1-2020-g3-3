<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="clientes">

    <h2>Información del Cliente</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${cliente.nombre} ${cliente.apellidos}"/></b></td>
        </tr>
        <tr>
            <th>NIF</th>
            <td><c:out value="${cliente.nif}"/></td>
        </tr>
        <tr>
            <th>Dirección de Facturación</th>
            <td><c:out value="${cliente.direccionFacturacion}"/></td>
        </tr>
        <tr>
            <th>IBAN</th>
            <td><c:out value="${cliente.iban}"/></td>
        </tr>
        
        <tr>
            <th>Fecha de Nacimiento</th>
            <td><aerolineasAAAFC:localDate date="${cliente.fechaNacimiento}" pattern="yyyy-MM-dd"/></td>
        </tr>
    </table>

    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Cliente</a>


    <br/>
    <br/>
    <h2>Billetes</h2>

    <table class="table table-striped">
        <c:forEach var="billete" items="${cliente.billetes}">
        
            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                    	<dt>Coste</dt>
                        <dd><c:out value="${billete.coste}"/></dd>
                        <dt>Asiento</dt>
                        <dd><c:out value="${billete.asiento}"/></dd>
                        <dt>Fecha de reserva</dt>
                        <dd><aerolineasAAAFC:localDate date="${billete.fechaReserva}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Clase</dt>
                        <dd><c:out value="${billete.clase}"/></dd>
                    </dl>
                </td>
            </tr>

        </c:forEach>
    </table> 


</aerolineasAAAFC:layout>
