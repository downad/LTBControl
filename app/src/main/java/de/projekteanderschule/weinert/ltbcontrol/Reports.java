package de.projekteanderschule.weinert.ltbcontrol;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by ralf on 22.05.17.
 */

public class Reports extends AppCompatActivity {

    private String KlassenName = "Reports";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports);
        String methodenName = KlassenName + ".onCreate";
        String logText = "Lege die Activity Reports an.";
        Logger.log( "info", methodenName, logText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String methodenName = KlassenName + ".onStart";
        String logText = "Berichtsseite ist aktiv";
        Logger.log( "info", methodenName, logText);

        // AllowedEmail
        Button bAllowedEmailList = (Button) findViewById(R.id.btn_listAllowedEmail);
        bAllowedEmailList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String methodenName = KlassenName + ".bAllowedEmailList";
                String reportHTMLString = AllowedEmailAdress.getHTML2printAllowedEmailList();
                printReport("Erlaubte Emailadressen", reportHTMLString);
            }
        });

        // Studentlist
        Button bStudentList = (Button) findViewById(R.id.btn_studenList);
        bStudentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String methodenName = KlassenName + ".bStudentList";
                String reportHTMLString = LTBStudents.getHtml2PrintStudentList(0, "Alle");
                printReport("Liste der Schüler", reportHTMLString);
            }
        });
    }
    private void printReport( String headline, String body ) {
        //WebView webview = (WebView) findViewById(R.id.webView_reports);
        WebView reportWebview = (WebView) findViewById(R.id.webView_reports);
        String report = HTMLformater.createHtml(headline, body);
        reportWebview.loadData(report, "text/html", null);
    }
}
