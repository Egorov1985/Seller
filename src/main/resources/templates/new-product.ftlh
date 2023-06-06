<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1> BUYSELL </h1><hr>
<h4> Создание нового товара </h4>
<form action="/product/create" method="post" enctype="multipart/form-data">
    Название объявления: <input type="text" name="title" required/> <br><br>
    Описание объявления: <input type="text" name="description" required/><br><br>
    Цена: <input type="number" name="price" required/><br><br>
    Город: <input type="text" name="city" required/><br><br>
    Имя: <input type="text" name="author" required/><br><br>
    <p>Загрузите фотографии вашего товара</p>
    <p><input type="file" name="file" multiple accept="image/*,image/jpeg"> <br><br>
        <input type="submit" value="Добавить товар"/>
</form>
</body>
</html>