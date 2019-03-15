package os.web.verifier.model;

public interface Application {
    String getVersion(String webPageHTML);
    String getURL();
}
