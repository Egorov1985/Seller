<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1> BUYSELL </h1>
<a href="http://localhost:8080"> Вернуться на главную страницу </a>
<hr>

<h4> Подробная информация о товаре </h4>

<b> Название товара: </b>${product.title}<br>
<b> Описание товара: </b>${product.description}<br>
<b> Цена: </b>${product.price} руб.<br>
<b> Город: </b>${product.city}<br>
<b> Имя: </b> <a href="/user/${product.user.id}"> ${product.getUser().name} </a> <br>
<#if user.email??>
    <b> Номер телефона: </b>${product.user.getPhoneNumber()}<br>
    <#list images as img>
        <img src="/images/${img.id}"  height="120px"/>
    <#else >
        <h3> Фотографий нет </h3>
    </#list>
    <br>

    <#if user.id==productUserId>
        <a href="/product/${product.id}/product-edit" > Редактировать </a>
    </#if>
    <hr>
    <#else >
        <b> Номер телефона: </b> Для просмотра номера телефона войти в аккаунт<br>
        <#list images as img>
            <img src="/images/${img.id}"  height="120px"/>
        <#else >
            <h3> Фотографий нет </h3>
        </#list>
        <br>
        <a href="/login"> Войти в аккаунт</a>
</#if>
</body>
</html>