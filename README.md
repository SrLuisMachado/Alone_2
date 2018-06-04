# Alone_2
Juego hecho desde un archivo JSON y programación java

Este proyecto se trata sobre un juego Java en 2d en el cual hay un personaje(avatar) que es el que controla el usuario.


El juego incluye estos apartados los cuales por codigo me asegure que se pudieran modificar fácil y que se puedan añadir más:

·Unos enemigos que pueden ser cualquiera personaje y ser ampliados solo se necesita una hoja de sprites y asignar un nombre y el daño y la vida que tienen.
·Objetos que te aumentaran la vida o la resistencia.
·Armas que te ayudaran a defenderte de los zombies.


El mapa esta hecho mediante un archivo JSON, el cual escoge un sprite de una o varias hojas de sprite y las asigna en un lugar del mapa.

En el juego hay sonidos tanto para disparar, como para que los zombies se quejen o musica de fondo(aconsejo apagarla si te irritas fácil).

El avatar puede equiparse un arma y atacar a los enemigos a distancia.

Los enemigos estan programados por una IA para perseguir al avatar y atacarlo.

Hay colisione implementadas por el mapa JSON.

El juego dispone de un menu donde tienes el inventario y el equipo.


La vida de los enemigos y del avatar bajan si son atacados.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------

Aclaramientos:
·No se a implementado conexion a base de datos en este proyecto, si que tengo un proyecto con la conexion completa y funcionando perfectamente,
pero como la implemente después de terminar el proyecto la conexion con la clase principal hacia que diera el error de que los controle no se detectaran,
haciendo que el personaje se quedara en el mismo sitio y que las actualizaciones y los enemigos moviendose siguieran funcionando.

·La IA esta implementada mediante metodo de Dijkstra el cual era bastante utilizado para detectar el camino más corto en los gps, la implementacio es sencilla si hay colision es un "Double Max_Value" y los campos que se conectan directamente se les asigna un valor y los que son "vecinos" se deja para la siguiente secuencia hasta que se cambia por un resultado que sea menor a las demás opciones.
de lo que se trata es de sustituir los valores que se anotan por la unidad más baja posible hasta encontrar un camino que necesite pocos movimientos.

·Es verdad que mi juego puede estar en pantalla completa en cualquier monitor, que por que lo he dejado en 640 x 360?Por rendimiento, si lo hubiera dejado en pantalla completa sería injugable para ordenadores poco potentes.

·Que pasa cuando muere el avatar? aparecera una ventana de Game Over con un boton de Play Again.
