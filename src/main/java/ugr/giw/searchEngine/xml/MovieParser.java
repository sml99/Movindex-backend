package ugr.giw.searchEngine.xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import ugr.giw.searchEngine.indexer.MyDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// ... 

public class MovieParser {
    private static final Pattern URL_PATTERN = Pattern.compile("http://www.imdb.com/Title\\?\"(.*)\" \\((\\d{4})\\) \\{\\((.*)\\)}");
    private static final Pattern TITLE_PATTERN = Pattern.compile("\"'(.*)'\" \\((\\d{4})\\) \\{\\((.*)\\)}");

    public List<Movie> parseMovies(String xmlDirectoryPath) throws JDOMException, IOException {
        File directory = new File(xmlDirectoryPath);
        File[] xmlFiles = directory.listFiles((dir, name) -> name.endsWith(".xml"));
        List<Movie> movies = new ArrayList<>();

        if (xmlFiles == null) throw new IllegalArgumentException("No XML files found");

        System.out.println("Found " + xmlFiles.length + " XML files");

        for (File xmlFile : xmlFiles) {
            System.out.println("Parsing " + xmlFile.getName());
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(xmlFile);

            Element movieElement = document.getRootElement();
            if (movieElement == null) continue; // Skip if movie element is not present

            Movie movie = new Movie();
            String fileNameWithExtension = xmlFile.getName();
            String fileNameWithoutExtension = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'));
            movie.setId(fileNameWithoutExtension);

            String url = movieElement.getChildText("url");
            String titleEl = movieElement.getChildText("title");
            String title = null;
            if (url != null) {
                Matcher matcher = URL_PATTERN.matcher(url);
                if (matcher.find()) {
                    title = matcher.group(1);
                    String year = matcher.group(2);
                    String date = matcher.group(3);


                    // Format the title as needed
                    String formattedUrl = "http://www.imdb.com/Title?" + title;
                    movie.setTitle(title.toLowerCase());
                    movie.setReleaseYear(year);
                    movie.setDate(date);
                    movie.setUrl(formattedUrl);
                } else {
                    Matcher matcher2 = TITLE_PATTERN.matcher(titleEl);
                    if (matcher2.find()) {
                        title = matcher2.group(1);
                        String year = matcher2.group(2);
                        String date = matcher2.group(3);

                        // Format the title as needed
                        String formattedTitle = title + " (" + year + ") - " + date;
                        movie.setTitle(formattedTitle.toLowerCase());
                        movie.setUrl(url);
                    }

                }
            }

            if (title == null || title.isEmpty()) {
                title = titleEl;
                movie.setTitle(title.toLowerCase());
            }


            // Parse writers
            Element overviewElement = movieElement.getChild("overview");
            if (overviewElement != null) {
                Element writersElement = overviewElement.getChild("writers");
                if (writersElement != null) {
                    List<Element> writerElements = writersElement.getChildren("writer");
                    List<String> writers = new ArrayList<>();
                    for (Element writerElement : writerElements) {
                        writers.add(writerElement.getText().toLowerCase());
                    }
                    movie.setWriters(writers);
                }

                // Parse genres
                Element genresElement = overviewElement.getChild("genres");
                if (genresElement != null) {
                    List<Element> genreElements = genresElement.getChildren("genre");
                    List<String> genres = new ArrayList<>();
                    for (Element genreElement : genreElements) {
                        genres.add(genreElement.getText().toLowerCase());
                    }
                    movie.setGenres(genres);
                }
            }

            // Parse actors
            Element castElement = movieElement.getChild("cast");
            if (castElement != null) {
                Element actorsElement = castElement.getChild("actors");
                if (actorsElement != null) {
                    List<Element> actorElements = actorsElement.getChildren("actor");
                    List<String> actors = new ArrayList<>();
                    for (Element actorElement : actorElements) {
                        String actorName = actorElement.getChildText("name").toLowerCase();
                        actorName = actorName.replace(",", ""); // Remove comma
                        actors.add(actorName);
                    }
                    movie.setActors(actors);
                }
            }
            movies.add(movie);
        }

        return movies;
    }


    public MyDocument createDocumentFromTopic(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }

        MyDocument doc = new MyDocument();

        String id = movie.getId();
        if (id != null) {
            doc.setId(id);
        }

        String title = movie.getTitle();
        if (title != null) {
            doc.setTitle(title);
        }

        List<String> writers = movie.getWriters();
        List<String> genres = movie.getGenres();
        List<String> actors = movie.getActors();

        StringBuilder keyWords = new StringBuilder();
        if (title != null) {
            keyWords.append(title).append(" ");
        }
        if (writers != null) {
            keyWords.append(String.join(" ", writers).trim());
        }
        if (genres != null) {
            keyWords.append(String.join(" ", genres).trim());
        }
        if (actors != null) {
            keyWords.append(String.join(" ", actors).trim());
        }
        if (movie.getReleaseYear() != null) {
            keyWords.append(" ").append(movie.getReleaseYear());
        }
        if (movie.getDate() != null) {
            keyWords.append(" ").append(movie.getDate());
        }

        String url = movie.getUrl();
        if (url != null) {
            doc.setPath(url);
        }

        doc.setKeyWords(keyWords.toString());


        System.out.println(movie.getUrl());

        return doc;
    }

}

