<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List of all words</title>
</head>
<body>
<h1>List of all words</h1>
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


