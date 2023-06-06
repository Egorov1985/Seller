<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1> BUYSELL </h1>
<hr>
<h4> Редактирование информация о товаре </h4>
<form action="/product/${product.id}/update" method="post" enctype="multipart/form-data"/>
Название объявления: <input type="text" name="title" value="${product.title}" required/><br><br>
Описание объявления: <input type="text" name="description" value="${product.description}" required/><br><br>
Цена: <input type="number" name="price" value="${product.price}" руб. min="0" required/><br><br>
Город: <input type="text" name="city" value="${product.city}" required/><br><br>
Имя: <input type="text" name="author" value="${product.author}" required/><br><br>
<#list images as img>
    <img src="/images/${img.id}" height="120px"/>
<#else >
    <h3> Фотографий товара нет </h3>
</#list>
<br>

<button>
    <a href="/product/images/delete/${product.id}" > Удалить все фотографии товара </a>
</button>
<hr>
<p>Добавьте фотографии вашего товара</p>
<p><input type="file" name="file" multiple accept="image/*,image/jpeg"> <br><br>
    <input type="submit" value="Сохранить изменения"/> </p>
    <br>
    </form>
</body>
</html>