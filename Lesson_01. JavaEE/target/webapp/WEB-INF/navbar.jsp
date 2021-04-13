<%--<%@ page import="ru.geekbrains.persistence.ProductRepository" %>
<%@ page import="ru.geekbrains.persistence.Product" %>
<%@ page import="java.util.List" %>--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">

    <c:url value="/http-servlet" var="pageHttpServlet"/>
    <a class="navbar-brand" href="${pageHttpServlet}">HttpServlet</a>

    <c:url value="/product" var="pageProduct"/>
    <a class="navbar-brand" href="${pageProduct}">Продукт</a>

</nav>