<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page editing user data</title>
</head>
<body>
<h1>Сторінка редагування даних користувача</h1>

<p>Тут Ви можете оновити дані користувача</p>
<font size="5" color="red" face="Arial">${message}</font>
<%--<p>${message}</p>--%>

<form action="${pageContext.request.contextPath}/user/edit/${user.id}" method="post">
    <table>
        <tr>
            <td><label for="name-field">Ім'я:</label></td>
            <td><input type="text" name="name" id="name-field" value="${user.name}"></td>
        </tr>
        <tr>
            <td><label for="age-field">Вік:</label></td>
            <td><input type="text" name="age" id="age-field" value="${user.age}"></td>
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
            <td><input type="submit" value="Зберегти"></td>
        </tr>
    </table>
</form>
<br/>

<p><a href="${pageContext.request.contextPath}/">Повернутися на головну сторінку</a></p>
</body>
</html>
