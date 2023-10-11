<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1><a href="http://localhost:8080">BUYSELL</a></h1><hr>
<#if userException??>
    <b>Omg... ${userException}</b>
    <#else >
        <h3> Подробная информация о пользователе ${user.name}:</h3>
        <b> Пользователь зарегистрирован с ${user.getDateOfCreated()}  </b>

        <h4>Товары пользователя: </h4>
        <#list products as product>
            <#if product.previewImage??>
                <p><img src="/product/${product.id}/previewImage" height="50px"/> ${product.title}  ${product.price} руб.
                    | <a href="/products/${product.id}">Подробнее...</a></p><br>
            <#else >
                <p><img src="/product/defaultImage"/> ${product.title}  ${product.price} руб.
                    | <a href="/products/${product.id}">Подробнее...</a></p><br>
            </#if>
        <#else >
            <b> ${user.name} пока ничего не продает </b>
        </#list>
</#if>
</body>
</html>