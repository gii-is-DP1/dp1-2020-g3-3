<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<!-- Pagina en la que se mostraran los vuelos y se permitira acceder a modificarlos -->

<aerolineasAAAFC:layout pageName="vuelos">
    <h2>Vuelos</h2>

    <table id="tablaVuelos" class="table table-striped">
        <thead>
        <tr>
            <th>Fecha de salida</th>
            <th>Fecha de llegada</th>
            <th>Coste del vuelo</th>
           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vuelos}" var="vuelo">
            <tr>
              <%---    <td>
                   <spring:url value="/vuelo/{vueloId}" var="vueloURL">
                        <spring:param name="vueloId" value="${vuelo.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(vueloUrl)}"><c:out value="${avion.id}"/></a>
                </td>
                <td><aerolineasAAAFC:inputField name="fechaSalida" date="${vuelo.fechaSalida}" pattern="yyyy-MM-dd'T'HH:mm"/></td>
                <td><c:out value="${vuelo.fechaSalida}"/>
                <td><c:out value="${vuelo.fechaLlegada}"/>
                <td><aerolineasAAAFC:localDate  date="${vuelo.fechaSalida}" pattern="yyyy-MM-dd hh:mm"/></td>
                <td><aerolineasAAAFC:localDate  date="${vuelo.fechaLlegada}" pattern="yyyy-MM-dd hh:mm"/></td>
				--%>
          
               <td><c:out value="${vuelo.fechaSalida}"/>
                <td><c:out value="${vuelo.fechaLlegada}"/>

                <td><c:out value="${vuelo.coste}"/></td>
                <tr>
					<td><a href="<spring:url value="/vuelos/${vuelo.id}/edit" htmlEscape="true" />">Editar</a>
				</tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="<spring:url value="/vuelos/new" htmlEscape="true" />">Nuevo</a>
</aerolineasAAAFC:layout>