<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="controladores">

	<div style="padding-left: 40%">
 	<form action="/controladores/${pControlId}/horario" method="get">
			<label for="fecha">Fecha: </label> <input name="fecha" id="fecha"
				type="month" pattern="^(19|2[0-1])\d{2}-(0[1-9]|1[0-2])$"
				placeholder="yyyy-mm" />
			<button type="submit" class="btn btn-default">Buscar</button>
		</form>
	</div>
	<div class="month">
		<ul>
			<li>${mes}<br><span style="font-size:20px">${año}</span></li>
		</ul>
	</div>

	<ul class="weekdays">
		<li>Mo</li>
		<li>Tu</li>
		<li>We</li>
		<li>Th</li>
		<li>Fr</li>
		<li>Sa</li>
		<li>Su</li>
	</ul>

	<ul class="days">
	<c:forEach var="dia" begin="1" end="${dias}" >
		<li>
			<c:out value="${dia}"/>
		</li>
	</c:forEach>
	</ul>


</aerolineasAAAFC:layout>
