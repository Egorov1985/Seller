<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1> BUYSELL </h1><hr>
<a href="http://localhost:8080"> Вернуться на главную страницу </a>
<h4> Подробная информация о товаре </h4>

<b> Название товара: </b>${product.title}<br>
<b> Описание товара: </b>${product.description}<br>
<b> Цена: </b>${product.price} руб.<br>
<b> Город: </b>${product.city}<br>
<b> Имя: </b>${product.author}<br>
<#list images as img>
    <img src="/images/${img.id}" height="150px"/><br><br>
<#else >
    <h3> Фотографий нет </h3>
</#list>
<a href="/product/${product.id}/product-edit" > Редактировать </a>

<hr>
<form action="/product/delete/${product.id}" method="post">
    <input type="submit" value="Удалить товар"/>
</form>
</body>
</html>