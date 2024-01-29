var input = document.querySelectorAll('.input'),
    buffer = [];
for (var i = 0; input.length > i; i++) {
    console.log(input[i].value);
    buffer[i] = document.createElement('div');
    buffer[i].className = "buffer";
    //вставляем скрытый div.buffer
    input[i].parentNode.insertBefore(buffer[i], input[i].nextSibling);

    input[i].oninput = function() {
        this.nextElementSibling.innerHTML = this.value;
        this.style.width = this.nextElementSibling.clientWidth + 'px';
    };
}
