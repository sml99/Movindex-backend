package ugr.giw.searchEngine.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;
import java.util.Scanner;

public class SearchEngine {

    public static void main(String[] args) throws Exception {
        String indexDirectoryPath = "index";
        String field =  "keyWords";
        Analyzer analyzer = new StandardAnalyzer();
        Scanner scanner = new Scanner(System.in);

        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexDirectoryPath)));
        IndexSearcher searcher = new IndexSearcher(indexReader);
        QueryParser parser = new QueryParser(field, analyzer);

        while (true) {
            System.out.print("Enter query: ");
            String queryText = scanner.nextLine().toLowerCase();

            Query query = parser.parse(queryText);

            TopDocs results = searcher.search(query, 10); // Retrieve top 10 results
            ScoreDoc[] hits = results.scoreDocs;

            System.out.println("Found " + hits.length + " results.");
            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);
                System.out.println("Title: " + doc.get("title"));
            }
        }
    }
}
