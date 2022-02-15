function validate(event){   // JS pre validovanie prihlasovacích údajov
    var inputName = document.querySelector('#name');
    var inputPassword = document.querySelector('#password');

    if(inputName.value.length < 5 || inputName.value.length > 20){
        event.preventDefault();
        inputName.parentNode.classList.add('error', 'nameErr');
    } 

    else{
        inputName.parentNode.classList.remove('error', 'nameErr');
    }

    if(inputPassword.value.length < 5 || inputPassword.value.length > 20){
        event.preventDefault();
        inputPassword.parentNode.classList.add('error', 'passwordErr');
    }
    else{
        inputPassword.parentNode.classList.remove('error', 'passwordErr');
    }
}

function init(){    // Volanie funkcie pre validáciu
    var formular = document.querySelector('body>article>form');
    formular.addEventListener('submit', validate);
}

document.addEventListener('DOMContentLoaded', init);