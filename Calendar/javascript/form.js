function validate(event){   // JS pre validovanie addForm.php
    var inputDate = document.querySelector('#date');
    var inputTime = document.querySelector('#time');
    var inputTitle = document.querySelector('#title');
    var inputText = document.querySelector('#text');

    if(inputDate.value.length != 10 ){
        event.preventDefault();
        inputDate.parentNode.classList.add('error', 'dateErr');
    } 

    else{
        inputDate.parentNode.classList.remove('error', 'dateErr');
    }

    if(inputTime.value.length != 5 ){
        event.preventDefault();
        inputTime.parentNode.classList.add('error', 'timeErr');
    }

    else{
        inputTime.parentNode.classList.remove('error', 'timeErr');
    }

    if(inputTitle.value.length < 5 || inputTitle.value.length > 30){
        event.preventDefault();
        inputTitle.parentNode.classList.add('error', 'titleErr');
    }

    else {
        inputTitle.parentNode.classList.remove('error', 'titleErr');
    }

    if(inputText.value.length >= 150){
        event.preventDefault();
        inputText.parentNode.classList.add('error', 'textErr');
    }

    else {
        inputText.parentNode.classList.remove('error', 'textErr');
    }
}

function init(){    // Volanie funkcie pre validáciu
    var formular = document.querySelector('body>article>form');
    formular.addEventListener('submit', validate);
}

document.addEventListener('DOMContentLoaded', init);