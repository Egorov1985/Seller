<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ByuSell</title>
</head>
<body>
<h1>BUYSELL</h1><hr>
<h4>Авторизация</h4>
<form action="/login" method="post">
    Эл. почта: <input type="email" name="username"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход"/>
</form>
<br>
<a href="/registration">Зарегистрироваться</a>
</body>
</html>