// JS pre zobrazenie textu poznámky po kliknutí na poznámku
var div = document.getElementById("sendToJS");	// Príjmanie hodnoty $counter z main.php -> počet zobrazených poznámok na webstránke
var count = div.textContent;

var classCounter = '.n';
var popUpCounter = '.p';
var closeCounter = '.c';
var counter = 0;

var classArray = [];
var popUpArray = [];
var closeArray = [];

for(let i = 0; i < count; i++){	// Generovanie Class, ktoré sú od seba rozdielne, ale každé dve sú zhodné s Classami na main.php
	classCounter = classCounter + counter;
	popUpCounter = popUpCounter + counter;
	closeCounter = closeCounter + counter;
	counter += 1;

	classArray.push(classCounter);	// Ukladanie do Array listu
	popUpArray.push(popUpCounter);
	closeArray.push(closeCounter);
}

for(let i = 0; i < count; i++){	// Pridanie Eventov k jednotlivým poznámkam
	document.querySelectorAll(classArray[i]).forEach(item => {item.addEventListener("click", function() {	// Po kliknutí na poznámku -> zobrazí text poznámky
		document.querySelector(popUpArray[i]).style.display = "flex";
		})
	});
	
	document.querySelectorAll(closeArray[i]).forEach(item => {item.addEventListener("click", function() {	// Po kliknutí na 'X' v texte poznámky -> zobrazí schová text poznámky
		document.querySelector(popUpArray[i]).style.display = "none";
		})
	});
}