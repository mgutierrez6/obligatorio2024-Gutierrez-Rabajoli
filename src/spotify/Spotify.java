package spotify;

import TADs.binarysearchtree.BinarySearchTree;
import TADs.binarysearchtree.BinarySearchTreeImpl;
import TADs.hash.HashTable;
import TADs.hash.NodeHash;
import TADs.linkedlist.*;
import TADs.hash.HashTableImpl;
import spotify.csvReader.CsvReader;
import spotify.entities.Artist;
import spotify.entities.Song;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Scanner;

public class Spotify {
    private HashTable<Date, HashTable<String, BinarySearchTree<Integer, Song>>> fechas;
//    HashTable<Date,HashTable<String,Integer>> cancionesRepe;
//    private HashTable<Date,BinarySearchTree<String,Integer>> cancionesRepe;
//    private BinarySearchTree<String,Integer> cR;


    public Spotify() {
        CsvReader csv = new CsvReader();
        this.fechas = csv.readSongsFromCsv("data.csv");;
    }

    public void top10FechaYPais(Date fecha, String pais) {
        System.out.println("Consulto para la fecha: " + fecha + " y país: " + pais);

        if (fechas.contains(fecha)){

            HashTable<String,BinarySearchTree<Integer,Song>> paises = fechas.search(fecha);
            if (paises != null && paises.contains(pais)){

                BinarySearchTree<Integer,Song> cancionesPais = paises.search(pais);
                MyList<Song> topCanciones = cancionesPais.inOrderValue();

                System.out.println("Top 10 canciones en el "+pais+" en la " + fecha+ " es:");
                for (int i = 0; i<10; i++){
                    Song cancion = topCanciones.get(i);
                    System.out.println((i+1) + ". "+ cancion.getName());
                }


            }else{
                System.out.println("no se encontraron datos paea el pais" + pais);
            }
        }else{
            System.out.println("no se encontraton datos para la fecha: "+fecha);
        }

    }

    public void top50CanRep(Date fecha2){
        HashTable<String,Integer> cancionesRepe=new HashTableImpl<>(300);

        HashTable<String, BinarySearchTree<Integer, Song>> paises2= fechas.search(fecha2);
        for(int i=0; i<paises2.size();i++){
            if(paises2.get(i)!=null){
                MyList<Song> can= paises2.get(i).getValue().inOrderValue();
                for (int j=0; j<can.size();j++){
                    String nombre= can.get(j).getName();
                    if(!cancionesRepe.contains(nombre)){
                        cancionesRepe.put(nombre,1);
                    }else{
                        int value= cancionesRepe.search(nombre)+1;
                        cancionesRepe.changeValue(nombre,value);
                    }
                }
            }

        }
        BinarySearchTree<Integer,String> ordenados= new BinarySearchTreeImpl<>();
        for (int i = 0; i < (cancionesRepe.size() - 1); i++) {
            NodeHash<String, Integer> nodo = cancionesRepe.get(i);
            if (nodo != null) {
                ordenados.insert(nodo.getValue(), nodo.getKey());
            }
        }
        MyList<String> lista = ordenados.inOrderValue();
        for (int i = (lista.size() - 1); i > (lista.size() - 6); i--) {
            System.out.println(lista.get(i));
        }
    }

