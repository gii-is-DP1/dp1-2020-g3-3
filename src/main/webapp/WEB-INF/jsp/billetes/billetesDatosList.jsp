<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="billetes">

	<c:choose>
		<c:when test="${empty param.apellidos}">
			<h2 class="centrado">Billetes</h2>
		</c:when>
		<c:otherwise>
			<h2 class="centrado">Billetes del apellido ${param.apellidos}</h2>
		</c:otherwise>
	</c:choose>

	<div style="float: right; padding-bottom: 1%;">
	<form action="/billetes/datos" method="get">
		<label for="apellidos">Apellidos: </label>
   		<input name="apellidos" id="apellidos" type="text" pattern="^\w+( \w+)*$"/>
   		<button type="submit" class="btn btn-default">Buscar por apellidos</button>
   	</form>
	</div>

    <table id="tablaBilletes" class="table table-striped centrado">
        <thead>
        <tr>
            <th class="centrado">ID Billete</th>
            <th class="centrado">Cliente</th>
            <th class="centrado">Importe</th>
            <th class="centrado">Direccion de facturacion</th>
            <th class="centrado">Asiento</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
			<c:when test="${billetes.size() > 0 }">
		        <c:forEach items="${billetes}" var="billete">
		            <tr>
		                <td>
		         			<c:out value="${billete.id}"/>
		                </td>
		                <td>
		                    <c:out value="${billete.cliente.apellidos}, ${billete.cliente.nombre} "/>
		                </td>
		                <td>
		                    <c:out value="${billete.coste}"/>
		                </td>
		                <td>
		                    <c:out value="${billete.cliente.direccionFacturacion}"/>
		                </td>
		                <td>
							<c:out value="${billete.asiento.nombre}"/>
		                </td>     
		            </tr>
		        </c:forEach>
        	</c:when>
				<c:otherwise>
					<tr class="centrado">
						<c:if test="${not empty msg}"><td colspan="10"><h3><c:out value="${msg}"/></h3></td></c:if>
						<c:if test="${empty msg}"><td colspan="10"><h3>¡Lo sentimos! No se encuentra ningún billete.</h3></td></c:if>
					</tr>
				</c:otherwise>
		</c:choose>
			
        </tbody>
    </table>
    
    	<div class="panel-footer centrado">
		<h3>Mostrando página ${number+1} de ${totalPages}</h3>
		<ul class="pagination" style="margin: 0px;">
			<c:forEach begin="0" end="${totalPages-1}" var="page">
				<li class="page-item"><a
					href="datos?apellidos=${param.apellidos}&page=${page}&size=${size}" class="page-link">${page+1}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	
				<h2 class="centrado">Ganancias semanales: 	<fmt:formatNumber value="${gananciasSemanales}" maxFractionDigits="2"/> EUR</h2>
        
</aerolineasAAAFC:layout>