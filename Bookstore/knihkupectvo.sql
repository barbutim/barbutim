DROP DATABASE IF EXISTS knihkupectvo;
CREATE DATABASE knihkupectvo CHARACTER SET utf8 COLLATE utf8_SLOVAK_ci;

USE knihkupectvo;

CREATE TABLE vydavatelstvo(
	IDvydavatelstvo int AUTO_INCREMENT PRIMARY KEY,
    vydavatelstvo varchar(30) NOT NULL
)ENGINE = InnoDB;

CREATE TABLE zaner(
	IDzaner int AUTO_INCREMENT PRIMARY KEY,
    zaner varchar(30) NOT NULL
)ENGINE = InnoDB;

CREATE TABLE rok(
	IDrok int AUTO_INCREMENT PRIMARY KEY,
    rok int NOT NULL
)ENGINE = InnoDB;

CREATE TABLE hodnotenie(
	IDhodnotenie int AUTO_INCREMENT PRIMARY KEY,
    hodnotenie int NOT NULL
)ENGINE = InnoDB;

CREATE TABLE kniha(
	IDkniha int AUTO_INCREMENT PRIMARY KEY,
    IDvydavatelstvo int NOT NULL,
    IDzaner int NOT NULL,
    nazov varchar(30) NOT NULL,
    cena float(4,2) NOT NULL,
    naSklade int NOT NULL,
    IDrok int NOT NULL,
    IDhodnotenie int NOT NULL,
    FOREIGN KEY (IDvydavatelstvo) REFERENCES vydavatelstvo (IDvydavatelstvo),
    FOREIGN KEY (IDzaner) REFERENCES zaner (IDzaner),
    FOREIGN KEY (IDrok) REFERENCES rok (IDrok),
    FOREIGN KEY (IDhodnotenie) REFERENCES hodnotenie (IDhodnotenie)
)ENGINE = InnoDB;

CREATE TABLE autor(
	IDautor int AUTO_INCREMENT PRIMARY KEY,
    meno varchar(30) NOT NULL,
    priezvisko varchar(30) NOT NULL
)ENGINE = InnoDB;

CREATE TABLE autorKniha(
	IDautor int NOT NULL,
    IDkniha int NOT NULL,
    PRIMARY KEY (IDautor,IDkniha),
    FOREIGN KEY (IDautor) REFERENCES autor (IDautor),
    FOREIGN KEY (IDkniha) REFERENCES kniha (IDkniha)
)ENGINE = InnoDB;

CREATE TABLE mesto(
	IDmesto int AUTO_INCREMENT PRIMARY KEY,
    mesto varchar(30) NOT NULL
)ENGINE = InnoDB;

CREATE TABLE zakaznik(
	IDzakaznik int AUTO_INCREMENT PRIMARY KEY,
	meno varchar(30) NOT NULL,
    priezvisko varchar(30) NOT NULL,
    ulica varchar(30) NOT NULL,
    cislo int NOT NULL,
    IDmesto int NOT NULL,
    kontakt varchar(13) NOT NULL,
    FOREIGN KEY (IDmesto) REFERENCES mesto (IDmesto)
)ENGINE = InnoDB;

CREATE TABLE platba(
	IDplatba int AUTO_INCREMENT PRIMARY KEY,
    platba varchar(30) NOT NULL
)ENGINE = InnoDB;
   
CREATE TABLE doprava(
	IDdoprava int AUTO_INCREMENT PRIMARY KEY,
    doprava varchar(30) NOT NULL
)ENGINE = InnoDB;
   
CREATE TABLE stav(
	IDstav int AUTO_INCREMENT PRIMARY KEY,
    stav varchar(1) NOT NULL
)ENGINE= InnoDB;

CREATE TABLE objednavka(
	IDobjednavka int AUTO_INCREMENT PRIMARY KEY,
    IDzakaznik int NOT NULL,
    datum date NOT NULL,
    pocet int NOT NULL,
    celkovaCena float(7,2) NOT NULL,
    IDplatba int NOT NULL,
    IDdoprava int NOT NULL,
    IDstav int NOT NULL,
    FOREIGN KEY (IDzakaznik) REFERENCES zakaznik (IDzakaznik),
    FOREIGN KEY (IDplatba) REFERENCES platba (IDplatba),
    FOREIGN KEY (IDdoprava) REFERENCES doprava (IDdoprava),
    FOREIGN KEY (IDstav) REFERENCES stav (IDstav)
)ENGINE = InnoDB;

