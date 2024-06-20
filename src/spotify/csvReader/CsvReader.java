package spotify.csvReader;

import TADs.binarysearchtree.BinarySearchTree;
import TADs.binarysearchtree.BinarySearchTreeImpl;
import TADs.hash.HashTable;
import TADs.hash.HashTableImpl;
import TADs.linkedlist.MyLinkedListImpl;
import TADs.linkedlist.MyList;
import spotify.entities.Artist;
import spotify.entities.Song;
import spotify.Spotify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CsvReader {

    public HashTable<Date, HashTable<String, BinarySearchTree<Integer,Song>>> readSongsFromCsv(String filePath) {

        HashTable<Date, HashTable<String, BinarySearchTree<Integer,Song>>> fechas =  new HashTableImpl<>(208);
//        HashTable<Date,HashTable<String,Integer>> cancionesRepe = new HashTableImpl<>(208);
//        HashTable<Date,HashTable<String,Integer>> artistasRepe = new HashTableImpl<>(208);

        String line;
        String csvSplitBy = "\",\""; // al abrir el archivo csv en intellij se ve que se le agregan comillas, entones queda: "spotify_id,""name"",""artists"" y decidimos que se separe cuando hayan [,""] , de esa forma, si hay una coma en cualquier parte de una cancion, no la separe

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            br.readLine(); // saltea la primera linea

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                String[] dataIdYNombre = data[0].split(",\""); // en data[0] obtengo "spotify_id,""name"" todo junto entonces quiero separar por ," en vez de ","


                Song song = new Song();

                String id = song.setSpotify_id((dataIdYNombre[0]).replaceAll("\"", ""));
                String nombre = song.setName((dataIdYNombre[1]).replaceAll("\"", ""));
                song.setArtist(parseArtists(data[1].replaceAll("\"", "")));
                Integer daily = song.setDaily_rank(Integer.parseInt(data[2].replaceAll("\"", "")));
                song.setDaily_movement(Integer.parseInt(data[3].replaceAll("\"", "")));
                song.setWeekly_movement(Integer.parseInt(data[4].replaceAll("\"", "")));
                String nombrePais = song.setCountry(data[5].isEmpty() ? "" : data[5].replaceAll("\"", ""));  // como los primeros paises son vacios, tenemos que chequear esa posibilidad
                Date snapshot= song.setSnapshot_date(parseDate(data[6].replaceAll("\"", ""), dateFormat));
                song.setPopularity(Integer.parseInt(data[7].replaceAll("\"", "")));
                song.setIs_explicit(Boolean.parseBoolean(data[8].replaceAll("\"", "")));
                song.setDuration_ms(Integer.parseInt(data[9].replaceAll("\"", "")));
                song.setAlbum_name(data[10].replaceAll("\"", ""));
                song.setAlbum_release_date(parseDate(data[11].replaceAll("\"", ""), dateFormat));
                song.setDanceability(Double.parseDouble(data[12].replaceAll("\"", "")));
                song.setEnergy(Double.parseDouble(data[13].replaceAll("\"", "")));
                song.setKey(Integer.parseInt(data[14].replaceAll("\"", "")));
                song.setLoudness(Double.parseDouble(data[15].replaceAll("\"", "")));
                song.setMode(Integer.parseInt(data[16].replaceAll("\"", "")));
                song.setSpeechiness(Double.parseDouble(data[17].replaceAll("\"", "")));
                song.setAcousticness(Double.parseDouble(data[18].replaceAll("\"", "")));
                song.setInstrumentalness(Double.parseDouble(data[19].replaceAll("\"", "")));
                song.setLiveness(Double.parseDouble(data[20].replaceAll("\"", "")));
                song.setValence(Double.parseDouble(data[21].replaceAll("\"", "")));
                song.setTempo(Double.parseDouble(data[22].replaceAll("\"", "")));
                song.setTime_signature(Integer.parseInt(data[23].replaceAll("[\";]", "")));

                //no hay fechas         // fechas no tiene la fecha que quiero     -----> no existe ni el pais, creo el pais y la cancion y lo pongo en la fecha
                if(fechas.size()==0 || fechas.search(snapshot)==null ) { //busco en las fechas si esta la fecha, si no existe la fecha, significa que no existe ningun pais en esa fecha
                    HashTable<String, BinarySearchTree<Integer,Song>> paises = new HashTableImpl<>(75);  //74 * 50 +-
                    BinarySearchTree<Integer,Song> ranking = new BinarySearchTreeImpl<>();

                    ranking.insert(daily, song);
                    paises.put(nombrePais,ranking);
                    fechas.put(snapshot,paises);

                }else if(!fechas.search(snapshot).contains(nombrePais)){ //si ya lo contiene, tengo que agregar al lugar donde ya tengo guardada esa fecha
                    HashTable<String, BinarySearchTree<Integer,Song>> paises = fechas.search(snapshot);
                    BinarySearchTree<Integer,Song> ranking = new BinarySearchTreeImpl<>();

                    ranking.insert(daily, song);
                    paises.put(nombrePais,ranking);

                    //al hash paises en la fecha correspondiente le agregamos el arbol con la cancion nueva
                }else {//si ya lo contiene, tengo que agregar al lugar donde ya tengo guardado ese pais
                    BinarySearchTree<Integer,Song> ranking = fechas.search(snapshot).search(nombrePais);
                    MyList<Integer> inOrder = ranking.inOrderKey();

                    while(inOrder.contains(daily)) {
                        daily++;
                    }
                    ranking.insert(daily, song);

                }

//                if(!cancionesRepe.contains(snapshot)){
//                    BinarySearchTree<Integer,String> arbolRepe = new BinarySearchTreeImpl<>();
//                    cancionesRepe.put(snapshot,arbolRepe);
//                }
//                BinarySearchTree<Integer,String> arbolRepe = cancionesRepe.search(snapshot);
//                if(arbolRepe.find(nombre)==null){
//                    arbolRepe.insert(1,nombre);
//                }else {
//                    int value=arbolRepe.findKey(nombre)+1;
//                    arbolRepe.replace(nombre,value);
//                }
//
//                if(!cancionesRepe.contains(snapshot)){
//                    HashTable<String,Integer> cancion = new HashTableImpl<>(51);
//                    cancion.put(nombre,1);
//                    cancionesRepe.put(snapshot,cancion);
//                }else if(!cancionesRepe.search(snapshot).contains(nombre)){
//                    cancionesRepe.search(snapshot).put(nombre,1);
//                }else {
//                    HashTable<String,Integer> can = cancionesRepe.search(snapshot);
//                    int value=can.search(nombre)+1;
//                    can.changeValue(nombre,value);
//                }
//
//                if(!artistasRepe.contains(snapshot)){
//                    HashTable<String,Integer> artista = new HashTableImpl<>(51);
//                    artista.put(nombre,1);
//                    artistasRepe.put(snapshot,artista);
//                } else if (!artistasRepe.search(snapshot).contains(nombre)) {
//                    artistasRepe.search(snapshot).put(nombre,1);
//                }else {
//                    HashTable<String,Integer> artista = artistasRepe.search(snapshot);
//                    int value=artista.search(nombre)+1;
//                    artista.changeValue(nombre,value);
//                }


//                if(cR.find(nombre)==null){
//                    cR.insert(nombre,1); //aparece 1 vez
//                }else {
//                    int value = cR.find(nombre) + 1;
//                    cR.replace(nombre,value);
//                }


            }
        } catch (IOException | NumberFormatException | ParseException i ) {
            i.printStackTrace();
        }

