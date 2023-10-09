<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1> BUYSELL </h1>
<a href="http://localhost:8080/product/products"> Вернуться на главную страницу </a>
<hr>
<#if productException??>
    <b>Omg... ${productException}</b>
    <#else >
        <h4> Подробная информация о товаре </h4>

        <b> Название товара: </b>${product.title}<br>
        <b> Описание товара: </b>${product.description}<br>
        <b> Цена: </b>${product.price} руб.<br>
        <b> Город: </b>${product.city}<br>


        <#if user.email??>
            <b> Имя: </b> <a href="/user/${product.user.id}"> ${product.getUser().name} </a> <br>
            <b> Номер телефона: </b>${product.user.getPhoneNumber()}<br><br>

            <br><br>
            <#list images as img>
                <img src="/product/${product.id}/image/${img_index}" height="120px"/>
            <#else >
                <h3> Фотографий нет </h3>
            </#list>
            <br>
            <#if user.id==productUserId>
                <a href="/product/${product.id}/product-edit"> Редактировать </a>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <br>
                <br>
                <form action="/product/delete/${product.id}" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Удалить товар"/>
                </form>
            </#if>
            <hr>

        <#else >
            <b> Номер телефона: </b> Для просмотра скрытой информации <a href="/login">зайдите в аккаунт</a><br>
            <#list images as img>
                <img src="/product/${product.id}/image/${img_index}" height="120px"/>
            <#else >
                <h3> Фотографий нет </h3>
            </#list>
            <br>
            <hr>
        </#if>
</#if>

</body>
</html>