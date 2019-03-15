package os.web.verifier;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import os.web.verifier.model.Application;
import os.web.verifier.model.AppsListReader;
import os.web.verifier.model.Downloader;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class RootController {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    private AppsListReader apps;

    @GetMapping("/")
    @ResponseBody
    public String root() {
        StringBuilder sb = new StringBuilder("<h5>Hello, I'm OK</h5>");
        sb.append("Check one by one<a href=\"/list\"> Click </a>");
        sb.append("<p/>");
        sb.append("or check all at once <a href=\"/all\"> Click </a>");
        return sb.toString();
    }

    @GetMapping("/list")
    @ResponseBody
    public String list() {
        StringBuilder sb = new StringBuilder();
        List<String> checkNames = apps.getAllClassNames();
        sb.append("<p>").append("List of Applications' names:").append("</p>");
        for (int i = 0; i < checkNames.size(); i++) {
            sb.append("<p id=").append(i).append(">")
                    .append("<a href=\"").append("/get/").append(checkNames.get(i)).append("\">")
                    .append(checkNames.get(i)).append("</a></p>");
        }

        return sb.toString();
    }

    @GetMapping("/get/{clazz}")
    @ResponseBody
    public String version(@PathVariable String clazz) {
        return getVersion(clazz);
    }

    @GetMapping("/all")
    @ResponseBody
    public String all() {
        Document doc = Jsoup.parseBodyFragment("");
        logger.log(Level.INFO, doc.outerHtml());

        List<String> names = apps.getAllClassNames();

        doc.body().appendElement("table");

        Element table = doc.getElementsByTag("table").first();

        for (String name : names) {
//            Element tr = Jsoup.parseBodyFragment("<tr><td>" + name + "</td><td>" + getVersion(name) + "</td></tr>");
            table.appendElement("tr");
            table.getElementsByTag("tr").first().appendElement("td");
            table.getElementsByTag("tr").first().appendElement("td");
            Element first = table.getElementsByTag("td").first();
            Element last = table.getElementsByTag("td").last();

            first.appendText(name).attr("width", "50%");
            last.appendText(getVersion(name)).attr("width", "50%");
        }

        logger.log(Level.SEVERE, doc.outerHtml());
        return doc.outerHtml();
    }

    public String getVersion(String clazz) {
        String body = "";
        Downloader downloader = new Downloader();
        try {
            Application application = apps.getClassByName(clazz);
            logger.log(Level.SEVERE, application.toString());

            String url = application.getURL();
            logger.log(Level.SEVERE, url);

            body = application.getVersion(downloader.getWebPagHTML(url));
            logger.log(Level.SEVERE, body);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return body;
    }
}
