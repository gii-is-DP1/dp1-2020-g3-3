<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags" %>

<aerolineasAAAFC:layout pageName="error">

    <h2 class="centrado">Parece que ha habido un problema...</h2>

    <p>${exception.message}</p>

</aerolineasAAAFC:layout>
