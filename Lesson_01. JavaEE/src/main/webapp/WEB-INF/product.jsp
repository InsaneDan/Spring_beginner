<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">


<c:import url="header.jsp"/>

<body>

<c:import url="navbar.jsp"/>

<div class="container">
    <div class="row py-2">

        <div class="col-12">
            <table class="table table-bordered my-2">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Price</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${requestScope.products}">
                    <tr>
                        <th scope="row">
                            <c:out value="${product.id}"/>
                        </th>
                        <td>
                            <c:out value="${product.name}"/>
                        </td>
                        <td>
                            <c:out value="${product.description}"/>
                        </td>
                        <td>
                            $ <c:out value="${product.price}"/>
                        </td>

                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>