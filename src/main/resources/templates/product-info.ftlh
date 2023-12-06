<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1><a href="http://localhost:8080">BUYSELL</a></h1>
<hr>
<#if productException??>
    <b>Omg... ${productException}</b>
<#else >
    <h4> Подробная информация о товаре </h4>
    <#list images as img>
        <img src="/products/${product.id}/image/${img_index}" height="120px" alt=""/>
    <#else >
        <h3> Фотографий нет </h3>
    </#list>
    <br>
    <b> Название товара: </b>${product.title}<br>
    <b> Описание товара: </b>${product.description}<br>
    <b> Цена: </b>${product.price} руб.<br>
    <b> Город: </b>${product.city}<br>

    <#if user.name??>
        <b> Имя: </b> <a href="/user/${product.user.id}"> ${product.getUser().name} </a> <br>
        <b> Номер телефона: </b>${product.user.getPhoneNumber()}<br><br>
        <#if user.id==productUserId>
            <a href="/products/${product.id}/product-edit"> Редактировать информацию о товаре </a>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <br><br>
            <form action="/products/delete/${product.id}" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Удалить товар"/>
            </form>
        </#if>
        <hr>

    <#else >
        <b> Номер телефона: </b> Для просмотра скрытой информации <a href="/login">зайдите в аккаунт</a><br>
        <br>
        <hr>
    </#if>
</#if>

</body>
</html>