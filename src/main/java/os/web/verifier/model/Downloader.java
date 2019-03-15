package os.web.verifier.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class Downloader {
    public Document getWebPage(String url) throws IOException {
        return Jsoup.parse(new URL(url), 5000 );
    }
    public String getWebPagHTML(String url) throws IOException {
        return getWebPage(url).outerHtml();
    }
}
