<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL</h1><hr>
<h3> Подробная информация о пользователе ${user.name}</h3>
<b>Номер телефона </b> ${user.getPhoneNumber()} <br>

<h4>Товары пользователя ${user.name}</h4>
<#list products as product>
    <#if product.previewImageId?has_content>
        <p><img src="/images/${product.previewImageId}" height="50px"/> ${product.title}  ${product.price} руб.
            | <a href="/product/${product.id}">Подробнее...</a></p><br>
    <#else >
        <p><img src="/product/defaultImage"/> ${product.title}  ${product.price} руб.
            | <a href="/product/${product.id}">Подробнее...</a></p><br>
    </#if>
</#list>
<a href="/registration">Зарегистрироваться</a>
</body>
</html>