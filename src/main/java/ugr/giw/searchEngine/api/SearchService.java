package ugr.giw.searchEngine.api;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.*;

@Service
public class SearchService {
    public List<Map<String, Object>> search(String urlQuery) throws Exception {
        String indexDirectoryPath = "index";
        String field =  "keyWords";
        Analyzer analyzer = new StandardAnalyzer();

        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexDirectoryPath)));
        IndexSearcher searcher = new IndexSearcher(indexReader);
        QueryParser parser = new QueryParser(field, analyzer);

        Query query = parser.parse(urlQuery);

        TopDocs results = searcher.search(query, 10); // Retrieve top 10 results
        ScoreDoc[] hits = results.scoreDocs;
        List<Map<String, Object>> resultsArray = new ArrayList<>();


        System.out.println("Found " + hits.length + " results.");
        for (ScoreDoc hit : hits) {
            Document doc = searcher.doc(hit.doc);

            Map<String, Object> movieInfo = new HashMap<>();
            movieInfo.put("title", doc.get("title"));
            movieInfo.put("url", doc.get("path"));
            movieInfo.put("keyWords", doc.get("keyWords"));
            movieInfo.put("id", doc.get("id"));

            resultsArray.add(movieInfo);
        }
        return resultsArray;
    }
}