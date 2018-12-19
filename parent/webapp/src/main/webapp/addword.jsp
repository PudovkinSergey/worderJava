<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">


    <title>Form for adding words with translation</title>
</head>
<body>
<h1>You can add new translation here.</h1>
<p><i>${message}</i></p>
<form action="addword" method="POST">
    <input type="hidden" name="action" value="add">
    Word: <input type="text" name="word"  value="${wordTranslation.word}"/>
    <br><br>
    Translation: <input type="text" name="translation" value="${wordTranslation.translation}"/>
    <br><br>
    <input type="submit" value="Submit" />
</form>
<form action="index.jsp" method="get">
    <input type="submit" value="Main page">
</form>
</body>
</html>