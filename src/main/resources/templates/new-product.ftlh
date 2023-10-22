<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<h1><a href="http://localhost:8080">BUYSELL</a></h1><hr>
<h4> Создание нового товара </h4>
<#if imageAddError??>
    <b style="color: red"> ${imageAddError} </b>
</#if>

<form action="/products/create" method="post" enctype="multipart/form-data">
    Название объявления: <input type="text" name="title" value="${(product.title)!""}" required/> <br><br>
    Описание объявления: <input type="text" name="description" value="${(product.description)!""}" /><br><br>
    Цена: <input type="text" name="price" value="${(product.price)!""}"  required/><br><br>
    Город: <input type="text" name="city" value="${(product.city)!""}" required/><br><br>
    <p>Загрузите фотографии вашего товара</p>
    <p><input type="file" name="file" multiple accept="image/*,image/jpeg"></p> <br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Добавить товар"/>
</form>

<body></body>
</html>