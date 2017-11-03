package de.projekteanderschule.weinert.ltbcontrol;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by ralf on 19.05.17.
 */
/*
public class LoadCSV  extends Activity {
    private final static String KlassenName = "LoadCSV";
    private static String fileName = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_webview);
        String methodenName = KlassenName + ".onCreate";
        String logText = "Versuche eine Datei zu laden.";
        Logger.log( "info", methodenName, logText);
        String data = "das soll gespeichert wreden";


    }// onCreate

    public void PlayWithRawFiles() throws IOException {
        String methodenName = KlassenName + ".PlayWithRawFiles";
        String logText = "Versuche eine Datei zu laden.";
        Logger.log( "info", methodenName, logText);

        String str="";
        File file = new File("assets/allowdEmail"); // fileName;//ConfigData.getAllowedEmailCSV();

        StringBuffer buf = new StringBuffer();

        Logger.log( "info", methodenName, "vor InputStream.");
        //InputStream is = this.getResources().getAssets().open(file, AssetManager.ACCESS_STREAMING); //openRawResource(R.raw.ashraf);
       // InputStream is = this.getResources().getAssets().open("allowdEmail"); //openRawResource(R.raw.ashraf);

        InputStreamReader is = new InputStreamReader(this.getResources().getAssets().open("allowedEmail.csv"), "UTF-8"); //openRawResource(R.raw.ashraf);

        //InputStream is = this.getResources().openRawResource(R.raw.allowed_email);
        Logger.log( "info", methodenName, "nach InputStream.");
        //getResources().getResources().getAssets().open(file, AssetManager.ACCESS_STREAMING)
        //BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        BufferedReader reader = new BufferedReader(is);
        Logger.log( "info", methodenName, "nach BufferedReader.");
        if (is!=null) {
            Logger.log( "info", methodenName, "in If is!=null.");
            while ((str = reader.readLine()) != null) {
                buf.append(str + "\n" );
            }
        }
        Logger.log( "info", methodenName, "nach If");
        is.close();
        String viewHtmlText = buf.toString();

        WebView webview = (WebView) findViewById(R.id.log_WebView);
        webview.loadData(viewHtmlText, "text/html", null);


    }
    public void setFileName(String file){
        String methodenName = KlassenName + ".setFileName";
        String logText = "Setzte CSV-FileName auf " + file;
        Logger.log( "info", methodenName, logText);
        fileName = file;

       // PlayWithRawFiles();
    }
    public void writeToFile(String data)   {
        String methodenName = KlassenName + ".writeToFile";
        String logText = "Versuche zu beschreiben.";
        Logger.log( "info", methodenName, logText);


        final File path = Environment.getDataDirectory();
        // Make sure the path directory exists.
        Logger.log( "info", methodenName, "Gibt es den Pfad?" + path.toString());



        final File file = new File(path, "config.txt");

        // Save your stream, don't forget to flush() it before closing it.
        Logger.log( "info", methodenName, "schreibe auf config.txt " + file.toString());
        try
        {
            file.createNewFile();
            Logger.log( "info", methodenName, "schreibe auf file: " + file.toString());
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
           // Log.e("Exception", "File write failed: " + e.toString());
            Logger.log( "error", methodenName,  "File write failed: " + e.toString());
        }
    }
}
*/