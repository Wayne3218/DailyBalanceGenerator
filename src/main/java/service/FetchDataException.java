package service;

public class FetchDataException extends Exception {
    private String urlUsed;

    public FetchDataException(String urlUsed) {
        super();
        this.urlUsed = urlUsed;
    }

    public String getUrlUsed() {
        return urlUsed;
    }
}
