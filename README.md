# obligatorio2024-Gutierrez-Rabajoli
Descripción de los procesos de carga y realización de reportes:

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
