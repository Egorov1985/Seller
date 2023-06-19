<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ByuSell</title>
    <link rel="stylesheet" href="/public/css/registration.css">
</head>
<body>
<h1>BuySell</h1><hr>
<h4>Регистрация</h4>
<form action="/registration" method="post">
        Имя пользователя :<br>
        <@spring.formInput "user.name"/>
        <@spring.showErrors "<br>"/>
        <br><br>
        Эл.почта :<br>
        <@spring.formInput "user.email"/>
        <@spring.showErrors "<br>"/>
        <br><br>
        Номер телефона:<br>
        <@spring.formInput "user.phoneNumber"/>
        <@spring.showErrors "<br>"/>
        <br><br>
        Пароль :<br>
        <@spring.formInput "user.password"/>
        <@spring.showErrors "<br>"/>
        <br><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Зарегистрироваться">

</form>
<script src="/public/js/registration.js"></script>
<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>
</body>
</html>