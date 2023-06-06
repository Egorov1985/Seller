<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL | <a href="/login"> Войти </a></h1>
<hr>
<h4>Товары со всей России</h4>
<form action="/" method="get">
    Поиск по названию объявления: <input type="text" name="title"><br><br>
    <input type="submit" value="Поиск"/><br><br>
</form>
<#list products as product >
    <div>
        <#if product.previewImageId?has_content>
            <p> <img src="/images/${product.previewImageId}" height="50px"/> ${product.title}  ${product.price} руб.
                | <a href="/product/${product.id}">Подробнее...</a></p><br>
            <#else >
               <p>  <img src="/product/defaultImage" />  ${product.title}  ${product.price} руб.
                | <a href="/product/${product.id}">Подробнее...</a></p><br>
        </#if>
    </div>
<#else>
    <h3>Товаров нет </h3>
</#list>

<a href="/product/new"> Создать новое объявление </a>
<hr>
</body>
</html>