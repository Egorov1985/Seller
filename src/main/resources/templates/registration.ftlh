<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
    <link href="/public/css/registration.css" rel="stylesheet">
</head>
<body>
<a href="http://localhost:8080/product/products"> Вернуться на главную страницу </a>
<h1>BUYSELL</h1><hr>
<h4>Регистрация</h4>
<form action="/registration" method="post">

    Имя пользователя :
    <@spring.formInput "user.name"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Эл.почта :
    <@spring.formInput "user.email"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Номер телефона:
    <@spring.formInput "user.phoneNumber"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Пароль :
    <@spring.formInput "user.password"/>
    <@spring.showErrors "<br>"/>
    <br><br>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Зарегистрироваться"/>
</form>
<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>

<script src="/public/js/registration.js"></script>
</body>
</html>