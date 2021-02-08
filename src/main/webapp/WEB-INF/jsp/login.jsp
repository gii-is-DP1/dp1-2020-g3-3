<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="aerolineasAAAFC" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" rel="stylesheet">


</head>
<body style="background-color: #f1f1f1;">
<div class="container-fluid text-center" style="margin-top: 10%">
	<div>
		<spring:url value="/resources/images/logoT.png" var="logo" />
		<img class="imagen" alt="AAAFC logo" src="${logo}" style="width:30%;">
	</div>

   <form method="POST" action="/login" style="max-width: 350px; margin:0 auto;">
   	<div class="border border-secondary p-3 rounded" style="background-color: white;">
   		<h2>Iniciar Sesión</h2>
   		 <p>
         	<label for="username" class="sr-only">Usuario</label>
         	<input type="text" id="username" name ="username" class="form-control" placeholder="Usuario" required autofocus>
      	</p>
      	<p>
         	<label for="password" class="sr-only">Contraseña</label>
         	<input type="password" id="password" name ="password" class="form-control" placeholder="Contraseña" required>
      	</p>

   		<button class="btn btn-primary" type="submit" style="background-color: #34302d; border-color: #34302d">Entrar</button>
   		
   	</div>
   </form>
   
   	<div>
		<c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
	</div>
   
</div>    
</body>
</html>