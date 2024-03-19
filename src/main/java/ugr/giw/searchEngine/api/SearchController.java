package ugr.giw.searchEngine.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public List<Map<String, Object>> get(@RequestParam String query) {
        List<Map<String, Object>> results = List.of();
        try {
            results = searchService.search(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

}