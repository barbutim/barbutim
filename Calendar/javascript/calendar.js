if(localStorage.getItem("dateSet") === null){ // Ak v localStorage 'dateSet' nie je uložená hodnota -> vygeneruje aktuálny čas pre date
  var date = new Date();
}

else{ // Ak v localStorage 'dateSet' je uložená hodnota -> zapíš tento čas pre date
  var date = localStorage.getItem("dateSet");
  date = new Date(date);
}

const months = [  // Mesiace po slovensky
  "Január",
  "Február",
  "Marec",
  "Apríl",
  "Máj",
  "Jún",
  "Júl",
  "August",
  "September",
  "Október",
  "November",
  "December",
];

var nextDate = 0;
var check = 0;
var clickedDate = 0;
var savedMonth = 0;
var savedYear = 0;
var rawMonth = 0;
var savedRawMonth = 0;
var saved = (function(){  // Funkcia, ktorá za zavolá iba 1x -> pridá do savedMonth, aktuálny mesiac
  var executed = false;
  return function(){
    if(!executed){
        executed = true;
        savedMonth = months[new Date().getMonth()];
    }
  };
})();
saved();

const calendar = () => {  // Funkcia pre kalendár
  date.setDate(1);  // Nastavenie dátumu
  
  const monthDays = document.querySelector(".numberOfDays");

  const lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate(); //posledný deň v aktuálnom mesiaci -> Output: ćíslo dňa

  const prevLastDay = new Date(date.getFullYear(), date.getMonth(), 0).getDate(); // Posledný deň v predchádzajúcom mesiaci -> Output: číslo dňa

  const firstDayIndex = date.getDay() - 1;  // ID prvého dňa v mesiaci (0 | pondelok -> 6 | nedeľa)
  
  if(firstDayIndex == '-1')
    var firstDayIndexX = firstDayIndex.toString().replace('-1', '6');

  else
    var firstDayIndexX = firstDayIndex

  const lastDayIndex = new Date(date.getFullYear(), date.getMonth() + 1, 0).getDay() - 1; // ID posledného dňa v mesiaci (0 | pondelok -> 6 | nedeľa)
  
  if(lastDayIndex == '-1')
    var lastDayIndexX = lastDayIndex.toString().replace('-1', '6');

  else
    var lastDayIndexX = lastDayIndex

  const nextDays = 7 - lastDayIndexX - 1; // Počet pridaných dní z ďalšieho mesiaca

  if(localStorage.getItem("clickedOnDateStored") === null)  // Ak v localStorage 'clickedOnDateStored' nie je uložená hodnota
    clickedOnDate = 0;

  else
    clickedOnDate = localStorage.getItem("clickedOnDateStored");  // Predchádzajúca hodnota zakliknutého dátumu -> vložená do clickedOnDate

  if(nextDate != clickedOnDate){  // Ak je predchádzajúci deň zadaný, znamená že je iný ako 0 -> opätovné načítanie kalendára
    todaysYear = date.getFullYear();
    savedYear = todaysYear;
  }

  else{ // Ak je predchádzajúci deň == 0 -> prvotné načítanie kalendára
    if(savedYear == 0){ // Ak je predchádzajúci Rok == 0 -> prvotné načítanie kalendára
      todaysYear = date.getFullYear();
    }

    else{ // Ak je predzádzajúci rok zadaný, znamená že je iný ako 0 -> opätovné načítanie kalendára
      todaysYear = savedYear;
    }
  }
  
  if(months[date.getMonth()] != savedMonth){  // Ak je mesiac zhodný s uloženým mesiacom...
    if(nextDate != clickedOnDate){  // Ak je deň zhodný s uloženým dňom...
      todaysMonth = months[date.getMonth()];
      savedMonth = todaysMonth;
      rawMonth = date.getMonth();
      savedRawMonth = rawMonth;
    }

    else{
      todaysMonth = savedMonth;
      rawMonth = savedRawMonth;
    }
  }

  else{
    var todaysMonth = months[date.getMonth()];
    savedMonth = todaysMonth;
    rawMonth = date.getMonth();
    savedRawMonth = rawMonth;
  }

  if(localStorage.getItem("clickDate") !== null)  // Ak v localStorage 'clickedDate' je uložená hodnota
    clickedDate = localStorage.getItem("clickDate");  // Uložená hodnota po predchádzajúcom zakliknutí dátumu
    
  if(clickedOnDate == 0){ // Po prvotnom načítaní stránky main.php, do premennej todaysDay -> vlož aktuálny dátum
    var todaysDay = new Date().getDate();
  }

  else{ // Po opakovaných načítaniach predchádzajúcu hodnotu ukladá a mení sa na základe nej
    todaysDay = clickedDate;
    nextDate = clickedOnDate;
  }

  todaysDate = todaysDay + '. ' + todaysMonth + ' ' + todaysYear;

  var thisMonth = months[date.getMonth()];  // Aktuálne zakliknutý mesiac a rok (slovenský preklad)
  var thisYear = date.getFullYear();
  thisMonthYear = thisMonth + ' ' + thisYear;

  document.querySelector(".currentDate h2").innerHTML = thisMonthYear;  // Aktuálne zakliknutý mesiac a rok -> zobrazenie
  document.querySelector(".currentDate p").innerHTML = todaysDate;  // Aktuálne zakliknutý dátum -> zobrazenie

  var split = todaysDate.split(" ");  // Rozdelenie dátumu na menšie časti, aby ho bolo ďalej možné použiť
  var day = split[0].slice(0, -1);
  var month = rawMonth + 1;
  var year = split[2];
  var zero = 0;

  if(day.length == 1){  // Ak je dĺžka údaju dňa jednociferná (napr. 4), pridaj na začiatok 0, aby tento údaj bolo možné použiť na main.php a addForm.php -> 04
    day = zero + day;
  }

  month = month.toString();

  if(month.length == 1){  // Ak je dĺžka údaju mesiaca jednociferná (napr. 6), pridaj na začiatok 0, aby tento údaj bolo možné použiť na main.php a addForm.php -> 06
    month = zero + month;
  }

  $.ajax({  // Posielanie dátumu cez AJAX -> _getValuesFromJS.php -> main.php
    url: "includes/_getValuesFromJS.php",
    method: "POST",
    data: {
      'dayVal': day,
      'monthVal': month,
      'yearVal': year
    }
  });

  var days = "";

  for(var x = firstDayIndexX; x > 0; x--){  // Zobrazenie dní z predchádzajúceho mesiaca
    days += `<a class="extraDays">${prevLastDay - x + 1}</a>`;
  }

  for(var i = 1; i <= lastDay; i++){  // Zobrazenie dní z aktuálneho mesiaca
    if(i === new Date().getDate() && date.getMonth() === new Date().getMonth() && date.getFullYear() === new Date().getFullYear()) //zobrazenie aktuálneho dňa
      days += `<a id="${i}" onClick = "dateClick(this.id)" class="todaysDay">${i}</a>`; // Dnešný dátum je zvýraznený
    
      else
      days += `<a id="${i}" onClick = "dateClick(this.id)">${i}</a>`;
  }

  for(var j = 1; j <= nextDays; j++){ // Zobrazenie dní z nasledujúceho mesiaca
    days += `<a class="extraDays">${j}</a>`;
  }

  monthDays.innerHTML = days;
  localStorage.setItem("dateSet",date);
}

calendar();

window.onload = function(){
  if(!window.location.hash){  // Po prvotnom načítaní stránky -> reloadni stránku ešte raz
    window.location = window.location + '#loaded';
    window.location.reload();
  }
}

function dateClick(clicked){  // Po kliknutí na dátum v kalendáry na main.php
  clickedDate = clicked;
  check = parseInt(clickedOnDate);
  check = check + 1;
  
  localStorage.setItem("clickedOnDateStored", check); // Ukladanie hodnôt do localStorage
  localStorage.setItem("clickDate", clickedDate);

  calendar(); 
  location.reload();
}    

document.querySelector(".previousMonth").addEventListener("click", () => { // Po kliknutí na šípku pre zmenu mesiaca na main.php
  date.setMonth(date.getMonth() - 1); // -> prechod na predchádzajúci mesiac
  calendar();
});

document.querySelector(".nextMonth").addEventListener("click", () => { // Po kliknutí na šípku pre zmenu mesiaca na main.php
  date.setMonth(date.getMonth() + 1); //  -> prechod na nasledujúci mesiac
  calendar();
});