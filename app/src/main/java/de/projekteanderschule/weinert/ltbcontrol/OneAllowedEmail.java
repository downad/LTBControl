package de.projekteanderschule.weinert.ltbcontrol;

/*
 * Created by ralf on 17.05.17.
 * Diese Klasse speichert alles was zur AllowedEmail gehört
 *
 * @param email
 * @param name
 * @param id
 *
 * @return kein
 *
 *  @author ralf weinert - ralf.weinert@gmx.de
 *  @version 0.8.0-05052017
 */
public class OneAllowedEmail {
    private final static String KlassenName = "OneAllowedEmail";

    private String email; 			// Diese Email ist berechtigt LTB-Status zu ändern
    private String name;			// Besitzer des Email Kontos

    private int id;					// fortlaufende ID der Speicherklasse
    private static int numberOfAllowedEmails = 0;

    public OneAllowedEmail (String IniEmail, String IniName) {
        email = IniEmail;
        name = IniName;
        // increment number Mail and assign ID number
        id = ++numberOfAllowedEmails;
    }
    public int getID() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAllowedEmail() {
        return email;
    }

}