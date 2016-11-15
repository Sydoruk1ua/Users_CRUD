<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page adding user</title>
</head>
<body>
<h1>Сторінка додавання користувача</h1>

<p>Додайте нового користувача.</p>
<font size="5" color="red" face="Arial">${message}</font>

<form action="${pageContext.request.contextPath}/user/add" method="post">
    <table>
        <tr>
            <td><label for="name-field">Ім'я</label></td>
            <td><input type="text" name="name" id="name-field"></td>
        </tr>
        <tr>
            <td><label for="age-field">Вік</label></td>
            <td><input type="text" name="age" id="age-field"></td>
        </tr>
        <tr>
            <td>Адміністратор:</td>
            <td>
                <select name="isAdmin">
                    <option value="true">Так</option>
                    <option selected="selected" value="false">Ні</option>
                </select>
            </td>


        </tr>

        <tr>
            <td></td>
            <td><input type="submit" value="Додати"></td>
        </tr>
    </table>
</form>
<br/>


<p><a href="${pageContext.request.contextPath}/">Повернутися на головну сторінку</a></p>
</body>
</html>