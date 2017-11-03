package de.projekteanderschule.weinert.ltbcontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;



/*
 * Created by ralf on 16.05.17.
 */

public class LogView extends AppCompatActivity {

    private String KlassenName = "LogView";
    private static String ViewHtmlPage;

    public static void setViewText (String headline, String body) {
        ViewHtmlPage = HTMLformater.createHtml(headline, body);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_webview);
        String methodenName = KlassenName + ".onCreate";
        String logText = "erzeuge ein html-View der Logeinträge.";
        Logger.log( "info", methodenName, logText);

        WebView webview = (WebView) findViewById(R.id.log_WebView);
        webview.loadData(ViewHtmlPage, "text/html", null);

    }
}
