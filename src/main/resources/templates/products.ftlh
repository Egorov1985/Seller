<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1><a href="http://localhost:8080">BUYSELL</a></h1>
<hr>
<#if user??>
<h3 Имя пользователя> ${user.name}</h3>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Выйти">
</form>
<#if user.isAdmin()>
    <br>
    <a href="/admin"> Панель администратора</a>
</#if>
<#else >
<a href="/login"> Войти <a/>
    </#if>
    <hr>
    <h4>Товары со всей России</h4>
    <form action="/products" method="get">
        Поиск по названию объявления: <input type="text" name="title"><br><br>
        <input type="submit" value="Поиск"/>
    </form>

    <#list products as product>
        <div>
            <#if product.getPreviewImage()??>
                <p><img src="/products/${product.id}/previewImage" height="60px"/> ${product.title}  ${product.price}
                    руб.
                    | <a href="/products/${product.id}">Подробнее...</a></p><br>
            <#else >
                <p><img src="/product/defaultImage"/> ${product.title}  ${product.price} руб.
                    | <a href="/products/${product.id}">Подробнее...</a></p><br>
            </#if>
        </div>
    <#else>
        <h3>Товаров нет </h3>
    </#list>
    <#if user??>
        <a href="/products/new"> Создать новое объявление </a>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </#if>
</body>
<hr>
</html>