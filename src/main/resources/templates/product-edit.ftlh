<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1> BUYSELL </h1><hr>
<h4> Редактирование информация о товаре </h4>
<form action="/product/${product.id}/update" method="post"  enctype="multipart/form-data" />
    Название объявления: <input type="text" name="title" value="${product.title}"  required/><br><br>
    Описание объявления: <input type="text" name="description" value="${product.description}" required /><br><br>
    Цена: <input type="number" name="price" value="${product.price}" min="0" required/><br><br>
    Город: <input type="text" name="city" value="${product.city}" required/><br><br>
    Имя: <input type="text" name="author" value="${product.author}" required /><br><br>
    <#list images as img>
        <img src="/images/${img.id}" height="60px" /><br><br>
        <form method="post" enctype="multipart/form-data">
    <#else >
        <h3> Фотографий нет </h3>
    </#list>
    <input type="submit" value="Сохранить изменения"/>
</form>
</body>
</html>