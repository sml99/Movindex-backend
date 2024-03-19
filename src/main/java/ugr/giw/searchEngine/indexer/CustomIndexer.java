package ugr.giw.searchEngine.indexer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class CustomIndexer {
    private final IndexWriter indexWriter;

    // Constructor
    public CustomIndexer(String indexDirectoryPath, String stopWordsFilePath) throws IOException {
        // Open the index directory for storing the index
        Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));

        // Load the stop words
        CharArraySet stopWords = CharArraySet.copy(loadStopWords(stopWordsFilePath));

        // Create a standard English analyzer with custom stop words
        Analyzer analyzer = new EnglishAnalyzer(stopWords);

        // Configure how the index is written
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        // Create the IndexWriter
        indexWriter = new IndexWriter(indexDirectory, config);
    }

    // Function to load stop words from a file
    private Set<String> loadStopWords(String stopWordsFilePath) throws IOException {
        Set<String> stopWords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(stopWordsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line.trim());
            }
        }
        return stopWords;
    }

    // Function to index a document
    public void indexDocument(MyDocument doc) throws IOException {
        Document luceneDoc = new Document();

        // Add fields to the Lucene document
        if (doc.getId() != null)
            luceneDoc.add(new StringField("id", doc.getId(), Field.Store.YES));
        if (doc.getTitle() != null)
            luceneDoc.add(new TextField("title", doc.getTitle(), Field.Store.YES));
        if (doc.getKeyWords() != null)
            luceneDoc.add(new TextField("keyWords", doc.getKeyWords(), Field.Store.YES));
        if (doc.getPath() != null)
            luceneDoc.add(new StringField("path", doc.getPath(), Field.Store.YES));

        // Add the document to the index
        indexWriter.addDocument(luceneDoc);
    }

    // Remember to close the IndexWriter after indexing is complete
    public void close() throws IOException {
        indexWriter.close();
    }
}
