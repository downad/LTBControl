package de.projekteanderschule.weinert.ltbcontrol;

/*
 * Created by ralf on 17.05.17.
 * Diese Klasse speichert den Fehlern in der Emailbearbeitung gehört
 *
 * @param klasse
 * @param name
 * @param from
 * @param subject
 * @param senddate
 * @param id
 *
 * @return kein
 *
 *  @author ralf weinert - ralf.weinert@gmx.de
 *  @version 0.8.0-05052017
 */
public class OneMailStorage {
    private final static String KlassenName = "OneMailStorage";

    private String klasse; 			// in welcher Klasse soll der Schüler sein
    private String name;			// Vorname und Name des Schüler
    private String from;			// Emailadresse des Absender (Person die den QRCode gescannt und gesendet hat)
    private String subject;			// MailSubject (original)
    private String senddate;		// Sendedatum der Mail
    private int id;					// fortlaufende ID der Speicherklasse
    private static int numberOfMails = 0;

    public OneMailStorage (String IniKlasse, String IniName, String IniFrom, String IniSubject, String IniSendDate) {
        klasse = IniKlasse;
        name = IniName;
        from = IniFrom;
        subject = IniSubject;
        senddate = IniSendDate;
        // increment number Mail and assign ID number
        id = ++numberOfMails;
    }
    public int getID() {
        return id;
    }
    public String getFullname() {
        return name;
    }
    public String getFrom() {
        return from;
    }
    public String getKlassse() {
        return klasse;
    }
    public String getSendDate() {
        return senddate;
    }
    public String getSubject() {
        return subject;
    }
}