CREATE TABLE knihaObjednavka(
	IDkniha int NOT NULL,
    IDobjednavka int NOT NULL,
    PRIMARY KEY (IDkniha,IDobjednavka),
    FOREIGN KEY (IDkniha) REFERENCES kniha (IDkniha),
    FOREIGN KEY (IDobjednavka) REFERENCES objednavka (IDobjednavka)
)ENGINE = InnoDB;

INSERT INTO vydavatelstvo VALUES
	(0,"Jaga"),
    (0,"Gama"),
    (0,"Hera"),
    (0,"Cola"),
    (0,"Vila");
    
INSERT INTO zaner VALUES
	(0,"Beletria"),
	(0,"Sci-fi"),
	(0,"Fantasy"),
	(0,"Odborna"),
	(0,"Detektivne");
    
INSERT INTO rok VALUES
	(0,2000),
	(0,2001),
	(0,2002),
	(0,2003),
	(0,2004),
    (0,2005),
    (0,2006),
    (0,2007);
    
INSERT INTO hodnotenie VALUES
    (0,1),
	(0,2),
	(0,3),
	(0,4),
	(0,5),
    (0,6),
    (0,7),
    (0,8),
    (0,9),
    (0,10);
   
INSERT INTO kniha VALUES
	(0,4,2,"Jarmok",19.52,5,1,7),
    (0,1,3,"Vylet",14.02,1,3,4),
    (0,3,5,"Zahada",9.25,6,7,6),
	(0,2,1,"Cestovatel",5.96,4,5,3),
    (0,4,5,"Med",15.40,2,2,1);
   
INSERT INTO autor VALUES
	(0,"Janko","Hrasko"),
    (0,"Jozef","Mak"),
    (0,"Peter","Jilemnicky"),
    (0,"Marcel","Zavadny"),
    (0,"Ivana","Bartova");
   
INSERT INTO autorKniha VALUES
	(3,2),
    (3,1),
    (2,4),
    (1,3),
    (5,5);
   
INSERT INTO mesto VALUES
	(0,"Kosice"),
    (0,"Zilina"),
    (0,"Martin"),
    (0,"Presov"),
    (0,"Bratislava");
   
INSERT INTO zakaznik VALUES
	(0,"Milan","Velky","Letna",27,2,"+421904458232"),
    (0,"Jakub","Haus","Podhradova",8,3,"+420916545728"),
    (0,"Mikulas","Jakob","Hutna",65,3,"+421911103582"),
    (0,"Dalibor","Urela","Vesela",40,1,"+410913570402"),
    (0,"Veronika","Janigova","Timravy",19,2,"+442920578236");

INSERT INTO platba VALUES
	(0,"Debetna Karta"),
    (0,"Kreditna Karta"),
    (0,"Dobierka");
   
INSERT INTO doprava VALUES
	(0,"Posta"),
    (0,"Kurier");
   
INSERT INTO stav VALUES
	(1,"V"),
    (2,"N");

INSERT INTO objednavka VALUES
	(0,3,'2019-10-18',1,17.55,1,2,1),
    (0,2,'2019-10-25',3,15.23,2,1,2),
    (0,4,'2019-09-08',2,10.13,1,1,2),
    (0,5,'2019-09-20',1,7.52,2,2,1),
    (0,1,'2019-09-12',1,12.54,2,1,1);

INSERT INTO knihaObjednavka VALUES
	(1,2),
    (2,2),
    (3,1),
    (4,1),
    (3,3);

SELECT * FROM vydavatelstvo;
SELECT * FROM zaner;
SELECT * FROM rok;
SELECT * FROM hodnotenie;

SELECT * FROM kniha;
SELECT * FROM autor;
SELECT * FROM autorKniha;

SELECT * FROM mesto;
SELECT * FROM zakaznik;

