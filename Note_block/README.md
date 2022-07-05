# KAJ - semestral

Cieľom bolo vytvoriť poznámkový blok, tzv. TODO list ako semestrálnu prácu predmetu KAJ.

### Obsah webu a funkcionalita:
V hornej časti sa nachádza nádpis a uživateľský vstup pre zadanie nových poznámok. Užívateľ môže taktiež zaškrtnúť checkbox tlačidlo na ľavej strane vedľa input, tým sa po pridaní poznámky automaticky nastaví jej stav tak ako to zadal uživateľ.

Pridanie poznámky je možné buď pomocou tlačidla na pravej strane vedľa inputu alebo po stlačení enteru. V oboch prípadoch je potrebné aby text poznámky nebol prázdny. Po pridaní poznámky sa prehraje hudba.

V dolnej časti sú zobrazené poznámky, naľavo je opäť zobrazený ich stav pomocou checkboxov a na pravej strane animovaná ikonka koša, ktorá odstráni danú poznámku. Po jej odstránení sa taktiež prehraje hudba.

Po kliknutí na text konkrétnej poznámky sa zobrazí okno s detailom poznámky. Toto okno sa dá zatvoriť kliknutím na tlačidlo napravo hore.

V prípade ak má uživateľ zobrazené okno s detailom konkrétnej poznámky a zároveň túto poznámku vymaže, okno s detailom poznámky sa zatvorí, poznámka sa vymaže a zobrazí sa animovaný SVG smajlík.

### Implementované požiadavky:
#### Všetky povinné požiadavky:
- Dokumentácia
- Validita
- Semantické značky
- Pokročilé selektory
- CSS3 transitions/animations
- OOP prístup
- Použitie pokročilých JS API: LocalStorage

#### Nepovinné požiadavky:
- Validita v moderných prehliadačoch
- Grafika: SVG
- Média: Audio
- Formulárové prvky
- Vendor prefixy
- CSS3 transformácie
- Ovládanie medií

### Neimplementované požiadavky:
- Offline aplikácia HTML
- Media queries
- Použitie JS frameworku
- Funkčná história
- Offline aplikácia JS
- JS práca s SVG