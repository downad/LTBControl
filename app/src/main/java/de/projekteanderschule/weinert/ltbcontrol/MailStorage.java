package de.projekteanderschule.weinert.ltbcontrol;

import java.util.ArrayList;

/*
 * Created by ralf on 17.05.17.
 * Diese Klasse erzeugt die Listen (ArrayList) in denen die Mail und die Fehlerhaften Mail
 * gespeichert werden
 *
 * @param ArrayList<OneMailStorage> OneMailStorage
 * @param ArrayList<OneMailError> OneMailError
 *
 *  @author ralf weinert - ralf.weinert@gmx.de
 *  @version 0.8.0-05052017
 */
public class MailStorage {
    private final static String KlassenName = "MailStorage";

    private static ArrayList<OneMailStorage> OneMailStorage = new ArrayList<OneMailStorage>();
    private static ArrayList<OneMailError> OneMailError = new ArrayList<OneMailError>();

    /**
     * addLTBMail speichert in die Listen (ArrayList)
     *
     * @param klasse		Klasse der Schüler
     * @param name		Vollständiger Name des Schülers
     * @param email		SenderEmail
     * @param subject 	original Subject
     * @param sendDate	Sendezeit der Email
     *
     * @return kein
     *
     */
    public static void addLTBMail(String klasse, String name, String email, String subject, String sendDate) {
        OneMailStorage.add(new OneMailStorage(klasse, name, email, subject, sendDate));
    }
    /**
     * addMailError speichert in die Listen (ArrayList)
     *
     * @param IniErrorText	Fehler/Error Typ
     * @param IniFrom		SenderEmail
     * @param IniSubject 	original Subject
     * @param IniSendDate	Sendezeit der Email
     *
     * @return kein
     *
     */
    public static void addMailError(String IniErrorText, String IniFrom, String IniSubject, String IniSendDate) {
        OneMailError.add(new OneMailError(IniErrorText, IniFrom, IniSubject, IniSendDate));
    }
    /**
     * getError2html - Wandelt die Lister der ErrorMails in html-formatierten body-Text um
     *
     * @return body	ein html-formated Text
     *
     */
    public static String getError2html() {
        String headline = HTMLformater.htmlHeadline("LTB Errorliste", 2);
        String body = headline
                + "<table><th>ID</th><th>Errortext</th><th>Original Subject</th><th>Sender Email</th><th>Senddate</th>";

        for (int i = 0;i<OneMailError.size();i++){
            body = body + "<tr><td>" + OneMailError.get(i).getID() + "</td>"
                    + "<td>" + OneMailError.get(i).getErrorText() + "</td>"
                    + "<td>" + OneMailError.get(i).getSubject() + "</td>"
                    + "<td>" + OneMailError.get(i).getFromEmail() + "</td>"
                    + "<td>" + OneMailError.get(i).getSendDate() + "</td></tr>";
        }
        return body + "</table>";
    }
    /**
     * getMail2html - Wandelt die Liste der Mails in html-formatierten body-Text um
     *
     * @return body	ein html-formated Text
     *
     */
    public static String getMail2html() {
        String headline = HTMLformater.htmlHeadline("Liste der QR-Mails", 2);
        String body = headline
                + "<table><th>ID</th><th>Klasse</th><th>Vorname Name</th><th>sender Email</th><th>Senddate</th>";

        for (int i = 0;i<OneMailStorage.size();i++){
            body = body + "<tr><td>" + OneMailStorage.get(i).getID() + "</td>"
                    + "<td>" + OneMailStorage.get(i).getKlassse() + "</td>"
                    + "<td>" + OneMailStorage.get(i).getFullname() + "</td>"
                    + "<td>" + OneMailStorage.get(i).getFrom() + "</td>"
                    + "<td>" + OneMailStorage.get(i).getSendDate() + "</td></tr>";
        }
        return body + "</table>";
    }

}
