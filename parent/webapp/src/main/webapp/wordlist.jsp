<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <title>List of all words</title>
</head>
<body>
<h1>List of all words</h1>
<p><i>${message}</i></p>
    <table>
        <thead>
            <tr>
                <th>Original Word</th>
                <th>Translation</th>
                <th class="but"></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="word" items="${wordList}">
               <tr>
                   <td></td>
                    <td><strong></strong>${word.word}</td>
                    <td>${word.translation}</td>
               </tr>
            </c:forEach>
        </tbody>

    </table>


<form action="addword.jsp" method="get">
    <input type="submit" value="add word" />
</form>
<form action="index.jsp" method="get">
    <input type="submit" value="Main page">
</form>
</body>
</html>


