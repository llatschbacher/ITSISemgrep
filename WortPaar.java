package at.ac.tgm.jertl2;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Die Klasse WortPaar stellt ein WortPaar bestehend aus dem Wort und dem dazugehörigen Bild als URL da.
 * @author jakob ertl
 * @version 02.10.2023
 */
public class WortPaar {

    private String wort;
    private String url;

    public WortPaar() {}
    public WortPaar(String wort, String url) {
        if(this.check(wort,url)) {
            this.wort = wort;
            this.url = url;
        } else {
            this.wort = "";
            this.url = "";
        }

    }

    /**
     * Überprüft ob das Wort und die URL den gegeben Kriterien entspricht
     * @param wort
     * @param url
     * @return true oder false
     */
    public boolean check(String wort, String url) {
        if(wort==null || wort.isEmpty()) {
            return false;
        }
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException ex) {
            return false;
        }

    }

    public String getWort() {
        return this.wort;
    }

    public String getUrl() {
        return this.url;
    }

}
