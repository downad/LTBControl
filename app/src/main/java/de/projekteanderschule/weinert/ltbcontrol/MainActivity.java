package de.projekteanderschule.weinert.ltbcontrol;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.File;

/*
    Projekt LTB-Control
    Beginn 19.04.2017
    by ralf.weinert@gmx.de

            Versionen
		0.3 - neue Main 									20.04.2017
            0.4 - Auswahlmenü									23.04.2017
            0.5	- Einlesen von Studentliste und Allowed EMail	24.04.2017
            0.6 - Ausgabe der Berichte in einen Frame			25.04.2017
            0.7 - Ausgabe als Datei 							25.04.2017
            ..4 - neues htmlformat, bessere pdf-Export, jarPath	27.04.2017
            ..5 - Klassen sind wählbar							27.04.2017
            ..6 - Begin mit der Dokumentation
		0.8.0 - Ende der Dokumentation
		0.8.1 - Neues GUI									    09.05.2017
            0.8.2 - überführen auf Andriod Studio               15.05.2017
            0.8.3 - settings eingebaut
            0.9.1 - Schüler gehöht zu Liste a, b und / oder c..
            0.9.2 - mehrere Schüler CSV-Dateien, einspielen über Webmailer
		1.0 - Finale tests

*/
public class MainActivity extends AppCompatActivity {
    private final static String KlassenName = "MainActivity";
    private static String version = "0.8.3"; // - 15.05.2017";
    private static File LTBAPPPATH;

    private String choosenClass = "Alle";
    //private String textChooesenClassView = "<p align='center'> Gewählte Klasse: <br /> <b>" + choosenClass + "</b></p>";
    private final String textMailView = HTMLformater.htmlHeadline("Checke Mails",3) + "LTB-Mails einlesen und bearbeiten.";
    private final String textBerichteView = HTMLformater.htmlHeadline("Schülerlisten",3) + "Listen erstellen, Klassen wählen";
    private final String textReportView = HTMLformater.htmlHeadline("Berichte",3) + "Eine Seite mit weiteren Berichte";
    private final String textConfigView = HTMLformater.htmlHeadline("Config",3) + "Weiter Einstellungen, Laden von cvs-Listen,...";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Wurde onbCreate schon ausgeführt?
        if (savedInstanceState != null) {
            return;
        }
        String methodenName = KlassenName + ".onCreate";
        // AppPath
        LTBAPPPATH = new File(getBaseContext().getExternalFilesDir(null), MainActivity.getVersion());

        // Initialiseire
        initLTB();

        // befülle die TextView
        TextView textViewVersion = (TextView) findViewById(R.id.version);
        textViewVersion.setText("Version " + getVersion());

        TextView textViewMail = (TextView) findViewById(R.id.textView_checkMail);
        textViewMail.setText(Html.fromHtml(textMailView));

        TextView textViewBericht = (TextView) findViewById(R.id.textView_Berichte);
        textViewBericht.setText(Html.fromHtml(textBerichteView));
        setChoosenClass(choosenClass);


        TextView textViewReport = (TextView) findViewById(R.id.textView_Reports);
        textViewReport.setText(Html.fromHtml(textReportView));

        TextView textViewConfig = (TextView) findViewById(R.id.textView_Config);
        textViewConfig.setText(Html.fromHtml(textConfigView));


