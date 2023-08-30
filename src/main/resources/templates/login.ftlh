<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ByuSell</title>
</head>
<body>
<h1>BUYSELL</h1><hr>
<h4>Авторизация</h4>

<#if errorSighIn??>
    <h4 style="color: red"> ${errorSighIn}</h4>
</#if>
<form action="/login" method="post">
    Эл. почта: <input type="email" name="username" placeholder="Введите ваш почтовый адрес" required><br><br>
    Пароль: <input type="password" name="password" placeholder="Введите пароль от аккаунта" required><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход"/>
</form>
<br>
<a href="/registration">Зарегистрироваться</a>
</body>
</html>