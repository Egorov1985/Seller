<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL </h1>
<hr>
     <#if user.email??>
           <h3 Имя пользователя> ${user.name}</h3>
           <form action="/logout" method="post">
               <input type="hidden" name="_csrf" value="${_csrf.token}">
               <input type="submit" value="Выйти">
           </form>
          <#if user.isAdmin()>
              <a href="/admin"> Панель администратора</a>
          </#if>
     <#else >
          <a href="/login"> Войти <a/>
     </#if>
    <hr>
    <h4>Товары со всей России</h4>
    <form action="/" method="get">
        Поиск по названию объявления: <input type="text" name="title"><br><br>
        <input type="submit" value="Поиск"/><br><br>
    </form>
    <#list products as product >
        <div>
            <#if product.previewImageId?has_content>
                <p><img src="/images/${product.previewImageId}" height="50px"/> ${product.title}  ${product.price} руб.
                    | <a href="/product/${product.id}">Подробнее...</a></p><br>
            <#else >
                <p><img src="/product/defaultImage"/> ${product.title}  ${product.price} руб.
                    | <a href="/product/${product.id}">Подробнее...</a></p><br>
            </#if>
        </div>
    <#else>
        <h3>Товаров нет </h3>
    </#list>
    <#if user.email??>
        <a href="/product/new"> Создать новое объявление </a>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </#if>
</body>
<hr>
</html>