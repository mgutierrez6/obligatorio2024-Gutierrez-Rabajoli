package spotify;

import spotify.csvReader.CsvReader;

public class Main {

    public static void main(String[] args) {
        Spotify spoti = new Spotify();
        spoti.menu(spoti); // menu adentro de spotify
    }
}
