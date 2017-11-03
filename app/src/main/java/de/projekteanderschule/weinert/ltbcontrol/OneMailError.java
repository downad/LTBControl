package de.projekteanderschule.weinert.ltbcontrol;

/*
 * Created by ralf on 17.05.17.
 * Diese Klasse speichert den Fehlern in der Emailbearbeitung gehört
 *
 * @param from
 * @param subject
 * @param errortext
 * @param senddate
 * @param id
 *
 * @return kein
 *
 *  @author ralf weinert - ralf.weinert@gmx.de
 *  @version 0.8.0-05052017
 */
public class OneMailError {
    private final static String KlassenName = "OneMailError";

    private String from;			// Absender
    private String subject;			// Betreff
    private String errortext;		// Errormeldung
    private String senddate;		// Sendedatum
    private int id;					// fortlaufende ID der Speicherklasse
    private static int numberOfErrorLogs = 0;

    public OneMailError (String IniErrorText, String IniFrom, String IniSubject, String IniSendDate) {
        errortext = IniErrorText;
        from = IniFrom;
        subject = IniSubject;
        senddate = IniSendDate;
        // increment number Mail and assign ID number
        id = ++numberOfErrorLogs;
    }
    public int getID() {
        return id;
    }
    public String getErrorText() {
        return errortext;
    }
    public String getSubject() {
        return subject;
    }
    public String getSendDate() {
        return senddate;
    }
    public String getFromEmail() {
        return from;
    }
}