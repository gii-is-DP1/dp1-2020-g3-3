<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="personalOficina">

    <h2>Información del Personal de Oficina</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre y apellidos</th>
            <td><b><c:out value="${personalOficina.nombre} ${personalOficina.apellidos}"/></b></td>
        </tr>
        <tr>
            <th>NIF</th>
            <td><c:out value="${personalOficina.nif}"/></td>
        </tr>
        <tr>
            <th>IBAN</th>
            <td><c:out value="${personalOficina.iban}"/></td>
        </tr>
        
        
        <tr>
            <th>Salario</th>
            <td><c:out value="${personalOficina.salario}"/></td>
        </tr>
        
    </table>

  	<spring:url value="/personalOficina/{pOficinaId}/edit" var="editUrl">
        <spring:param name="pOficinaId" value="${personalOficina.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Oficinista</a>

    <br/>
    <br/>
    <h2>Vuelos gestionados</h2>

    <table class="table table-striped">
        <c:forEach var="vuelo" items="${personalOficina.vuelos}">
            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>ID Vuelo</dt>
                        <dd onclick="javascript:location.href='/vuelos/${vuelo.id}'" onmouseover="" style="cursor: pointer;"><b><c:out value="${vuelo.id}"/></b></dd>
                        <dt>Fecha de salida</dt>
                        <dd><aerolineasAAAFC:localDateTime date="${vuelo.fechaSalida}" pattern="yyyy-MM-dd HH:mm"/></dd>
                        <dt>Fecha de llegada</dt>
                        <dd><aerolineasAAAFC:localDateTime date="${vuelo.fechaLlegada}" pattern="yyyy-MM-dd HH:mm"/></dd>
                    </dl>
                </td>
            </tr>
        </c:forEach>
    </table>


</aerolineasAAAFC:layout>
