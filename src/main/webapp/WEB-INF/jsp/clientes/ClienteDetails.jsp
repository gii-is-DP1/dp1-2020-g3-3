<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clientes">

    <h2>Informaci�n del Cliente</h2>


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
            <th>Direcci�n de Facturaci�n</th>
            <td><c:out value="${cliente.direccionFacturacion}"/></td>
        </tr>
        <tr>
            <th>IBAN</th>
            <td><c:out value="${cliente.iban}"/></td>
        </tr>
        
        <tr>
            <th>Fecha de Nacimiento</th>
            <td><petclinic:localDate date="${cliente.fechaNacimiento}" pattern="yyyy-MM-dd"/></td>
        </tr>
    </table>

    <spring:url value="{clienteId}/edit" var="editUrl">
        <spring:param name="clienteId" value="${cliente.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Cliente</a>


    <br/>
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
                        <dd><petclinic:localDate date="${billete.fechaReserva}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Clase</dt>
                        <dd><c:out value="${billete.clase}"/></dd>
                    </dl>
                </td>
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>