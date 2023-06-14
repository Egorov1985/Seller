<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
    <link href="src/main/resources/static/css/main.css" rel="stylesheet">
</head>
<body>
<h1>BUYSELL</h1><hr>
<h4>Регистрация</h4>
<form action="/registration" method="post">

    Имя пользователя :
    <@spring.formInput "userForm.name"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Эл.почта :
    <@spring.formInput "userForm.email"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Номер телефона:
    <@spring.formInput "userForm.phoneNumber"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Пароль :
    <@spring.formInput "userForm.password"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    <#--Эл. почта: <input type="email" name="email" ><br><br>
    Номер телефона: <input type="text" name="phoneNumber" ><br><br>
    Пароль: <input type="password" name="password" ><br><br>-->
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Зарегистрироваться"/>
</form>
<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>

<script src="src/main/resources/static/js/main.js"></script>
</body>
</html>