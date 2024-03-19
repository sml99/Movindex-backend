package ugr.giw.searchEngine.indexer;

import ugr.giw.searchEngine.xml.Movie;
import ugr.giw.searchEngine.xml.MovieParser;

import java.util.List;

public class IndexerCLI {

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: java -jar indexer.jar <docs-path> <stopwords-path> <index-path>");
            System.exit(1);
        }

        // Get command-line arguments
        String xmlFilesPath = args[0];
        String stopWordsPath = args[1];
        String indexPath = args[2];

        System.out.println("Indexing documents from " + xmlFilesPath + " to " + indexPath + " using stopwords from " + stopWordsPath);

        // Create your indexer instance
        CustomIndexer indexer = new CustomIndexer(indexPath, stopWordsPath);

        // Parse the XML file to extract movies
        MovieParser parser = new MovieParser();
        List<Movie> movies = parser.parseMovies(xmlFilesPath);

        // Index each movie
        for (Movie movie : movies) {
            MyDocument doc =  parser.createDocumentFromTopic(movie);
            System.out.println("Indexing " + doc.getTitle());
            indexer.indexDocument(doc);
        }

        // Close the IndexWriter
        indexer.close();

        System.out.println("Indexing complete!");
    }

}