package os.web.verifier.model.Apps;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import os.web.verifier.model.Application;

public class AdobeFlashPlayer implements Application {
    @Override
    public String getVersion(String webPageHTML) {
        Document doc = Jsoup.parse(webPageHTML);
        if(doc.getElementById("AUTO_ID_columnleft_p_version")!=null){
            return doc.getElementById("AUTO_ID_columnleft_p_version").text();
        }
        return "";
    }

    @Override
    public String getURL() {
        return "https://get.adobe.com/flashplayer/";
    }
}
