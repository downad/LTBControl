package de.projekteanderschule.weinert.ltbcontrol;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by ralf on 29.05.17.
 */

public class ShowSettings  extends AppCompatActivity {
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_settings);

        textView = (TextView) findViewById(R.id.textView_settings);
        //displaySharedPreferences();
    }


    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

//        String ChoosenClass = prefs.getString("ChoosenClass", "Alle");

        String host = prefs.getString("host", "Default Host");
        String username = prefs.getString("username", "Default NickName");
        String passw = prefs.getString("password", "Default Password");
        boolean AutoCheckQRMail = prefs.getBoolean("AutoCheckQRMail", false);
        String AutoCheckInterval = prefs.getString("AutoCheckInterval", "0");


        String ChoosenClass = ConfigData.getChoosenClass();


        StringBuilder builder = new StringBuilder();

        builder.append("\n<b>gewählte Klasse</b>");
        builder.append("Klassen: " + ChoosenClass + "\n");

        builder.append("\n<b>Konfiguration des Mailkonto</b>");
        builder.append("Host: " + host + "\n");
        builder.append("Username: " + username + "\n");
        builder.append("Password: " + passw + "************* \n");

        builder.append("\n<b>service Mailchecker</b>");
        builder.append("AutoCheck: " + AutoCheckQRMail + "\n");
        builder.append("Interval: " + AutoCheckInterval + " Minuten \n");

        builder.append("\n\n\n");



        textView.setText(builder.toString());
    }
}
