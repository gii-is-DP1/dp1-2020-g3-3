<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- Archivo .jsp que muestra el listado de todos los aviones, a su vez se
puede acceder a la ficha de cada avión (por id) para editarlo o borrarlo -->

<aerolineasAAAFC:layout pageName="aviones">
    <h2 class="centrado">Estado de aviones</h2>

    <table id="tablaAviones" class="table table-striped centrado">
        <thead>
        <tr>
            <th class="centrado">ID Avión</th>
            <th class="centrado">Tipo de avión</th>
            <th class="centrado">Horas de vuelo acumuladas</th>
            <th class="centrado">Fecha de fabricación</th>
            <th class="centrado">Última revisión</th>
            <th class="centrado">Disponibilidad</th>
            <th class="centrado">Estacionamiento</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${aviones}" var="avion">
            <tr>
                <td>
                    <spring:url value="/aviones/{avionId}" var="avionUrl">
                        <spring:param name="avionId" value="${avion.id}"/>
                    </spring:url>
                    <b><a href="${fn:escapeXml(avionUrl)}"><c:out value="${avion.id}"/></a></b>
                </td>
                <td>
                    <c:out value="${avion.tipoAvion}"/>
                </td>
                <td>
                    <c:out value="${avion.horasAcumuladas}"/>
                </td>
                <td>
                    <c:out value="${avion.fechaFabricacion}"/>
                </td>
                <td>
                    <c:out value="${avion.fechaRevision}"/>
                </td>
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
     		<c:forEach items="${avion.vuelos}" var="vuelo" end="0">
                	<td>
            			<c:out value="${vuelo.aeropuertoDestino.nombre}"/>
            		</td>
        	</c:forEach> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
	 
	 <div class="panel-footer centrado">
	 <h3>Mostrando página ${number+1} de ${totalPages}</h3>
            <ul class="pagination" style="margin: 0px;">
                <c:forEach begin="0" end="${totalPages-1}" var="page">
                    <li class="page-item">
                        <a href="rutaAviones?page=${page}&size=${size}" class="page-link">${page+1}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>


</aerolineasAAAFC:layout>