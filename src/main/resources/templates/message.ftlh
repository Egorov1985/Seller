<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ByuSell</title>
    <script src="../js/sockjs-0.3.4.js"></script>
    <script src="../js/stomp.js"></script>
    <script src="/static/public/js/app.js">
    </script>
</head>
<body>
<h1>BuySell</h1>
<hr>
<h2>Сообщения</h2>

<#if product.previewImageId?has_content>
    <p><img src="/images/${product.previewImageId}" height="50px"/> ${product.title}  ${product.price} руб.
        | <a href="/product/${product.id}">Подробнее...</a></p><br>
<#else >
    <p><img src="/product/defaultImage"/> ${product.title}  ${product.price} руб.
        | <a href="/product/${product.id}">Подробнее...</a></p><br>
</#if></p>

<onload="disconnect();">

<div>
    <div>
        <input type="text" id="from" placeholder="Choose a nickname"/>
    </div>
    <br/>
    <div>
        <button id="connect" onclick="connect();">Connect</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">
            Disconnect
        </button>
    </div>
    <br/>
    <div id="conversationDiv">
        <input type="text" id="text" placeholder="Write a message..."/>
        <button id="sendMessage" onclick="sendMessage();" >Send</button>
        <script src="../js/clear-input-form.js"></script>
        <p id="response"></p>
    </div>
</div>
<form action="">
    <input type="text" name="message">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" name="Отправить сообщение">
</form>



</>
</html>