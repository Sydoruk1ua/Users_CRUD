<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>List of users</title>
    <style type="text/css">
        div {
            border: 2px;
        }

        .left {
            position: absolute;
            float: left;
            width: 1000px;
            left: 50px;
        }

        .right {
            float: right;
            width: 250px;
        }

        .buttonForAdd {
            padding: 12px 23px;
            border-radius: 3px;
            border: 2px solid #dcddde;
            border-bottom-color: #c9cacb;
            background: #f5f6f7;
            background: -moz-linear-gradient(top, #fcfdfe 0%, #f5f6f7 100%);
            background: -webkit-linear-gradient(top, #fcfdfe 0%, #f5f6f7 100%);
            background: -o-linear-gradient(top, #fcfdfe 0%, #f5f6f7 100%);
            box-shadow: inset 0 0 0 1px #fff;
            text-shadow: 0 1px 0 #fff;
            border-color: #68a8f7;

        }

        .button {
            padding: 4px 23px;
            border-radius: 3px;
            border: 1px solid #dcddde;
            border-bottom-color: #c9cacb;
            background: #f5f6f7;
            background: -moz-linear-gradient(top, #fcfdfe 0%, #f5f6f7 100%);
            background: -webkit-linear-gradient(top, #fcfdfe 0%, #f5f6f7 100%);
            background: -o-linear-gradient(top, #fcfdfe 0%, #f5f6f7 100%);
            box-shadow: inset 0 0 0 1px #fff;
            text-shadow: 0 1px 0 #fff;
        }

        .sizePage {
            font-size: 19pt;
        }


    </style>
</head>
<body>
<div class="right">
    <div>
        <h1>Пошук</h1>

        <form action="${pageContext.request.contextPath}/user/search" method="get">
            <table>
                <tr>
                    <td><label for="name-field">Ім'я</label></td>
                    <td><input type="text" name="name" id="name-field"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input class="button" type="submit" value="Пошук">
                        <a class="button" href="${pageContext.request.contextPath}/user/remove.html">Скинути${reset}</a>
                    </td>

                </tr>

            </table>
        </form>
        <br/>
        <p>
            <a class="buttonForAdd" href="${pageContext.request.contextPath}/user/add.html">Додати нового
                користувача</a><br/>

        </p>

    </div>

</div>

<div class="left" align="center">
    <h1>Список користувачів</h1>


    <c:if test="${not empty users}">
        <table border="1px" cellpadding="0" cellspacing="0">
            <thead>
            <tr>
                <th width="100px">ID</th>
                <th width="180px">ІМ'Я</th>
                <th width="50px">ВІК</th>
                <th width="150px">АДМІНІСТРАТОР</th>
                <th width="200px">ДАТА СТВОРЕННЯ</th>
                <th width="150px">ДІЯ</th>


            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">

                <tr>

                    <td align="center">${user.id}</td>
                    <td align="center">${user.name}</td>
                    <td align="center">${user.age}</td>
                    <td align="center">${user.isAdmin}</td>
                    <td align="center">${user.createdDate}</td>
                    <td align="center">
                        <a href="${pageContext.request.contextPath}/user/delete/${user.id}.html">ВИДАЛИТИ</a><br/>
                        <a href="${pageContext.request.contextPath}/user/edit/${user.id}.html">РЕДАГУВАТИ</a><br/>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
        <c:if test="${number > 1}">
            <div>
                <table>
                    <tr class="sizePage">

                        <td><a href="${pageContext.request.contextPath}/user/first">${first}</a></td>
                        <td><a href="${pageContext.request.contextPath}/user/prev">${prev}</a></td>
                        <c:forEach var="i" begin="${startPage}" end="${current}">
                            <td><a href="${pageContext.request.contextPath}/user/list.html?page=${i-1}">${i}</a></td>
                        </c:forEach>

                        <td>${current+1}</td>

                        <c:forEach var="i" begin="${current+2}" end="${endPage}">
                            <td><a href="${pageContext.request.contextPath}/user/list.html?page=${i-1}">${i}</a></td>
                        </c:forEach>
                        <td><a href="${pageContext.request.contextPath}/user/next">${next}</a></td>
                        <td><a href="${pageContext.request.contextPath}/user/last">${last}</a></td>

                    </tr>

                </table>
            </div>
        </c:if>
        <br/>
        <font size="7" color="green" face="Arial">${message}</font>
    </c:if>
    <c:if test="${empty users}">
        <font size="5" color="blue" face="Arial">По вашому запиту (</font><font size="6" color="black"
                                                                                face="Arial"> ${nameReturn}</font>
        <font size="5" color="blue" face="Arial">) відсутні результати!</font>
    </c:if>
</div>
</body>
</html>