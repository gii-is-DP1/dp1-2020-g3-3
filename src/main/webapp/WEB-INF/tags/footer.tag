<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/webjars/jquery/2.2.4/jquery.min.js" var="jQuery"/>
<script src="${jQuery}"></script>

<spring:url value="/webjars/jquery-ui/1.11.4/jquery-ui.min.js" var="jQueryUiCore"/>
<script src="${jQueryUiCore}"></script>

<spring:url value="/webjars/bootstrap/3.3.6/js/bootstrap.min.js" var="bootstrapJs"/>
<script src="${bootstrapJs}"></script>

<spring:url value="/resources/scripts/dropdown3Bars.js" var="sidedMenu"/>
<script src="${sidedMenu}"></script>

