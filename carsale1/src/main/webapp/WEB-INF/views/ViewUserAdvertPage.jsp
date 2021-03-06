<%@ page import="ru.job4j.models.Advert" %>
<%@ page import="ru.job4j.storage.CarStor" %>
<%@ page import="ru.job4j.models.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adverts</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            $(".myTable").change(function(event) {
                var editId = event.target.id;
                $.ajax
                ({
                    method: "POST",
                    url: "./editstatus",
                    data: {"id": editId},
                    complete: function load() {
                        loadItems();
                    }
                });
            });
        });
    </script>
</head>
<body>
<div>
    <form action="${pageContext.servletContext.contextPath}/main" method="get">
        <input type="submit" value="К списку всех объявлений">
    </form>
</div>
<div>
    <% List<Advert> list = CarStor.INSTANCE.getaStor().findByUser((User) session.getAttribute("user"));
        if (list.size() > 0) { %>
    <table border="1" class="myTable">
        <tr>
            <th>Brand</th>
            <th>Model</th>
            <th>Time</th>
            <th>Short desc</th>
            <th>Status</th>
            <th>Delete</th>
        </tr>
        <% for (Advert advert : list) { %>
        <tr>
            <td><%=advert.getBrand().getName()%></td>
            <td><%=advert.getModel().getName()%></td>
            <td><%=advert.getTime()%></td>
            <td><%=advert.getName()%></td>
            <% if (advert.isStatus()) { %>
            <td><input id="<%=advert.getId()%>" type='checkbox' checked/></td>
            <% } else { %>
            <td><input id="<%=advert.getId()%>" type='checkbox'/></td>
            <% } %>
            <form action="${pageContext.servletContext.contextPath}/user/adverts" method="post">
                <input type="hidden" name="id" value="<%=advert.getId()%>">
                <td><input type="submit" value="удалить"></td>
            </form>
        </tr>
        <% } %>
        <% } %>
    </table>
</div>
</body>
</html>
