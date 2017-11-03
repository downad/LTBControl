package de.projekteanderschule.weinert.ltbcontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/*
 * Created by ralf on 22.05.17.
 *
 * Welche Email-Adressen sind als Sender zugelassen?
 *
 * Vorraussetzungen:
 * FILE: allowedEmail.csv im Path
 *      path =             APP-Path / 0.8.2
 *      path = new File(context.getExternalFilesDir(null), MainActivity.getVersion());
 *
 * @param ModifiedDate			Wann wurde die Datei das letztemal verändert?
 * @param ArrayList<OneAllowedEmail> AllowedEmail  		ArrayList der EmailAdresen
 *
 * @return kein
 *
 *  @author ralf weinert - ralf.weinert@gmx.de
 *  @version 0.8.2-22052017
 *
 */
public class AllowedEmailAdress  {
    private final static String KlassenName = "AllowedEmailAdress";
    public static String ModifiedDate = "";

    private static ArrayList<OneAllowedEmail> AllowedEmail = new ArrayList<OneAllowedEmail>();

    public static void  readLtbAllowedEmail(String inputFile) {
        String methodenName = KlassenName + ".readLtbAllowedEmail";
        String logText = "Begin mit readLTBAllowedEmail";
        Logger.log( "info", methodenName, logText);

        File path = MainActivity.getLTBAPPPATH();
        File fullFileName = new File(path, inputFile);
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(fullFileName));
            String zeile;

            while ((zeile = fileReader.readLine()) != null) {
                if (LTBStudents.countLetter(zeile, ";".charAt(0)) == 1){
                    String[] parts = zeile.split(";", 2);
                    // Name, Email, rest?
                    AllowedEmail.add(new OneAllowedEmail(parts[0].trim(), parts[1].trim()));
                    //Logger.log( "info", methodenName, "part[0]: " + parts[0].trim() + "\npart[1]: " + parts[1].trim() );
                } else {
                    logText = "Nicht genug ';' im String!";
                    Logger.log( "error", methodenName, logText);
                }
            }
            fileReader.close();
            //Logger.log( "info", methodenName, "Das ist das Ergebnis aus getHTML2printAllowedEmailList: \n" + getHTML2printAllowedEmailList());

        }
        catch (Exception e) {
            logText = "IST das der Fehler? " + e.getStackTrace()[0].getMethodName() + ": " + e.getStackTrace();
            Logger.log( "error", methodenName, logText);
            e.printStackTrace();
        }
    }
    /**
     * allowedEmailAdress
     * Welche Email-Adressen sind als Sender zugelassen?
     *
     * @param email
     *
     * @return true / false
     *
     */
    public static boolean allowedEmailAdress(String email){
        String methodenName = KlassenName + ".getHtml2PrintStudentList";
        String logText = "";
        for (int i = 0;i<AllowedEmail.size();i++){
            if (email.equals(AllowedEmail.get(i).getAllowedEmail())) {
                logText = "Die Email-Adresse |" + email + "| ist ein erlaubter Sender.";
                Logger.log( "WHAT", methodenName, logText);
                return true;
            }
        }
        logText = "Der Sender mit der Email-Adresse |" + email + "| ist nicht zugelassen.";
        Logger.log( "warning", methodenName, logText);
        return false;
    }
    /**
     * getHTML2printAllowedEmailList
     * Erzeuge aus AllowedEmail ein html-Body-Tabelle mit Inhalten
     *
     * @return html-body
     *
     */
    public static String getHTML2printAllowedEmailList() {
        String methodenName = KlassenName + ".getHtml2PrintStudentList";
        // alle Schüler ausgeben
        String body = "<table><th>ID</th><th>Email</th><th>Name</th>";
        for (int i = 0;i<AllowedEmail.size();i++){
            String output = "<tr><td>" + AllowedEmail.get(i).getID() + "</td>"
                    + "<td>" + AllowedEmail.get(i).getAllowedEmail() +"</td>"
                    + "<td>" + AllowedEmail.get(i).getName() + "</td></tr>";
            body = body + output;
        }
        String logText = "html-String aufbereitet";
        Logger.log( "WHAT", methodenName, logText);
        return body + "</table>";
    }
}