        Logger.setUseConsole(true /* true = use ConsoleLof */);
        String logText = "Initialisiere MainActivity";
        //Logger.setLogfileMode(false); // erzeuge eine Neue LogDatei
        Logger.log( "info", methodenName, logText);
        //Logger.consoleLog( "info", methodenName, logText);
        //Logger.setLogfileMode(false); // Setzte APPEND für die Log-Datei

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.actionmenu_settings_new) {
            Intent intent = new Intent(MainActivity.this, LtbEinstellungenActivity.class);
            //intent.setClassName(this, LtbEinstellungenActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.actionmenu_show_settings) {
            //ShowSettings.displaySharedPreferences();
            Intent intent = new Intent(MainActivity.this,
                    ShowSettings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        String methodenName = KlassenName + ".onStart";
        String logText = "Starte das Programm in der Version: " + getVersion();
        Logger.log( "info", methodenName, logText);


        // Button - Check-QRMail
        Button bCheckMail = (Button) findViewById(R.id.checkMail);
        bCheckMail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            String methodenName = KlassenName + ".checkMail";

            String logText = "Rufe checkMail auf.";
            Logger.log( "info", methodenName, logText);
            GetEmailList mailCheck = new GetEmailList();
            mailCheck.execute();
            }
        });

        // Button - abgegebenen LTB
        Button bDropedLTB = (Button) findViewById(R.id.btn_dropedLTB);
        bDropedLTB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String methodenName = KlassenName + ".bdropedLTB";
                String logText = "Ausgabe der abgegebenen LTB.";
                Logger.log( "info", methodenName, logText);

                String headline = "Abgegebenen LTB";
                String body =LTBStudents.getHtml2PrintStudentList(1, choosenClass);
                LogView.setViewText(headline, body);

                startActivity(new Intent(MainActivity.this, LogView.class));
            }
        });
        // Button - fehlende LTB
        Button bNotDropedLTB = (Button) findViewById(R.id.btn_notDropedLTB);
        bNotDropedLTB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String methodenName = KlassenName + ".bdropedLTB";
                String logText = "Ausgabe der fehlenden LTB.";
                Logger.log( "info", methodenName, logText);

                String headline = "Fehlende LTB";
                String body =LTBStudents.getHtml2PrintStudentList(2, choosenClass);
                LogView.setViewText(headline, body);

                startActivity(new Intent(MainActivity.this, LogView.class));
            }
        });

        // Button - Berichtsauswahl
        Button bReports = (Button) findViewById(R.id.btn_Berichte);
        bReports.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String methodenName = KlassenName + ".bReports";
                String logText = "Rufe Berichte auf.";
                Logger.log( "info", methodenName, logText);

                startActivity(new Intent(MainActivity.this, Reports.class));
            }
        });


        // Button - Config
        Button bConfig= (Button) findViewById(R.id.btn_config);
        bConfig.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String methodenName = KlassenName + ".bConfig";
                String logText = "Rufe Config auf.";
                Logger.log( "info", methodenName, logText);

                //startActivity(new Intent(MainActivity.this, Reports.class));
            }
        });


        // Button - Log-Files ausgeben
        Button bGetLog = (Button) findViewById(R.id.get_log);
        bGetLog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            String methodenName = KlassenName + ".get_log";
            String logText = "rufe LogView auf.";
            Logger.log( "info", methodenName, logText);

            String headline = "Log-Liste";
            String body = Logger.getHtml2PrintLogStorage("INFO");
            LogView.setViewText(headline, body);

            startActivity(new Intent(MainActivity.this, LogView.class));
            }
        });

        // Button - Exit Programm
        Button bExitLTB = (Button) findViewById(R.id.ExitLTB);
        bExitLTB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            String methodenName = KlassenName + ".ExitLTB";
            String logText = "Auf Wiedersehen";
            Logger.log( "info", methodenName, logText);

            finish();
            System.exit(0);
            }
        });

    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        String methodenName = KlassenName + ".onDestroy";
        String logText = "Auf Wiedersehen";
        Logger.log( "info", methodenName, logText);
    }
    private void initLTB() {
        String methodenName = KlassenName + ".initLTB";
        // fütter die Preferences mit defaults
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //String a = prefs.getString("host", "Default NickName");
        // Bis es möglich ist per Mail csv-Listen einzuspielen
        saveAllowedEmail(); //erzeuge allowedEmail.csv
        saveStudten();      //erzeuge eine schueler.csv

        //initialisiere AllowedEmail
        AllowedEmailAdress.readLtbAllowedEmail(ConfigData.getAllowedEmailCSV());

        // initialisiere LTBStudents
        LTBStudents.readLtbStudent(ConfigData.getSchuelerCSV());
    }
    private void setChoosenClass(String klasse){
        choosenClass = klasse;
        String textChooesenClassView = "<p align='center'> Gewählte Klasse: <br /> <b>" + choosenClass + "</b></p>";
        WebView webViewChoosenClass= (WebView) findViewById(R.id.webView_choosenClass);
        //webViewChoosenClass.loadData(HTMLformater.createHtmlWithoutHeadline(textChooesenClassView), "text/html", null);
        //WebView webViewChoosenClass= (WebView) findViewById(R.id.webView_choosenClass);
        //webViewChoosenClass.loadData(HTMLformater.createHtmlWithoutHeadline(textChooesenClassView), "text/html", "UTF-8");
        webViewChoosenClass.loadDataWithBaseURL("fake://fake.de", HTMLformater.createHtmlWithoutHeadline(textChooesenClassView), "text/html", "UTF-8", null);
    }
    private void saveAllowedEmail(){
        String fileName = ConfigData.getAllowedEmailCSV();
        String saveDataCSV = "";
        String[] sData = new String[3];
        sData[0] = "ralf.weinert@gmx.de;Ralf Weinert";
        sData[1] = "\nweinert.pad.rw@googlemail.com;Ralf Weinert";
        sData[2] = "\nralf.weinert@seewiesenschule.de;Ralf Weinert";
        for ( int i = 0; i< sData.length; i++) {
            saveDataCSV = saveDataCSV + sData[i];
        }

        SaveDataToFile a = new SaveDataToFile();
        a.SaveData(fileName,saveDataCSV);

    }

    /*
     * getter-Methode
     *
     * @return Versionsnummer
     *
     */
    public static String getVersion()  {
        return version;
    }
    public static File getLTBAPPPATH(){
        return LTBAPPPATH;
    }
    private void saveStudten(){
        String methodenName = KlassenName + ".saveStudten";
        String fileName = ConfigData.getSchuelerCSV();

        String[] sData = new String[198];
        sData[0] = "5a;Amara;Oelguel";
        sData[1] = "5a;Anastasia;Zivkovic";
        sData[2] = "5a;Lena;Klemeyer";
        sData[3] = "5a;Elvira;Jashari";
        sData[4] = "5a;Anesa;Majkofci";
        sData[5] = "5a;Jasmin;Maticki";
        sData[6] = "5a;Sebastian;Boylan";
        sData[7] = "5a;Maximilian;Braun";
        sData[8] = "5a;Erandi;Dula";
        sData[9] = "5a;Luca;Fiala";
        sData[10] = "5a;Roberto;Gueli";
        sData[11] = "5a;Gino;Hentz";
        sData[12] = "5a;Ardis;Krajsek";
        sData[13] = "5a;Raphael;Levin";
        sData[14] = "5a;Jannis;Paraskevopoulos";
        sData[15] = "5a;Tim;Tischinger";
        sData[16] = "5a;Fortuna;Kerelaj";
        sData[17] = "5a;Fivia-Alexandra;Stirlici";
        sData[18] = "5a;Sasa;Stojanovic";
        sData[19] = "5a;Stefan;Stojanovic";
        sData[20] = "5a;Kara;Wahler";
        sData[21] = "5a;Semjon;Matysek";
        sData[22] = "5a;Carolina;Torre";
        sData[23] = "5a;Christos;Topalidis";
        sData[24] = "5a;Meriban;Osmani";
        sData[25] = "5b;Celina;Tegeler";
        sData[26] = "5b;Sarra;Delalic";
        sData[27] = "5b;Toni;Karnstedt";
        sData[28] = "5b;Sonya;Klemeyer";
        sData[29] = "5b;Rabia-Ilayda;Narmanli";
        sData[30] = "5b;Zekya;Sahin";
        sData[31] = "5b;Bersu;Songur";
        sData[32] = "5b;Johannes;Grotrian-Steinweg";
        sData[33] = "5b;Henrik;Kronewitter";
        sData[34] = "5b;Julian;Krumrey";
        sData[35] = "5b;Estefano;Messerschmidt";
        sData[36] = "5b;Hajris;Redzovic";
        sData[37] = "5b;Hannes;Roehrle";
        sData[38] = "5b;Ben;Schaefer";
        sData[39] = "5b;Thorben;Schmidtke";
        sData[40] = "5b;Lion;Wagner";
        sData[41] = "5b;Fynn;Messner";
        sData[42] = "5b;VladValentin;Varga";
        sData[43] = "5b;Luca;Jaspert";
        sData[44] = "5b;Valentin;Wessler";
        sData[45] = "5b;Patricia;Ehmann";
        sData[46] = "5b;Dorsa;Farahani";
        sData[47] = "5b;Ankur;Sharma";
        sData[48] = "5b;Suaad;Bakhet";
        sData[49] = "5b;Ziad;El-Hassan";
        sData[50] = "6a;Annika;Lauber";
        sData[51] = "6a;Melissa;Serbest";
        sData[52] = "6a;Nele;Spitzenberger";
        sData[53] = "6a;Tim;Hoeschele";
        sData[54] = "6a;Onurcan;Oezcan";
        sData[55] = "6a;LynnVerena;Saad";
        sData[56] = "6a;Canay;Cakmak";
        sData[57] = "6a;Jasmin;Harara";
        sData[58] = "6a;Katharina;Grotrian-Steinweg";
        sData[59] = "6a;Oliver;Spiess";
        sData[60] = "6a;Martin;Stech";
        sData[61] = "6a;Pauline;Stech";
        sData[62] = "6a;Timucin;Guemues";
        sData[63] = "6a;Aaliyah;Guemues";
        sData[64] = "6a;Denisa;Stîrlici";
        sData[65] = "6a;Amina;Jadadic";
        sData[66] = "6a;Samed;DelalicMutlu";
        sData[67] = "6a;Nino;Feierabend";
        sData[68] = "6a;Efe;Ceren";
        sData[69] = "6b;DanaFranziska;Moeller";
        sData[70] = "6b;Lara;Pukall";
        sData[71] = "6b;Colin;Lich";
        sData[72] = "6b;Ruzhdi;Murtezani";
        sData[73] = "6b;FidanNur;Atalay";
        sData[74] = "6b;Fadila-Samira;Bnini";
        sData[75] = "6b;Niklas;Ruediger";
        sData[76] = "6b;Jennifer;Lubula";
        sData[77] = "6b;Maxima-Lea;Winkler";
        sData[78] = "6b;Georg;Ostroverchov";
        sData[79] = "6b;Roland;Stech";
        sData[80] = "6b;Emily;Hochstetter";
        sData[81] = "6b;Djellza;Kryeziu";
        sData[82] = "6b;Vanesa;Ramadani";
        sData[83] = "6b;Jason;Reutter";
        sData[84] = "6b;Lennard;Heess";
        sData[85] = "6b;Fatou;Sawaneh";
        sData[86] = "6b;Leander;Kroll";
        sData[87] = "6b;Anna;Sakouhi";
        sData[88] = "6b;Rene;Schmah";
        sData[89] = "7a;BilalMelih;Demir";
        sData[90] = "7a;Jasmin;Benz";
        sData[91] = "7a;Max;Demuth";
        sData[92] = "7a;LauraCeline;Leonti";
        sData[93] = "7a;Laura;Zenulovic";
        sData[94] = "7a;Sarah;Bauer";
        sData[95] = "7a;Annie;Chevallier";
        sData[96] = "7a;Leon;Beil";
        sData[97] = "7a;Benjamin;Nikl";
        sData[98] = "7a;Sabri;Oezcan";
        sData[99] = "7a;Annabell;FernandezFischer";
        sData[100] = "7a;Leja;Galic";
        sData[101] = "7a;Lukas;Straehle";
        sData[102] = "7a;Nicolas;Haertel";
        sData[103] = "7a;Simon;Pulvermueller";
        sData[104] = "7a;Chiara;Lavorato";
        sData[105] = "7a;Verena;Plankl";
        sData[106] = "7a;Elmin;Sahman";
        sData[107] = "7a;Lance;Ahner";
        sData[108] = "7a;Latifa;Evangelista";
        sData[109] = "7b;Christine;Gelmel";
        sData[110] = "7b;Agapi;Mantidou";
        sData[111] = "7b;Safira;Roesel";
        sData[112] = "7b;Felix;Rudolph";
        sData[113] = "7b;Tamara-Joy;Winkler";
        sData[114] = "7b;Despina;Tsimpida";
        sData[115] = "7b;Maria;Harara";
        sData[116] = "7b;Simon;Czernecki";
        sData[117] = "7b;Kerem;Goerkem";
        sData[118] = "7b;Luis;Helebrant";
        sData[119] = "7b;Sozdar;Oelguel";
        sData[120] = "7b;Stavros;Pechlivanidis";
        sData[121] = "7b;Nico;Schoenpflug";
        sData[122] = "7b;Leon;Walenta";
        sData[123] = "7b;May-Lee;Raaf";
        sData[124] = "7b;Valentino;MuinoFrancavilla";
        sData[125] = "7b;Michelle;Popp";
        sData[126] = "7b;Fehnja;Engelen";
        sData[127] = "7c;Giuliana;DiCarlo";
        sData[128] = "7c;Rafailia;Peskeloglou";
        sData[129] = "7c;Benjamin;Budde";
        sData[130] = "7c;Loris;Rendina";
        sData[131] = "7c;Zoe;Secco";
        sData[132] = "7c;Panagiota;Tsimpida";
        sData[133] = "7c;Azad;Demirci";
        sData[134] = "7c;Raphael;Ortz";
        sData[135] = "7c;Mertcan;Yildirim";
        sData[136] = "7c;Alina;Maticki";
        sData[137] = "7c;Abdurrahman;Yildirim";
        sData[138] = "7c;Moritz;Hoenisch";
        sData[139] = "7c;Chantal;Agosta";
        sData[140] = "7c;Denise;Boylan";
        sData[141] = "7c;Jakob;EyadKhalil";
        sData[142] = "7c;Andra-Maria;Stirlici";
        sData[143] = "7c;Lavinia;Engelen";
        sData[144] = "7c;Fabio;Schilling";
        sData[145] = "8a;Melda;Kirbas";
        sData[146] = "8a;Ridvan;Durmus";
        sData[147] = "8a;Youness;ElGhoufairi";
        sData[148] = "8a;Jana;Spitzenberger";
        sData[149] = "8a;Lisa;Tovletidou";
        sData[150] = "8a;Evita;Fauth";
        sData[151] = "8a;Faiza;Nekere";
        sData[152] = "8a;Leila-Anais;Saleh";
        sData[153] = "8a;Dilani;Selvanathan";
        sData[154] = "8a;Luis;Gramm-Bauer";
        sData[155] = "8a;Bernhard;Kemp";
        sData[156] = "8a;LukauSalamao;Pedro";
        sData[157] = "8a;Michelle;Foerster";
        sData[158] = "8a;Hakan;Karaoglu";
        sData[159] = "8a;Jacqueline;Sfilio";
        sData[160] = "8a;Paul;Biermann";
        sData[161] = "8a;Justin;Brengel";
        sData[162] = "8a;Celine;Mielke";
        sData[163] = "8a;HussainSami;HameedHamza";
        sData[164] = "8a;Aliona;Triebskorn";
        sData[165] = "8a;Paul;Wolter";
        sData[166] = "8a;SamedEnes;Gueler";
        sData[167] = "8a;Niklas;Duerrenberg";
        sData[168] = "8a;Lavina;Amos";
        sData[169] = "8a;Linus;Engelen";
        sData[170] = "8b;Reber;Boeluek";
        sData[171] = "8b;Sophia;Brenn";
        sData[172] = "8b;Gizem;Bakir";
        sData[173] = "8b;Murat;Bakir";
        sData[174] = "8b;Athanasia;Fassoulcou";
        sData[175] = "8b;Francesco;Scerra";
        sData[176] = "8b;Lenny-Luca;Weinzierl";
        sData[177] = "8b;Justin;Bruckner";
        sData[178] = "8b;Rico;Fioralba";
        sData[179] = "8b;Arlind;Krajsek";
        sData[180] = "8b;Neslihan;Uyar";
        sData[181] = "8b;Nora;Tamraoui";
        sData[182] = "8b;Lena;Nebjonat";
        sData[183] = "8b;Sarah;Tamraoui";
        sData[184] = "8b;Meryam;Benamar";
        sData[185] = "8b;Yonca;Oezcelik";
        sData[186] = "8b;Laurin;Maier";
        sData[187] = "8b;Timo;Zocher";
        sData[188] = "8b;Ellen;Luz";
        sData[189] = "8b;Marcel;Thiel";
        sData[190] = "8b;Cornelia;Michen";
        sData[191] = "8b;Julia;Trueten";
        sData[192] = "8b;DespinaZoi;Choneftidis";
        sData[193] = "8b;Tuemerk;Ablak";
        sData[194] = "8b;Leonardo;Filippelli";
        sData[195] = "8b;Johanna;Krieg";
        sData[196] = "8b;Lukas;Spieth";
        sData[197] = "9b;Peter;Lustig";

        String saveDataCSV = sData[0];
        for ( int i = 1; i< sData.length; i++) {
            saveDataCSV = saveDataCSV + "\n" + sData[i];
        }

        SaveDataToFile a = new SaveDataToFile();
        a.SaveData(fileName,saveDataCSV);
    }
}
