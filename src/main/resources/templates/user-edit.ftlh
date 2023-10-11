<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1><a href="http://localhost:8080">BUYSELL</a></h1><hr>
<h4> Редактирование пользователя ${user.name}</h4>
<form action="/admin/user/edit" method="post">
    <#list roles as role>
        <div>
            <label> <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}> ${role}</label>
        </div>
    </#list>
    <input type="hidden" value="${user.id}" name="userId">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Сохранить"/>

</form>
</body>
</html>