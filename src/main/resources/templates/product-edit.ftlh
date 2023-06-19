<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1> BUYSELL </h1>
<hr>
<h4> Редактирование информация о товаре </h4>
<#if user.email??>
<form action="/product/${product.id}/update" method="post" enctype="multipart/form-data">
    Название объявления: <input type="text" name="title" value="${product.title}" required/> <br><br>
    Описание объявления: <input type="text" name="description" value="${product.description}" required/><br><br>
    Цена: <input type="number" name="price" value="${product.price}" required/><br><br>
    Город: <input type="text" name="city" value="${product.city}" required/><br><br>
    <#list images as img>
        <img src="/images/${img.id}" height="120px"/>
    <#else >
        <h3> Фотографий товара нет </h3>

    </#list>
    <br>
    <p>Добавить фотографии товара</p>
    <p><input type="file" name="file" multiple accept="image/*,image/jpeg"/></p> <br>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="submit" value="Сохранить изменения"/>
</form>
<br><br>

<form action="/product/images/delete/${id}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Удалить все фотографии товара">
</form>
<#else >
<a href="/login"> Войти <a/>
    </#if>
</body>
</html>