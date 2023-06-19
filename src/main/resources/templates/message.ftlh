<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ByuSell</title>
</head>
<body>
<h1>BuySell</h1>
<hr>
<h2>Сообщения</h2>

<#if product.previewImageId?has_content>
    <p><img src="/images/${product.previewImageId}" height="50px"/> ${product.title}  ${product.price} руб.
        | <a href="/product/${product.id}">Подробнее...</a></p><br>
<#else >
    <p><img src="/product/defaultImage"/> ${product.title}  ${product.price} руб.
        | <a href="/product/${product.id}">Подробнее...</a></p><br>
</#if></p>

<form action="">
    <input type="text" name="message">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" name="Отправить сообщение">
</form>



</body>
</html>