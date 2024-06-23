# obligatorio2024-Gutierrez-Rabajoli
**Descripción de los procesos de carga y realización de reportes:
**
Para procesar los datos, nosotras decidimos utilizar la función readSongsFromCsv, para la cual nos creamos una clase CsvReader.
Dentro de la función, creamos un Hash de la forma: HashTable<Date, HashTable<String, BinarySearchTree<Integer,Song>>> para almacenar los datos que vayamos recolectando, el primer hash va a tener como key una fecha del “snapshot” de la canción (cuando se obtuvo esa información) y su value será otro hash, con key país y value un árbol binario de búsqueda, el cual tiene en su nodo como key la posición de la canción en el ranking y como value el objeto cancion que recuperamos haciendo lo que viene a continuación.

Para empezar a cargar los datos, tuvimos que indagar cómo leer de archivos externos, encontramos una clase de java: BufferedReader, la cual nos permite entrar a un archivo al hacer un objeto “br”, con el file path “data.csv” en nuestro caso. (BufferedReader br = new BufferedReader(new FileReader(archivo))

Primero leemos la primera línea con br.readLine();, ya que esta no debe ser registrada. Luego entramos a un while, que va a recorrer todo el archivo, hasta que la línea actual sea nula (hasta que termine el documento).

Dentro del while, ya le asignamos un valor a “lineaActual” que seria la linea que acaba de recuperar del archivo, a esta línea actual le aplicamos la función “split” para que nos separe la String linea actual en los atributos que necesitamos y nos devuelva un array con los mismos.

Para eso, tuvimos que elegir ese “separador” para los datos, es decir, tuvimos que pensar cómo decirle a la función que queremos que nos separe los datos del archivo, esto fue bastante complejo ya que java lee los archivos csv de esta forma:
"spotify_id,""name"",""artists"",""daily_rank"",""daily_movement"",""weekly_movement"",""country"",""snapshot_date"",""popularity"",""is_explicit"",""duration_ms"",""album_name"",""album_release_date"",""danceability"",""energy"",""key"",""loudness"",""mode"",""speechiness"",""acousticness"",""instrumentalness"",""liveness"",""valence"",""tempo"",""time_signature""";;

Al tener varias comillas y comas, tuvimos que encontrar una forma de separarlos para no perder información en el proceso. Primero probamos separarlo solo por la coma (ya que en los archivos csv se separa de esa forma), pero nos daba errores porque hay canciones con comas en el nombre.

Después de probar con varias formas, decidimos separar según String separador = "\",\""; , la cual está compuesta por “,” .
Pudimos hacer eso, ya que todos los atributos están separados entre sí por ““,”” (""name"",""artists""), excepto por el spotify_id y el name que están separados por solo ,””.

Una vez separada la línea en el array “data”, tenemos que resolver el problema anterior. Para eso usamos otra vez “split”, la posición 0 del array obtenido, haciendo String[] dataIdYNombre =
data[0].split(",\""); con eso, ya logramos separar todos los atributos, siendo dataIdTNombre[0] el id y dataIdTNombre[1] el nombre.

Continuamos creando un objeto de la clase song, al que le vamos a cargar los datos de la línea en la que estamos actualmente.
Ahora tenemos que usar setters de song para cada uno de los atributos recuperados, así cargamos sus valores.

Como todos los atributos que están en el array son Strings, tenemos que hacer algunos cambios para que se ajusten a lo pensado anteriormente. Para eso utilizamos Integer.parseInt() para convertir de String a Integer, Boolean.parseBoolean() para convertir de String a Boolean y Double.parseDouble para convertir de String a Double.

Para convertir la fecha y agregar artistas tuvimos que recurrir a funciones auxiliares como “cambiarArtists” y “cambiarDate”.
La primera separa el string dado por comas (ya que pueden haber muchos artistas) y crea una linkedlist en la que vamos a guardar los objeto artista.
La función cambiarDate se fija que la fecha no sea vacía antes de intentar hacer dateFormat.parse(dateString); para que no de errores después (hay fechas de álbumes nulas).

Además, en el nombre del país, chequeamos que si el nombre es vacío, que lo cambie a “General” así en las búsquedas podemos buscar “General” y aparece el top de todos.

Para cargar los datos al hash fecha que mencionamos al inicio, nos fijamos que exista la fecha, si no existe la creamos y también creamos el hash países y el árbol ranking, para guardar toda la información.
Si ya existía esa fecha, la buscamos y dentro nos fijamos que exista el país, si no existe lo creamos y también el árbol ranking.
Por último, si todo ya existe, agregamos una canción al árbol ranking; pero primero tenemos que fijarnos que no exista un nodo con la misma key, ya que en el archivo csv, hay un par de rankings que repiten número. Si eso pasa, entra en un while que va a asignarle el número que le sigue.

**Medición de eficiencia de la aplicación (cantidad de memoria RAM consumida y tiempo de ejecución promedio):
**
Para medir el tiempo de ejecución y la cantidad de memoria consumida, ya que no sabíamos muy bien cómo hacerlo, buscamos información de internet y hablando con compañeros, averiguamos cómo implementar métodos para calcular estos datos.

 Para medir la memoria utilizada, se utiliza el método "getUsedMemory." Este método obtiene la memoria total actualmente ("totalMemory") y la memoria libre disponible ("freeMemory"). La diferencia entre estos dos valores da la cantidad de memoria utilizada.
 
Antes de ejecutar la operación, se llama a "getUsedMemory()" para obtener la memoria utilizada actualmente y se guarda en "memoryBefore". Luego de ejecutar la operación, se vuelve a llamar a "getUsedMemory()" para obtener la memoria utilizada y se guarda en "memoryAfter". La diferencia entre "memoryAfter" y "memoryBefore" da la memoria consumida por la operación.
 
El tiempo de ejecución se mide utilizando "System.nanoTime()", que permite realizar una medición precisa en nanosegundos. 
Como no teníamos mucha idea de que hacer, optamos por realizar la función como veníamos haciendo, y agregar funciones auxiliares, que fueran exactamente el mismo código, pero sin los comentarios, así podíamos correr esa función cierta cantidad de veces, medir el tiempo y hacer un promedio. 
Por los datos obtenidos nos parece que algún error debe haber, así que decidimos realizar los tiempos a mano para comparar, los dejamos al final.

Dentro de las funciones auxiliares hicimos un bucle de iteraciones para calcular el tiempo promedio de ejecución. En cada iteración, se registra el tiempo inicial y final del proceso de impresión. Antes de ejecutar el bloque de código que se desea medir, se almacena el tiempo actual en nanosegundos usando "System.nanoTime()". Este valor se guarda en la variable "startIterTime". Después de ejecutar el bloque de código, se almacena nuevamente el tiempo actual en nanosegundos en la variable "endIterTime". La diferencia entre estos dos tiempos proporciona el tiempo de ejecución de una iteración. Este valor se suma a "totalTime" para calcular el tiempo total de todas las iteraciones. Este proceso se repite 9 veces (porque ya lo hicimos una vez en la funcion principal y recuperamos los datos de memoria y tiempo) para calcular un promedio, lo que ayuda a obtener una medición precisa del tiempo de ejecución.
 
Luego el método "printMemoryAndTime" se utiliza para mostrar en la consola los resultados de las mediciones de memoria y tiempo de ejecución de una operación. Por lo  tanto, cuando uno realiza una consulta además del resultado de esta misma, se muestra: la cantidad de memoria utilizada antes de que se ejecutara la operación,  la cantidad de memoria utilizada después de que se ejecutara la operación, la diferencia entre la memoria utilizada antes y después de la operación (memoryAfter - memoryBefore) que representa la cantidad de memoria consumida específicamente por la operación, se muestra el tiempo total que tomó ejecutar la operación y el tiempo promedio que tomó ejecutar la operación.

Datos a mano (con cronómetro):
consulta 1: 0.05 segundos 
consulta 2: 0.04 segundos 
consulta 3: 0.07 segundos 
consulta 4: 0.03 segundos 
consulta 5: 0.04 segundos

Carga de datos: demora 15,08 segundos en leer todos los datos del csv


Bibliografía:
https://www.geeksforgeeks.org/java-io-bufferedreader-class-java/
https://www.geeksforgeeks.org/java-runtime-getruntime-method/