    public void top7Artistas(Date fecha3inicio, Date fecha3fin){
        HashTable<String, Integer> artistasRep = new HashTableImpl<>(300);

        for (int k = 0; k < fechas.size(); k++) {
            if (fechas.get(k) != null) {
                Date fechaAct = fechas.get(k).getKey();
                if (fechaAct.compareTo(fecha3inicio) >= 0 && fechaAct.compareTo(fecha3fin) <= 0) {
                    HashTable<String, BinarySearchTree<Integer, Song>> paises3 = fechas.search(fechaAct);
                    for (int i = 0; i < paises3.size(); i++) {
                        if (paises3.get(i) != null) {
                            MyList<Song> can = paises3.get(i).getValue().inOrderValue();
                            for (int j = 0; j < can.size(); j++) {
                                MyList<Artist> artistas = can.get(j).getArtist();
                                for (int h = 0; h < artistas.size(); h++) {
                                    String nombre = artistas.get(h).getName();
                                    if (!artistasRep.contains(nombre)) {
                                        artistasRep.put(nombre, 1);
                                    } else {
                                        int value = artistasRep.search(nombre) + 1;
                                        artistasRep.changeValue(nombre, value);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        BinarySearchTree<Integer, String> ordenados3 = new BinarySearchTreeImpl<>();
        for (int i = 0; i < artistasRep.size(); i++) {
            NodeHash<String, Integer> nodo = artistasRep.get(i);
            if (nodo != null) {
                ordenados3.insert(nodo.getValue(), nodo.getKey());
            }
        }

        MyList<String> lista3 = ordenados3.inOrderValue();
        for (int i = (lista3.size()-1); i > (lista3.size()-8); i--) {
            System.out.println(lista3.get(i));
        }
    }


    public int contarArtista(Date fecha, String artista) {

        int veces = 0;
        if (fechas.contains(fecha)) {
            HashTable<String, BinarySearchTree<Integer, Song>> paises = fechas.search(fecha);

            if (paises != null) {
                for (int i = 0; i < paises.size(); i++) {
                    NodeHash<String, BinarySearchTree<Integer, Song>> nodoPais = paises.get(i);
                    if (nodoPais != null) {
                        BinarySearchTree<Integer, Song> cancionesPais = nodoPais.getValue();
                        if (cancionesPais != null) {
                            MyList<Song> canciones = cancionesPais.inOrderValue();
                            boolean found = false;
                            for (int j = 0; j < 50; j++) {
                                Song cancion = canciones.get(j);
                                for (int k = 0; k < cancion.getArtist().size(); k++) {
                                    if (cancion.getArtist().get(k).getName().equals(artista)) {
                                        veces++;
                                        found = true;
                                        break;
                                    }
                                }
                                if (found) {
                                    break;
                                }
                            }
                        }
                    }

                }
            } else {
                System.out.println("no se encontraton datos para la fecha: " + fecha);
            }
        }
        return veces;
    }

    void menu(Spotify spoti){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el numero de su consulta:");
        int opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion) {

            case 1:
                Date fecha1 = spoti.askFecha(sc);
                String pais = spoti.askPais(sc);
                spoti.top10FechaYPais(fecha1, pais);
                volver(sc,spoti);

            case 2:
                Date fecha2 = spoti.askFecha(sc);

                spoti.top50CanRep(fecha2);

                volver(sc,spoti);

            case 3:
                Date fecha3inicio = spoti.askFecha(sc);
                Date fecha3fin = spoti.askFecha(sc);

                spoti.top7Artistas(fecha3inicio,fecha3fin);

                volver(sc, spoti);

            case 4:
                Date fecha3 = spoti.askFecha(sc);
                System.out.println("Ingrese el nombre del artista:");
                String artista = sc.nextLine();
                int veces = spoti.contarArtista(fecha3, artista);
                System.out.println("El artista " + artista + " aparece " + veces + " veces en el top 50 en la fecha " + fecha3);
                volver(sc, spoti);

            default:
                System.out.println("Opción inválida.");
                break;
        }
    }


    Date askFecha(Scanner sc) {
        System.out.println("Ingrese la fecha en la que quiere consultar:");
        String fechaSC = sc.nextLine();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(fechaSC);
        } catch (ParseException e) {
            System.out.println("Fecha inválida. Inténtelo de nuevo.");
            askFecha(sc);
        }
        return null;
    }

    void volver(Scanner sc, Spotify spoti){
        System.out.println("\nDesea volver a realizar una consulta?");
        System.out.println("1- Si");
        System.out.println("2- No");
        int opcion2 = sc.nextInt();
        if(opcion2==1){
            menu(spoti);
        }
    }

    String askPais(Scanner sc) {
        System.out.println("Ingrese el país que quiere consultar:");
        String pais = sc.nextLine();
        if (pais != null) {
            return pais;
        } else {
            System.out.println("País inválido. Inténtelo de nuevo.");
            askPais(sc);
        }
        return null;
    }

    public HashTable<Date, HashTable<String, BinarySearchTree<Integer, Song>>> getFechas() {
        return fechas;
    }

    public void setFechas(HashTable<Date, HashTable<String, BinarySearchTree<Integer, Song>>> fechas) {
        this.fechas = fechas;
    }


}





