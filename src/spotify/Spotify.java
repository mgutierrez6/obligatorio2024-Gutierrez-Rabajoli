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

    public Spotify() {
        CsvReader csv = new CsvReader();
        this.fechas = csv.readSongsFromCsv("data.csv");;
    }

    public void top10FechaYPais(Date fecha, String pais) {

        int iterations = 10; // Número de iteraciones para calcular el promedio
        long totalTime = 0;
        long memoryBefore = getUsedMemory();

        if (fechas.contains(fecha)) {

            HashTable<String, BinarySearchTree<Integer, Song>> paises = fechas.search(fecha);
            if (paises != null && paises.contains(pais)) {

                BinarySearchTree<Integer, Song> cancionesPais = paises.search(pais);
                MyList<Song> topCanciones = cancionesPais.inOrderValue();

                // Iteraciones para calcular el promedio de tiempo de ejecución
                for (int i = 0; i < iterations; i++) {
                    long startIterTime = System.nanoTime();
                    long endTime = System.nanoTime();
                    long iterExecutionTime = endTime - startIterTime;
                    totalTime += iterExecutionTime;
                }

                System.out.println("Top 10 canciones en el " + pais + " en la " + fecha + " es:");
                for (int i = 0; i < 10; i++) {
                    Song cancion = topCanciones.get(i);
                    StringBuilder artistas = new StringBuilder();  //para construir la cadena que contiene los nombres de los artistas.
                    for (int j = 0; j < cancion.getArtist().size(); j++) {
                        artistas.append(cancion.getArtist().get(j).getName());
                        if (j < cancion.getArtist().size() - 1) {
                            artistas.append(", ");
                        }
                    }
                    System.out.println((i + 1) + ". " + cancion.getName() + " - " + artistas.toString());
                }
                long averageTime = totalTime / iterations;  // Cálculo del tiempo promedio de ejecución
                // Impresión de memoria y tiempo promedio
                long memoryAfter = getUsedMemory();
                printMemoryAndTime(memoryBefore, memoryAfter, totalTime, averageTime);
            }else{
                System.out.println("No se encontraron datos para el pais: " + pais);
            }
        } else {
            System.out.println("No se encontraron datos para la fecha: " + fecha);
        }
    }

    public void top50CanRep(Date fecha2){
        HashTable<String,Integer> cancionesRepe=new HashTableImpl<>(300);
        if(fechas.contains(fecha2)) {
            HashTable<String, BinarySearchTree<Integer, Song>> paises2 = fechas.search(fecha2);
            for (int i = 0; i < paises2.size(); i++) {
                if (paises2.get(i) != null) {
                    MyList<Song> can = paises2.get(i).getValue().inOrderValue();
                    for (int j = 0; j < can.size(); j++) {
                        String nombre = can.get(j).getName();
                        if (!cancionesRepe.contains(nombre)) {
                            cancionesRepe.put(nombre, 1);
                        } else {
                            int value = cancionesRepe.search(nombre) + 1;
                            cancionesRepe.changeValue(nombre, value);
                        }
                    }
                }
            }
            BinarySearchTree<Integer, String> ordenados = new BinarySearchTreeImpl<>();
            for (int i = 0; i < (cancionesRepe.size() - 1); i++) {
                NodeHash<String, Integer> nodo = cancionesRepe.get(i);
                if (nodo != null) {
                    ordenados.insert(nodo.getValue(), nodo.getKey());
                }
            }
            MyList<String> lista = ordenados.inOrderValue();
            int contador = 0;
            for (int i = (lista.size() - 1); i > (lista.size() - 6); i--) {
                contador++;
                System.out.println((contador) + ") " + lista.get(i));
            }
        }else {
            System.out.println("No se encontraron datos para la fecha: " + fecha2);
        }
    }

    public void top7Artistas(Date fecha3inicio, Date fecha3fin){
        HashTable<String, Integer> artistasRep = new HashTableImpl<>(300);

        boolean llega = false;

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
                                    llega=true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(!llega){
            System.out.println("No se encontraron datos para las fechas: " + fecha3inicio + "y" + fecha3fin);
        }else {
            if(artistasRep.size()!=0) {
                BinarySearchTree<Integer, String> ordenados3 = new BinarySearchTreeImpl<>();
                for (int i = 0; i < artistasRep.size(); i++) {
                    NodeHash<String, Integer> nodo = artistasRep.get(i);
                    if (nodo != null) {
                        ordenados3.insert(nodo.getValue(), nodo.getKey());
                    }
                }
                MyList<String> lista3 = ordenados3.inOrderValue();
                for (int i = (lista3.size() - 1); i > (lista3.size() - 8); i--) {
                    System.out.println(lista3.get(i));
                }
            }
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
            }
        }else {
            System.out.println("No se encontraron datos para la fecha: " + fecha);
            return -1;
        }
        return veces;
    }

    public int contarCancionesTempo(double tempo1, double tempo2,Date fechaInicio, Date fechaFin){
        int cantidad = 0;

        if (tempo1>tempo2){
            double tempo = tempo1;
            tempo1 = tempo2;
            tempo2 = tempo;

        }

        boolean paso=false;
        if(tempo2!=0) {
            for (int i = 0; i < fechas.size(); i++) {
                if (fechas.get(i) != null) {
                    Date fechaActual = fechas.get(i).getKey();
                    if (fechaActual.compareTo(fechaInicio) >= 0 && fechaActual.compareTo(fechaFin) <= 0) {
                        HashTable<String, BinarySearchTree<Integer, Song>> paises = fechas.search(fechaActual);
                        if (paises != null) {
                            for (int j = 0; j < paises.size(); j++) {
                                NodeHash<String, BinarySearchTree<Integer, Song>> nodoPais = paises.get(j);
                                if (nodoPais != null) {
                                    BinarySearchTree<Integer, Song> cancionesPais = nodoPais.getValue();
                                    if (cancionesPais != null) {
                                        MyList<Song> canciones = cancionesPais.inOrderValue();
                                        for (int k = 0; k < canciones.size(); k++) {
                                            Song cancion = canciones.get(k);
                                            double tempo = cancion.getTempo();
                                            if (tempo >= tempo1 && tempo <= tempo2) {
                                                cantidad++;
                                                paso = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!paso){
                System.out.println("No se encontraron datos para las fechas dadas");
                return -1;
            }
        }else{
            System.out.println("No se encontraron datos para las fechas dadas");
            return -1;
        }

        return cantidad;
    }

    void menu(Spotify spoti){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el numero de su consulta: \n  1. Top 10 canciones en una fecha y país a tu eleccion. \n  2. Top 5 canciones que aparecen en más top 50 en un día a tu eleccion. \n  3. Top 7 artistas que más aparecen en los top 50 para un rango de fechas a tu eleccion. \n" +
                "  4. Cantidad de veces que aparece un artista específico en un top 50 en una fecha a tu eleccion. \n  5. Cantidad de canciones con un tempo en un rango específico para un rango específico de fechas a tu eleccion.");
        int opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion) {

            case 1:
                System.out.println("Ingrese la fecha en la que quiere consultar (YYYY-MM-DD):");
                Date fecha1 = spoti.askFecha(sc);
                String pais = spoti.askPais(sc);
                spoti.top10FechaYPais(fecha1, pais);
                volver(sc,spoti);

            case 2:
                System.out.println("Ingrese la fecha en la que quiere consultar (YYYY-MM-DD):");
                Date fecha2 = spoti.askFecha(sc);
                spoti.top50CanRep(fecha2);
                volver(sc,spoti);

            case 3:
                System.out.println("Ingrese la fecha de inicio del rango que quiere consultar (YYYY-MM-DD):");
                Date fecha3inicio = spoti.askFecha(sc);
                System.out.println("Ingrese la fecha de fin del rango que quiere consultar (YYYY-MM-DD):");
                Date fecha3fin = spoti.askFecha(sc);
                spoti.top7Artistas(fecha3inicio,fecha3fin);
                volver(sc, spoti);

            case 4:
                System.out.println("Ingrese la fecha en la que quiere consultar (YYYY-MM-DD):");
                Date fecha = spoti.askFecha(sc);
                System.out.println("Ingrese el artista que quiere consultar:");
                String artista = spoti.askArtista(sc);
                int veces = spoti.contarArtista(fecha, artista);
                if(veces!=-1) {
                    System.out.println("El artista " + artista + " aparece " + veces + " veces en el top 50 en la fecha " + fecha);
                }
                volver(sc, spoti);

            case 5:
                System.out.println("Ingrese el tempo inicial del rango que quiere consultar:");
                double tempo1 = spoti.askTempo(sc);
                System.out.println("Ingrese el tempo final del rango que quiere consultar:");
                double tempo2 = spoti.askTempo(sc);
                System.out.println("Ingrese la fecha de inicio del rango que quiere consultar (YYYY-MM-DD):");
                Date fechaInicio = spoti.askFecha(sc);
                System.out.println("Ingrese la fecha de fin del rango que quiere consultar (YYYY-MM-DD):");
                Date fechaFin = spoti.askFecha(sc);
                int cantidad = spoti.contarCancionesTempo(tempo1,tempo2,fechaInicio,fechaFin);
                if(cantidad!=-1) {
                    System.out.println("La cantidad de canciones con un tempo entre " + tempo1 + " y " + tempo2 + " entre las fechas " + fechaInicio + " y " + fechaFin + " es: " + cantidad);
                }
                volver(sc, spoti);

            default:
                System.out.println("Opción inválida.");
                break;
        }
    }

    Date askFecha(Scanner sc) {
        String fechaSC = sc.nextLine();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(fechaSC);
        } catch (ParseException e) {
            System.out.println("Fecha inválida. Inténtelo de nuevo.");
            return askFecha(sc);
        }
    }

    void volver(Scanner sc, Spotify spoti){
        System.out.println("\nDesea volver a realizar una consulta? \n 1- Si \n 2- No");
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
            return askPais(sc);
        }
    }

    String askArtista(Scanner sc) {
        String artista = sc.nextLine();
        if (artista != null && !artista.isEmpty()) {
            return artista;
        } else {
            System.out.println("Artista inválido. Inténtelo de nuevo.");
            return askArtista(sc);
        }
    }

    double askTempo(Scanner sc){
        String tempo = sc.nextLine();
        if (tempo != null){
            try{
                return Double.parseDouble(tempo);
            }catch (NumberFormatException e){
                System.out.println("Tempo invalido. Inténtelo de nuevo.");
                return askTempo(sc);
            }
        }else {
            System.out.println("Tempo invalido. Inténtelo de nuevo.");
            return askTempo(sc);
        }
    }

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static void printMemoryAndTime(long memoryBefore, long memoryAfter, long executionTime, long promedio) {
        System.out.println("\nMemoria utilizada antes de la consulta: " + memoryBefore + " bytes");
        System.out.println("Memoria utilizada después de la consulta: " + memoryAfter + " bytes");
        System.out.println("Memoria consumida por la consulta: " + (memoryAfter - memoryBefore) + " bytes");
        System.out.println();
        System.out.println("Tiempo de ejecución total de la consulta: " + executionTime + " nanosegundos");
        System.out.println("Tiempo de ejecución promedio: " + promedio + " nanosegundos");
    }
}





