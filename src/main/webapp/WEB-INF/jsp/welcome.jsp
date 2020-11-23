<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    <div class="row">
    <h2> Project ${title}</h2>
    <h2> Group ${group}</h2>
    <img src="WEB-INF/jsp/favicon-32x32.png">
    </div>

</AAAFC:layout>
</aerolineasAAAFC:layout>