SELECT * FROM platba;
SELECT * FROM doprava;
SELECT * FROM stav;
SELECT * FROM objednavka;
SELECT * FROM knihaObjednavka;

SELECT datum,nazov FROM knihaObjednavka JOIN kniha ON kniha.IDkniha = knihaObjednavka.IDkniha
										JOIN objednavka ON objednavka.IDobjednavka = knihaObjednavka.IDobjednavka ORDER BY datum ASC;
SELECT nazov,meno,priezvisko FROM autorKniha 	JOIN kniha ON kniha.IDkniha = autorKniha.IDkniha
												JOIN autor ON autor.IDautor = autorKniha.IDautor WHERE meno LIKE 'J%';
SELECT IDkniha,vydavatelstvo,nazov,zaner FROM kniha JOIN vydavatelstvo ON vydavatelstvo.IDvydavatelstvo = kniha.IDvydavatelstvo
													JOIN zaner ON zaner.IDzaner = kniha.IDzaner WHERE vydavatelstvo = 'Cola';
                                                    
CREATE VIEW umelec AS SELECT meno,priezvisko,nazov,vydavatelstvo,zaner,naSklade FROM autor JOIN autorKniha ON autor.IDautor = autorKniha.IDautor
																						JOIN kniha ON autorKniha.IDkniha = kniha.IDkniha
																						JOIN vydavatelstvo ON vydavatelstvo.IDvydavatelstvo = kniha.IDvydavatelstvo
																						JOIN zaner ON zaner.IDzaner = kniha.IDzaner ORDER BY priezvisko;
CREATE VIEW knizka AS SELECT nazov,zaner,hodnotenie,vydavatelstvo,rok,cena FROM kniha 	JOIN zaner ON zaner.IDzaner = kniha.IDzaner
																						JOIN hodnotenie ON hodnotenie.IDhodnotenie = kniha.IDhodnotenie
																						JOIN vydavatelstvo ON vydavatelstvo.IDvydavatelstvo = kniha.IDvydavatelstvo
																						JOIN rok ON rok.IDrok = kniha.IDrok ORDER BY nazov;
CREATE VIEW predaj AS SELECT meno,priezvisko,pocet,datum,nazov,zaner FROM kniha JOIN zaner ON zaner.IDzaner = kniha.IDzaner
																				JOIN knihaObjednavka ON kniha.IDkniha = knihaObjednavka.IDkniha
																				JOIN objednavka ON objednavka.IDobjednavka = knihaObjednavka.IDobjednavka
																				JOIN zakaznik ON zakaznik.IDzakaznik = objednavka.IDzakaznik ORDER BY datum;
CREATE VIEW donaska AS SELECT priezvisko,kontakt,datum,doprava,stav,platba FROM objednavka 	JOIN zakaznik ON zakaznik.IDzakaznik = objednavka.IDzakaznik
																							JOIN doprava ON doprava.IDdoprava = objednavka.IDobjednavka
																							JOIN stav ON stav.IDstav = objednavka.IDobjednavka
																							JOIN platba ON platba.IDplatba = objednavka.IDplatba ORDER BY stav;
CREATE VIEW bydlisko AS SELECT meno,priezvisko,mesto,ulica,cislo,doprava FROM zakaznik 	JOIN mesto ON mesto.IDmesto = zakaznik.IDmesto
																						JOIN objednavka ON objednavka.IDzakaznik = zakaznik.IDzakaznik
                                                                                        JOIN doprava ON doprava.IDdoprava = objednavka.IDdoprava ORDER BY ulica;
CREATE VIEW sell AS SELECT datum,meno,priezvisko,nazov,celkovaCena FROM zakaznik 	JOIN objednavka ON zakaznik.IDzakaznik = objednavka.IDzakaznik
																					JOIN knihaObjednavka ON knihaObjednavka.IDobjednavka = objednavka.IDobjednavka
																					JOIN kniha ON knihaObjednavka.IDkniha = kniha.IDkniha ORDER BY priezvisko;
SELECT * FROM umelec;
SELECT * FROM knizka;
SELECT * FROM predaj;
SELECT * FROM donaska;
SELECT * FROM bydlisko;
SELECT * FROM sell;