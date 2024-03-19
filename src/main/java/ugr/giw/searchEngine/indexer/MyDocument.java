package ugr.giw.searchEngine.indexer;


public class MyDocument {
    private String path;
    private String title;
    private String keyWords;
    private String id;



    public MyDocument() {
    }

    public MyDocument(String path, String title, String keyWords) {
        this.path = path;
        this.title = title;
        this.keyWords = keyWords;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
