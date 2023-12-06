<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1><a href="http://localhost:8080">BUYSELL</a></h1>
<hr>
<h4> Редактирование информация о товаре </h4>
<#if user.email??>
<#list images as img>
    <img src="/products/${product.id}/image/${img_index}" height="120px"/>
<#else >
    <h3> Фотографий товара нет </h3>
</#list>
<br><br>
<#if product.previewImage??>
    <form action="/products/${product.id}/images/delete" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Удалить все фотографии товара">
    </form>
    <br>
</#if>
<form action="/products/${product.id}/update" method="post" enctype="multipart/form-data">
    Название объявления: <input type="text" name="title" value="${product.title}" required/> <br><br>
    Описание объявления: <input type="text" name="description" value="${product.description}" required/><br><br>
    Цена: <input type="text" name="price" value="${product.price}" руб. required/><br><br>
    Город: <input type="text" name="city" value="${product.city}" required/><br><br>
    <p>Добавить фотографии товара</p>
    <p><input type="file" name="file" multiple accept="image/*,image/jpeg"/></p> <br>
    <input type="hidden" name="id" value="${product.id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="submit" value="Сохранить изменения"/>
</form>
<br><br>
<#else >
<a href="/login"> Войти <a/>
    </#if>
</body>
</html>