function myFunction(){
  var x=document.getElementById("tlacidlo");
  x.style.display = "none";
var cardsArray = [{
  'name': '1',
  'img': '1.png'
}, {
  'name': '2',
  'img': '2.png'
}, {
  'name': '3',
  'img': '3.png'
}, {
  'name': '4',
  'img': '4.png'
}, {
  'name': '5',
  'img': '5.png'
}, {
  'name': '6',
  'img': '6.png'
}, {
  'name': '7',
  'img': '7.png'
}, {
  'name': '8',
  'img': '8.png'
}];
var gameGrid = cardsArray.concat(cardsArray).sort(function () {
  return 0.5 - Math.random();
});
var firstGuess = '';
var secondGuess = '';
var count = 0;
var previousTarget = null;
var delay = 700;
var game = document.getElementById('game');
var grid = document.createElement('section');
grid.setAttribute('class', 'grid');
game.appendChild(grid);

gameGrid.forEach(function (item) {
  var name = item.name,
      img = item.img;
  var card = document.createElement('div');
  card.classList.add('card');
  card.dataset.name = name;

  var front = document.createElement('div');
  front.classList.add('front');

  var back = document.createElement('div');
  back.classList.add('back');
  back.style.backgroundImage = 'url(' + img + ')';

  grid.appendChild(card);
  card.appendChild(front);
  card.appendChild(back);
});

var body=0;
var pocet=8;

var match = function match() {
  if(pocet==0){
        alert("Gratulujem vyhral si! Počet ťahov: "+body);
      }
  var selected = document.querySelectorAll('.selected');
  selected.forEach(function (card) {
    card.classList.add('match');
  });
};

var resetGuesses = function resetGuesses() {
  firstGuess = '';
  secondGuess = '';
  count = 0;
  previousTarget = null;
  var selected = document.querySelectorAll('.selected');
  selected.forEach(function (card) {
    card.classList.remove('selected');
  });
};

grid.addEventListener('click', function (event) {
  console.log(""+body);
  var clicked = event.target;
  if (clicked.nodeName === 'SECTION' || clicked === previousTarget || clicked.parentNode.classList.contains('selected') || clicked.parentNode.classList.contains('match')) {
    return;
  }

  if (count < 2) {
    count++;
    if (count === 1) {
  body++;
      firstGuess = clicked.parentNode.dataset.name;
      console.log(firstGuess);
      clicked.parentNode.classList.add('selected');
    } else {
      secondGuess = clicked.parentNode.dataset.name;
      console.log(secondGuess);
      clicked.parentNode.classList.add('selected');
    }

    if (firstGuess && secondGuess) {
      if (firstGuess === secondGuess) {
        setTimeout(match, delay);
        pocet--;
        console.log(""+pocet);
      }
      setTimeout(resetGuesses, delay);
    }
    previousTarget = clicked;
}
});
}
