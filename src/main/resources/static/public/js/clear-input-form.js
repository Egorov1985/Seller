const btn = document.getElementById('sendMessage');

btn.addEventListener('click', function handleClick(event) {

    event.preventDefault();

    const inputText = document.getElementById('text');

    console.log(inputText.value);

    inputText.value = '';
});