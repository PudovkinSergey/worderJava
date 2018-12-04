<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Form</title>
</head>
<body>

<form action="wordlist.jsp" method="get">
    <input type="submit" value="Show list of all words" />
    <label>&nbsp;</label>
</form>
<p><i>${message}</i></p>
<form action="addword.jsp" method="post">

    <input type="submit" value="Add new translation." />
</form>
</body>
</html>