//        spoti.setFechas(fechas);
////        spoti.setCancionesRepetidas(cancionesRepe);
////        spoti.setcR(arbolRepe);
//        spoti.setCancionesRepetidas(cancionesRepe);
        return fechas;
    }

//    private static HashTable<Date,HashTable<String,Integer>> verRepe(HashTable<Date,HashTable<String,Integer>> hash, Date snapshot, String nombre){
//        if(!hash.contains(snapshot)){
//            HashTable<String,Integer> repes = new HashTableImpl<>(51);
//            repes.put(nombre,1);
//            hash.put(snapshot, repes);
//        } else if (!hash.search(snapshot).contains(nombre)) {
//            hash.search(snapshot).put(nombre,1);
//        }else {
//            HashTable<String,Integer> repes = hash.search(snapshot);
//            int value= repes.search(nombre)+1;
//            repes.changeValue(nombre,value);
//        }
//    }

    private static MyList<Artist> parseArtists(String artistsString) {
        String[] artistNames = artistsString.split(",\\s*");
        MyList<Artist> artists = new MyLinkedListImpl<>();
        for (String name : artistNames) {
            artists.add(new Artist(name.trim()));
        }
        return artists;
    }

    private static Date parseDate(String dateString, SimpleDateFormat dateFormat) throws ParseException {
        if(!dateString.isEmpty()) { //hay release dates vacias, entonces tuvimos que chequear primero si eran vacias
            return dateFormat.parse(dateString);
        }
        return null;
    }

}
