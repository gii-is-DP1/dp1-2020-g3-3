<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<!-- ¿Queremos hacer una muestra individual de cada avión o sirve el
display de todas juntas? Dependiendo de esto se ponen los botones de
edición y borrado en una vista u otra -->

<aerolineasAAAFC:layout pageName="aviones">

    <h2> Información técnica sobre el avión con ID: <c:out value="${avion.id}"/></h2>


    <table class="table table-striped">
        <tr>
            <th>Tipo</th>
            <td><b><c:out value="${avion.tipoAvion}"/></b></td>
        </tr>
        <tr>
            <th>Horas de vuelo acumuladas</th>
            <td><c:out value="${avion.horasAcumuladas}"/></td>
        </tr>
        <tr>
            <th>Fecha de fabricación</th>
            <td><c:out value="${avion.fechaFabricacion}"/></td>
        </tr>
        <tr>
            <th>Última revisión</th>
            <td><c:out value="${avion.fechaRevision}"/></td>
        </tr>
        <tr>
            <th>Disponibilidad</th>
            <td>
                <c:choose>
    				<c:when test="${avion.disponibilidad==true}">
        				Sí 
        				<br />
    				</c:when>    
    				<c:otherwise>
        				No 
        				<br />
    				</c:otherwise>
				</c:choose>
            </td>
        </tr>
    </table>
    
    <h2> Información sobre capacidad de pasajeros</h2>
    <table class="table table-striped">
    	<tr>
            <th>Número total de pasajeros</th>
            <td><c:out value="${avion.capacidadPasajero}"/></td>
        </tr>
        <tr>
            <th>Plazas clase económica</th>
            <td><c:out value="${avion.plazasEconomica}"/></td>
        </tr>
        <tr>
            <th>Plazas clase ejecutiva</th>
            <td><c:out value="${avion.plazasEjecutiva}"/></td>
        </tr>
        <tr>
            <th>Plazas primera clase</th>
            <td><c:out value="${avion.plazasPrimera}"/></td>
        </tr>
    </table>

    <spring:url value="/aviones/{avionId}/edit" var="avionUrl">
        <spring:param name="avionId" value="${avion.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(avionUrl)}" class="btn btn-default">Editar avión</a>


    <br/>
    <br/>

    <h2>Vuelos asociados</h2>

    <table class="table table-striped">
        <c:forEach var="vuelo" items="${avion.vuelos}">


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