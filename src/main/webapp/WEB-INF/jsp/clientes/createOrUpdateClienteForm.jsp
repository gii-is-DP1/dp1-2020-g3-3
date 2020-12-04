<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clientes">
    <h2>
        <c:if test="${cliente['new']}">Nuevo </c:if> Cliente
    </h2>
    <form:form modelAttribute="cliente" class="form-horizontal" id="add-cliente-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre" type="text"/>
            <petclinic:inputField label="Apellidos" name="apellidos" type="text"/>
            <petclinic:inputField label="NIF" name="nif" type="text"/>
            <petclinic:inputField label="Direccion de Facturacion" name="direccionFacturacion" type="text"/>
            <petclinic:inputField label="IBAN" name="iban" type="text"/>
            <petclinic:inputField label="Fecha de Nacimiento" name="fechaNacimiento" type="date"/>
            <petclinic:inputField label="Username" name="user.username" type="text"/>
            <petclinic:inputField label="Password" name="user.password" type="password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${cliente['new']}">
                        <button class="btn btn-default" type="submit">Add Cliente</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Cliente</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
