package de.projekteanderschule.weinert.ltbcontrol;

/*
 * Created by ralf on 17.05.17.
 * Diese Klasse speichert den Fehlern in der Emailbearbeitung gehört
 *
 * @param Klasse
 * @param name
 * @param vorname
 * @param rufname
 * @param ltbstatus
 * @param QRscannedBy
 * @param id
 *
 * @return kein
 *
 *  @author ralf weinert - ralf.weinert@gmx.de
 *  @version 0.8.0-05052017
 */
public class OneStudent {
    private final static String KlassenName = "OneStudent";

    private String klasse;			// Klasse des Schülers
    private String name;			// Name des Schülers
    private String vorname;			// Vorname oder Vornamen des Schülers
    private String rufname;			// Rufname des Schülers
    private int ltbstatus;			// Status, abgegben oder nicht abgegeben - 	0 - nicht abgegeben 	1 - abgegeben
    private String QRscannedBy;		// Wer hat QR gescannt?

    private int id;					// fortlaufende ID der Speicherklasse
    private static int numberOfltbStudent = 0;

    public OneStudent (String Iniklasse, String IniVorname, String IniName) {
        klasse = Iniklasse;
        name = IniName;
        vorname = IniVorname;
        // in der Schülerkartei sind oftmals mehrer Vornamen eines Schülers gespeichert.
        // es soll nun der Rufname ermittelt werden.
        // testen ob Leerzeichen da ist
        if (vorname.matches(".*\\s+.*") == true)
        {
            String[] parts = IniVorname.split("\\s+");
            rufname = parts[0];
        } else {
            rufname = vorname;
        }
        ltbstatus = 0;
        QRscannedBy = "-";
        // increment number Mail and assign ID number
        id = ++numberOfltbStudent;
    }
    public int getID() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getVorname() {
        return vorname;
    }
    public String getRufname() {
        return rufname;
    }

    public String getFullname() {
        return rufname + " " + name;
    }

    public String getKlasse() {
        return klasse;
    }
    public int getLtbStatus() {
        return ltbstatus;
    }
    public void setLtbStatus(int status, String email ){
        // Check ob Status erlaubt?
        ltbstatus = status;
        QRscannedBy = email;
    }
    public String getQRscannedBy() {
        return QRscannedBy;
    }
}
