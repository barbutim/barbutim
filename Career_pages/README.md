## Popis projektu

Cieľom projektu je implementácia kariérnych stránok pre firmu Tesla, kde si používatelia môžu prezerať ponuky prác, poprípade sa na nich prihlásiť, a náborári jednotlivé ponuky spravovať



## Implementované: 

- Read.me + Dokumentácia
- Inštalačná príručka
- Využívanie databáze - PostgreSQL
- Interceptors
- REST
- Nasadenie na Heroku
- Monolitická architektúra
- Design patterns


## Čiastočné implementované: 

- Cache - Hazelcast (nepoužíva sa)


## Neimplementované: 

- Kafka
- Elastic search
- Basic Auth.

## Ako sa z BO urobí DTO?

Samotný business object je vlastne Entita a teda mapovanie Entity na DTO prebieha pomocou Model mapperu.
