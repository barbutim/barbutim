DROP TABLE IF EXISTS clovek, student, emailaddress, ucitel, telefonnumber, spolupracovnici, teoria, ucastnici, vyucujuci, auto, jazda;

CREATE TABLE clovek (
	rodneCislo CHAR(11) PRIMARY KEY,
	meno VARCHAR(50) NOT NULL,
	priezvisko VARCHAR(50) NOT NULL,
	ulica VARCHAR(100) NOT NULL,
	mesto VARCHAR(50) NOT NULL,
	psc CHAR(5) NOT NULL
);

CREATE TABLE student (
	rodneCislo CHAR(11) UNIQUE CONSTRAINT student_fk_rodneCislo REFERENCES clovek (rodneCislo) ON UPDATE CASCADE ON DELETE SET NULL,
	cisloPrihlasky CHAR(20) PRIMARY KEY
);

CREATE TABLE emailaddress (
	email VARCHAR(50) NOT NULL,
	student CHAR(20) PRIMARY KEY,
	CONSTRAINT emailaddress_ch_email CHECK (email LIKE '_%@_%.__%'),
	CONSTRAINT emailaddress_fk_student FOREIGN KEY (student) REFERENCES student (cisloPrihlasky)
);

CREATE TABLE ucitel (
	rodneCislo CHAR(11) UNIQUE CONSTRAINT ucitel_fk_rodneCislo REFERENCES clovek (rodneCislo) ON UPDATE CASCADE ON DELETE SET NULL,
	cisloUcitela CHAR(20) PRIMARY KEY,
	email VARCHAR(50) NOT NULL,
	CONSTRAINT ucitel_ch_email CHECK (email LIKE '_%@_%.__%')
);

CREATE TABLE telefonnumber (
	telefon CHAR(13) NOT NULL,
	ucitel CHAR(20) NOT NULL,
	PRIMARY KEY (telefon, ucitel),
	CONSTRAINT telefonnumber_fk_ucitel FOREIGN KEY (ucitel) REFERENCES ucitel (cisloUcitela)
);

CREATE TABLE spolupracovnici (
	ucitel CHAR(20) NOT NULL,
	spoluucitel CHAR(20) NOT NULL,
	PRIMARY KEY (ucitel, spoluucitel),
	CONSTRAINT spolupracovnici_fk_ucitel FOREIGN KEY (ucitel) REFERENCES ucitel (cisloUcitela),
	CONSTRAINT spolupracovnici_fk_spoluucitel FOREIGN KEY (spoluucitel) REFERENCES ucitel (cisloUcitela)
);

CREATE TABLE teoria (
	datum DATE NOT NULL,
	cas TIME NOT NULL,
	PRIMARY KEY (datum, cas)
);

CREATE TABLE ucastnici (
	student CHAR(20) NOT NULL,
	datum DATE NOT NULL,
	cas TIME NOT NULL,
	PRIMARY KEY (student, datum, cas),
	CONSTRAINT ucastnici_fk_student FOREIGN KEY (student) REFERENCES student (cisloPrihlasky),
	CONSTRAINT ucastnici_fk_datumcas FOREIGN KEY (datum, cas) REFERENCES teoria (datum, cas)
);

CREATE TABLE vyucujuci (
	ucitel CHAR(20) NOT NULL,
	datum DATE NOT NULL,
	cas TIME NOT NULL,
	PRIMARY KEY (ucitel, datum, cas),
	CONSTRAINT vyucujuci_fk_ucitel FOREIGN KEY (ucitel) REFERENCES ucitel (cisloUcitela),
	CONSTRAINT vyucujuci_fk_datumcas FOREIGN KEY (datum, cas) REFERENCES teoria (datum, cas)
);

CREATE TABLE auto (
	registracneCislo CHAR(20) PRIMARY KEY,
	znacka VARCHAR(30),
	statnaPoznavaciaZnacka VARCHAR(7) UNIQUE
);

CREATE TABLE jazda (
	idJazda INTEGER PRIMARY KEY,
	datum DATE NOT NULL,
	cas TIME NOT NULL,
	auto CHAR(20) NOT NULL,
	student CHAR(20) NOT NULL,
	ucitel CHAR(20) NOT NULL,
	UNIQUE (datum, cas, auto),
	CONSTRAINT jazda_fk_auto FOREIGN KEY (auto) REFERENCES auto (registracneCislo),
	CONSTRAINT jazda_fk_student FOREIGN KEY (student) REFERENCES student (cisloPrihlasky),
	CONSTRAINT jazda_fk_ucitel FOREIGN KEY (ucitel) REFERENCES ucitel (cisloUcitela)
);