<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<aerolineasAAAFC:layout pageName="controladores">


	<div style="padding: 5% 0% 0% 43%">
 	<form action="/controladores/${pControlId}/horario" method="get">
			<label for="fecha">Fecha: </label> <input name="fecha" id="fecha" type="date" />
			<button type="submit" class="btn btn-default">Buscar</button>
		</form>
	</div>
	
	<div class="calendario">
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
	
	 	<ul class="days" id="days">
		<c:forEach var="dia" begin="1" end="${dias}" >
			<c:forEach items="${diasV}" var="diaV">
				<c:if test="${diaV == dia}">
					<c:choose>
						<c:when test="${diaV < 10}">
						<li id="${dias}" onclick="javascript:location.href='/controladores/${pControlId}/semana?fecha=${año}-${mesN}-0${diaV}'" onmouseover="" style="cursor: pointer;">
							<span class="active">
								<c:out value="${dia}"/>
							</span>
						</li>
						</c:when>
						<c:otherwise>
						<li id="${dias}" onclick="javascript:location.href='/controladores/${pControlId}/semana?fecha=${año}-${mesN}-${diaV}'" onmouseover="" style="cursor: pointer;">
							<span class="active">
								<c:out value="${dia}"/>
							</span>
						</li>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach>
			<li id="${dias}">
				<c:out value="${dia}"/>
			</li>
		</c:forEach>
		</ul> 
	</div>
	
	<spring:url value="/controladores/{pControlId}" var="editUrl">
        <spring:param name="pControlId" value="${pControlId}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default" style="float:right;">Volver</a>
    
</aerolineasAAAFC:layout>
