<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ByuSell</title>
</head>
<body>
<h1><a href="http://localhost:8080">BUYSELL</a></h1><hr>
<h4>Авторизация</h4>
<form action="/login" method="post">
    Эл. почта: <input type="email" name="username" placeholder="Введите ваш почтовый адрес" required><br><br>
    Пароль: <input type="password" name="password" placeholder="Введите пароль от аккаунта" required><br><br>
    <input type="checkbox" name="remember-me"> Запомнить меня
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход"/>
</form>
<br>
<div <#if errorActivation??>
    <b style="color: red">${errorActivation}</b>
    <br><br>
</#if>
</div>
<a href="/registration">Зарегистрироваться</a>
</body>
</html>