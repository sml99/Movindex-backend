package ugr.giw.searchEngine.xml;

import java.util.List;

public class Movie {
    private String id;
    private String title;
    private List<String> genres;
    private List<String> actors;
    private List<String> writers;
    private String url;
    private String releaseYear;
    private String date;

    public Movie() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
