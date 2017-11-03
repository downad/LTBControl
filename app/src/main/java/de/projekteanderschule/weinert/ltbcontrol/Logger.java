package de.projekteanderschule.weinert.ltbcontrol;

import android.util.Log;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * Created by ralf on 15.05.17.
 * Diese Klasse erzeugt die Liste (ArrayList) in denen Logs
 * gespeichert werden
 *
 * @param ArrayList<OneLogEntry> LogStorage
 *
 * @author ralf weinert - ralf.weinert@gmx.de
 * @version 0.8.2-15052017
 */
public class Logger {
    private final static String KlassenName = "Logger";
    private static String debaugTAG = ConfigData.getDebugTAG();
    private static boolean appendToLogFile = true;
    private static boolean useConsole = true;

    private static ArrayList<OneLogEntry> LogStorage = new ArrayList<OneLogEntry>();

    /**
     * log
     * was ausgegeben werden soll?
     * Alle MSG werden mit Datum versehen und gespeichert (addLog)
     *
     * Der Filter für den LogWriter ist der DebugLevel
     *
     * @param 	msgTextCategory	- Welche rKategorie gehört die MSG an?
     * 								all, What, info, warning, error, critical
     * @param	methode			- Welche Methode sendet diese MSG?
     * @param	logText			- Text der MSG
     * @return 	kein
     *
     */
    public static void log( String msgTextCategory, String methode, String logText ) {
        // msgCategory - all, What, info, warning, error, critical
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss ");
        Date TimeNow = new Date();
        String currentTime = formatter.format(TimeNow);
        int msgCategory = ConfigData.loggerMSGTextToInt(msgTextCategory.toUpperCase());
        if (ConfigData.getLTBDebugLevelAsInt() <= msgCategory) {
            String logEntry = currentTime + " [ " + methode + " ]"
                    + "\n" + msgTextCategory.toUpperCase() + " - " + logText;
            //System.out.println(logEntry);
            //System.out.println(ConfigData.getMesageTextCategory(msgCategory) + " [" + methode + "] - " + logText);
            try {
                //writeLog( msgTextCategory, methode, logText );
                //writeLog(logEntry);
            } catch(Exception e) {
                //addLog( msgTextCategory, methode, e.toString());
                //System.out.println(msgTextCategory + " " +  methode + e.toString());
            }
        }
        addLog( msgTextCategory, methode, logText );
        //Log.d(debaugTAG, "useConsole = " + useConsole);
        if (useConsole == true) {
            consoleLog(msgTextCategory, methode, logText);
        }
    }

    /**
     * consoleLog
     * wie Log, nur wird NIE was gespeichert
     *
     * @param 	msgTextCategory	- Welcher Kategorie gehört die MSG an?
     * 								all, What, info, warning, error, critical
     * @param	methode			- Welche Methode sendet diese MSG?
     * @param	logText			- Text der MSG
     * @return 	kein
     *
     */
    public static void consoleLog( String msgTextCategory, String methode, String logText ) {
        // msgCategory - all, What, info, warning, error, critical
        int msgCategory = ConfigData.loggerMSGTextToInt(msgTextCategory.toUpperCase());
        String logString = msgTextCategory.toUpperCase() + ": [" + methode + "] - " + logText ;
        if (ConfigData.getLTBDebugLevelAsInt() <= msgCategory) {
           // Log.d(debaugTAG, " ");
            Log.d(debaugTAG, logString);
           // Log.d(debaugTAG, " ");
        }
        // TextView text = (TextView) yourActivity.findViewById(R.id.textView10);

    }

    /**
     * setUseConsole
     *
     * @param 	bUseConsole	- true - false
     * @return 	kein
     *
    */
    public static void setUseConsole (boolean bUseConsole){
        useConsole = bUseConsole;
    }

    /**
     * addlog speichert in die Liste (ArrayList)
     *
     * @param msgTextCategory
     * @param methode
     * @param logText
     *
     * @return kein
     *
     */
    public static void addLog( String msgTextCategory, String methode, String logText ){
        LogStorage.add(new OneLogEntry(msgTextCategory, methode, logText));
    }

    /**
     * setLogfileMode
     * soll an das Log-file angehängt werden oder soll es neu geschrieben werden?
     *
     * @param 	append	- true = append - false = new file
     * @return 	kein
     *
     */
    public static void setLogfileMode (boolean append){
        appendToLogFile = append;
    }

    /**
     * getHtml2PrintStudentList
     * Erzeuge aus OneStuden ein html-Body-Tabelle mit Inhalten
     * Ausgabetyp = DebugLevels
     *
     * @param msgTextCategory
     *
     * @return body - ein html-formatted-String
     *
     */
    public static String getHtml2PrintLogStorage( String msgTextCategory) {
        String methodenName = KlassenName + ".getHtml2PrintLogStorage";

       // String headline = HTMLformater.htmlHeadline("Log-Liste", 2);
        String body = "<table width=\"100%\">"
                +"<colgroup>\n"
                +"    <col width=\"20%\">\n"
                +"    <col width=\"20%\">\n"
                +"    <col width=\"40%\">\n"
                +"    <col width=\"20%\">\n"
                +"</colgroup>"
                +"<tr><th>Datum</th><th>Category</th><th>Log Text</th><th>Logging<br> Methode</th></tr>";

        for (int i = 0;i<LogStorage.size();i++){
            int msgCategory = ConfigData.loggerMSGTextToInt(msgTextCategory.toUpperCase());
            if ( msgCategory <= LogStorage.get(i).getLogCategoryByInt()) {
                String output = "<tr>"
                        + "<td><font size=\"2\"> " + LogStorage.get(i).getLogTime() + ": </font></td>"
                        + "<td>" + LogStorage.get(i).getLogCategory() + "</td>"
                        + "<td>" + LogStorage.get(i).getLogText() + "</td>"
                        + "<td><font size=\"2\"> " + LogStorage.get(i).getLogMethode()+ "</font></td>"
                        + "</tr>";
                body = body + output;
            }
        }
        String logText = "html-String aufbereitet";
        Logger.log( "WHAT", methodenName, logText);
        return  body + "</table>";
    }


    /*
  * writeLog speichert in die Datei, ltb.log
  *
  * @param msgTextCategory
  * @param methode
  * @param logText
  *
  * @return kein
  *
  */
    public static void writeLog( String logEntry ) throws Exception {
        String methodenName = "writeLog";
        String file = ConfigData.getLogPath() + "ltb.log";
        String logText = "";
        //logText = "Logpath + Datei " + file + "!";
        //consoleLog( "error", methodenName, logText);
        try {
            // create a new file with specified file name
            FileWriter fw = new FileWriter( file ,/* append */ appendToLogFile );

            logText = "Lege das File " + file + " an";
            consoleLog( "what", methodenName, logText);

            //FileWriter fw = new FileWriter("ausgabe.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            //bw.write(value); //("test test test");
            bw.append("\n" + logEntry); // msgTextCategory.toUpperCase() + " - " + currentTime + "\n[" + methode + "] - " + logText);

            bw.close();

            logText = "Schließe BufferedWriter bw.close";
            consoleLog( "what", methodenName, logText);

        } catch (Exception e) {
            // Ausgabe für den Logger
            logText = "Logger kann nicht in das File schreiben" + e.getMessage();
            log( "error", methodenName, logText);
            e.printStackTrace();
        }

    }
}