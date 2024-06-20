package spotify.entities;
import java.util.Date;
import TADs.linkedlist.MyList;


public class Song {
    private String spotify_id;
    private String name;
    private MyList<Artist> artist;
    private Integer daily_rank;
    private int daily_movement;
    private int weekly_movement;
    private String country;
    private Date snapshot_date;
    private int popularity;
    private boolean is_explicit;
    private int duration_ms;
    private String album_name;
    private Date album_release_date;
    private double danceability;
    private double energy;
    private int key;
    private double loudness;
    private int mode;
    private double speechiness;
    private double acousticness;
    private double instrumentalness;
    private double liveness;
    private double valence;
    private double tempo;
    private int time_signature;

    public String setSpotify_id(String spotify_id) {
        this.spotify_id = spotify_id;
        return spotify_id;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public void setArtist(MyList<Artist> artist) {
        this.artist = artist;
    }

    public Integer setDaily_rank(Integer daily_rank) {
        this.daily_rank = daily_rank;
        return this.daily_rank;
    }

    public void setDaily_movement(int daily_movement) {
        this.daily_movement = daily_movement;
    }

    public void setWeekly_movement(int weekly_movement) {
        this.weekly_movement = weekly_movement;
    }

    public String setCountry(String country) {
        this.country = country;
        return this.country;
    }

    public Date setSnapshot_date(Date snapshot_date) {
        this.snapshot_date = snapshot_date;
        return this.snapshot_date;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setIs_explicit(boolean is_explicit) {
        this.is_explicit = is_explicit;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }

    public void setAlbum_release_date(Date album_release_date) {
        this.album_release_date = album_release_date;
    }

    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setSpeechiness(double speechiness) {
        this.speechiness = speechiness;
    }

    public void setAcousticness(double acousticness) {
        this.acousticness = acousticness;
    }

    public void setLiveness(double liveness) {
        this.liveness = liveness;
    }

    public void setInstrumentalness(double instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public void setValence(double valence) {
        this.valence = valence;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public void setTime_signature(int time_signature) {
        this.time_signature = time_signature;
    }

    public String getName() {
        return name;
    }

    public MyList<Artist> getArtist() {
        return artist;
    }

    public double getTempo() {
        return tempo;
    }

    public String getSpotify_id() {
        return spotify_id;
    }
}
