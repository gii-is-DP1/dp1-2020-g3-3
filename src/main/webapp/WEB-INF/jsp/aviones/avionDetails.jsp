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
            <th>Peso máximo equipaje</th>
            <td><c:out value="${avion.pesoMaximoEquipaje}"/></td>
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
    <br/>
    <h2>Vuelos y personal</h2>
<!--
    <table class="table table-striped">
        <c:forEach var="pet" items="${owner.pets}">


            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Name</dt>
                        <dd><c:out value="${pet.name}"/></dd>
                        <dt>Birth Date</dt>
                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Type</dt>
                        <dd><c:out value="${pet.type.name}"/></dd>
                    </dl>
                </td>
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>Visit Date</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <c:forEach var="visit" items="${pet.visits}">
                            <tr>
                                <td><petclinic:localDate date="${visit.date}" pattern="yyyy-MM-dd"/></td>
                                <td><c:out value="${visit.description}"/></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/edit" var="petUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(petUrl)}">Edit Pet</a>
                            </td>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/visits/new" var="visitUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(visitUrl)}">Add Visit</a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

        </c:forEach>
    </table>
-->

</aerolineasAAAFC:layout>