package de.projekteanderschule.weinert.ltbcontrol;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Created by ralf on 15.05.17.
 *
 * Diese Klasse speichert die Log-Einträge
 *
 * @param fromMethode
 * @param logCategory
 * @param IntlogCategory
 * @param text
 * @param id
 *
 * @return kein
 *
 *  @author ralf weinert - ralf.weinert@gmx.de
 *  @version 0.8.2-15052017
 */
public class OneLogEntry {
    private final static String KlassenName = "OneLogEntry";

    private String logCategory;
    private int IntlogCategory;
    private String fromMethode = "";
    private String text;
    private int id;					// fortlaufende ID der Speicherklasse
    private static int numberOfErrorLogs = 0;
    private String logTime;

    public OneLogEntry (String InilogCategory, String InifromMethode, String Initext) {
        logCategory = InilogCategory.toUpperCase();
        String[] parts = InifromMethode.split("\\.");
        for (int i = 0; i < parts.length; i++) {
            fromMethode = fromMethode + "<br>." + parts[i];
            //Logger.consoleLog("info",KlassenName,"parts["+i+"] = " + parts[i]);
        }
        //fromMethode = InifromMethode;
        text = Initext;
        IntlogCategory = ConfigData.loggerMSGTextToInt(logCategory);
        // increment number Mail and assign ID number
        id = ++numberOfErrorLogs;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss ");
        Date TimeNow = new Date();
        logTime = formatter.format(TimeNow);
    }
    public int getID() {
        return id;
    }
    public String getLogText() {
        return text;
    }
    public String getLogCategory() {
        return logCategory;
    }
    public int getLogCategoryByInt() {
        return IntlogCategory;
    }
    public String getLogMethode() {
        return fromMethode;
    }
    public String getLogTime() {
        return logTime;
    }
    public String getFullLog() {
        String returnvalue =  logTime + " [ " + fromMethode + " ]" + text;
        return returnvalue;
    }

}