# CI2693. Proyecto II: Mundo Chiquito

Integrantes:
- 1910211 Prieto Tovar, Jesùs Leonardo.
- 1710614 Soares Dos Reis, Sandibel Yescenia.

# Implementacion del grafo

Para representar el grafo de este problema se decidio usar la interfaz Map, en especifico se uso la clase HashMap.
Como la clase HashMap usa llaves para identificar sus elementos y asi poder acceder al contenido, esto nos brinda 
una mayor facilidad para hallar el elemento deseado, en este caso, la carta deseada.

En este HashMap, las llaves son las cartas que nos presentan los jugadores(Conjunto de vertices) y el valor o contenido, 
serian las cartas que poseen un atributo igual a la llave indicada (Conjunto de aristas). 

Otra estructura que se uso fueron los ArrayList para representar a las cartas con un atributo igual(dependen de la carta/key).
Y se uso por la flexibilidad que nos brinda para incluir elementos, pues un Array al ser estatico no nos permite esto.
