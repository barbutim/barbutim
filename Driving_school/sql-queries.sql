/* 1 - Vyber čas, ŠPZ auta, priezvisko študenta a priezvisko učiteľa, ktorý má jazdu dňa 10.4.2021 */
SELECT cas, statnaPoznavaciaZnacka, CS.priezvisko, CU.priezvisko
FROM jazda AS J
JOIN auto AS A ON (J.auto = A.registracneCislo)
JOIN student AS S ON (J.student = S.cisloPrihlasky)
JOIN ucitel AS U ON (J.ucitel = U.cisloUcitela)
JOIN clovek AS CS ON (S.rodneCislo = CS.rodneCislo)
JOIN clovek AS CU ON (U.rodneCislo = CU.rodneCislo)
WHERE (J.datum = '2021-04-10');
/* 2 - Vyber meno, priezvisko, rodné čislo, ulicu a mesto študenta, ktorý sa účastní teórie dňa 5.4.2021 o 18:00. Výsledok zoraď podľa prieviska. */
SELECT meno, priezvisko, rodneCislo, ulica, mesto
FROM ucastnici AS U
JOIN student AS S ON (U.student = S.cisloPrihlasky)
NATURAL JOIN clovek
WHERE (U.datum = '2021-04-05') AND (U.cas = '18:00')
ORDER BY priezvisko;
/* 3 - Vyber dátum, čas a počet účastníkov teórie, teda študentov, ak je na teóriu prihlásených maximálne 20 študentov. Výsledok zoraď podľa dátumu. */
SELECT datum, cas, COUNT(student) AS pocetUcastnikov
FROM ucastnici
GROUP BY datum, cas
HAVING (COUNT(student) <= 20)
ORDER BY datum;
/* 4 - Vyber dátum a čas jazdy a ŠPZ auta, ktoré majú jazdy naplánované dňa 10.4.2021 a dňa 11.4.2021 */
SELECT datum, cas, statnaPoznavaciaZnacka
FROM jazda
LEFT OUTER JOIN auto ON (auto = registracneCislo)
WHERE (datum = '2021-04-10')
UNION
SELECT datum, cas, statnaPoznavaciaZnacka
FROM jazda
LEFT OUTER JOIN auto ON (auto = registracneCislo)
WHERE (datum = '2021-04-11');
/* 5 - Vyber dátum a čas jazdy, ktoré sú jazdené autom so ŠPZ - KE356UI */
SELECT datum, cas
FROM jazda
WHERE
EXISTS (
	SELECT *
	FROM auto
	WHERE (auto = registracneCislo) AND (statnaPoznavaciaZnacka = 'KE356UI')
);
/* 6 - Vyber meno, priezvisko a email všetkých učiteľov. Výsledok zoraď podľa priezviska. */
SELECT C.meno, C.priezvisko, U.email
FROM ucitel AS U
INNER JOIN clovek AS C ON (U.rodneCislo = C.rodneCislo)
ORDER BY C.priezvisko;
