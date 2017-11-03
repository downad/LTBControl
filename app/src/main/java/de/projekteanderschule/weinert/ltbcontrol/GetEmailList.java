package de.projekteanderschule.weinert.ltbcontrol;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.AsyncTask;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

/*
 * Created by ralf on 17.05.17.
 *
 *  @author ralf weinert - ralf.weinert@gmx.de
 *  @version 0.8.0-05052017
 */
public class GetEmailList extends  AsyncTask<String, Integer, Boolean>   {
    private final static String KlassenName = "GetEmailList";
    private final Properties props =  ConfigData.getMailProperties();
    private Message message[];
    public static boolean iAmReady = false;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String methodenName = KlassenName + ".onPreExecute";
        String logText = "Versuche das Emailpostfach zu checken....";
        Logger.log( "info", methodenName, logText);

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        String methodenName = KlassenName + ".onPostExecute";
        String logText = ".... habe das Emailpostfach gecheckt!";
        Logger.log( "info", methodenName, logText);
        //Logger.log( "info", methodenName, "aBoolean = " + aBoolean);

        iAmReady = aBoolean;
    }

    /**
     * Diese Methode erzeugt
     * - ein Property Objekt des Mailkonto
     * - holt von Mailserver die Subjects und die Sender-EmailAdresse
     * - Zerlegt das Subject in Klasse und Name
     * - lässt Klasse, Name und Sender-EmailAdresse prüfen
     * 	 bei
     *  - Erfolg -> Fülle MailStorage
     *  		und setze den Schlerstatus "Hat LTB abgegeben"
     *  - Misserfolg -> Fülle MailError
     *
     * Excieptions weden nach Möglichkeit aufgefangen und an eine Liste weitergeleteit.
     * System.out.println() werden an eine Liste weitergeleitet
     * und auf die Console ausgegeben
     *
     * @parm	kein
     * @return	kein     throws RuntimeException{
     *
     */
    @Override
    protected Boolean doInBackground(String...params) {
        String methodenName = KlassenName + ".doInBackground";
        String logText = "Rufe das Mail-Konto ab....";
        Logger.log( "info", methodenName, logText);

        //Creating a new session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(props.getProperty( "mail.pop3.user" ), props.getProperty( "mail.pop3.password" )) ;
                    }
                });
        //Debug true
        session.setDebug( true );
        String ErrorText = "falscher Sender";

        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            Store store = session.getStore("pop3");
            store.connect();
            Folder inboxfolder = store.getFolder("INBOX");

            if(inboxfolder == null) {
                // Ausgabe für den Logger
                logText = "CheckEmail: NO Inbox";
                Logger.log( "error", methodenName, logText);

            } else {
                logText = "hole die Mails ab.";
                Logger.log( "info", methodenName, logText);

                inboxfolder.open(Folder.READ_ONLY);
                message = inboxfolder.getMessages();;
                //inboxmessage;
                for ( int i = 0; i < message.length; i++ )
                {
                    // Hole alle Mails, message[i] enthält alle Mails
                    Message m = message[i];
                    // extrahiere eMail von From
                    String email = getEmailAdress(Arrays.toString(m.getFrom()));
                    String subject = m.getSubject();
                    String sendDate = sd.format(m.getSentDate());
                    //boolean NoAttachment = EmailHasNoAttachment(m.);

                    Logger.log( "info", methodenName, "Email: " + email + " vom " + sendDate + "\nBetreff: " + subject );

                    // ist die Email erlaubt,
                    if (AllowedEmailAdress.allowedEmailAdress(email) == false) {
                        MailStorage.addMailError(ErrorText, email, subject, sendDate);
                        // Ausgabe für den Logger
                        logText = "Diese Emailadresse: " + email + " ist kein berechtigter Sender!";
                        Logger.log( "warning", methodenName, logText);
                    } else {
                        // teste Weiter
                        String klasse = SubjectToKlasse(subject.toString());
                        String name = SubjectToName(subject.toString());
                        // Ausgabe für den Logger
                        logText = "Aus Subject " + subject + " wurde, Klasse: |" + klasse + "| Name: |" + name + "|";
                        Logger.log( "info", methodenName, logText);

                        String[] CheckKasseUndName = LTBStudents.CheckKlasseUndName(klasse, name);
                        ErrorText = CheckKasseUndName[0];

                        int ID = Integer.parseInt(CheckKasseUndName[1]);
                        if (ErrorText.equals("OK")) {
                            MailStorage.addLTBMail(klasse, name, email, subject, sendDate);
                            LTBStudents.setLTBAbgegeben(ID, email);

                            // Ausgabe für den Logger
                            logText = "Alles OK!";
                            Logger.log( "WHAT", methodenName, logText);
                        } else {
                            MailStorage.addMailError(ErrorText, email, subject, sendDate);
                            // Ausgabe für den Logger
                            logText = "Meldung aus Subject (" + subject + "): " + ErrorText;
                            Logger.log( "warning", methodenName, logText);
                        }
                    }
                }
            }
            inboxfolder.close(false);
            store.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
       return true; //return true - i habe fertig?;
    }


    /**
     * Liefert einen Teilstring des String s zurück, der auf den Pattern p passt
     *
     * @param  s  	ein Sting in dem ein Regex-Ausdruck gesucgt werden soll
     * @param  p 	der Regex-Ausdruck, ein Pattern
     * @return      Teilstring von String s mit den Pattern p
     */
    private static String get_match(String s, String p) {
        String methodenName = KlassenName + ".getEmailAdress";
        // returns Treffer von P in S
        Matcher matcher = Pattern.compile(p).matcher(s);
        String returnvalue ="";
        while (matcher.find()) {
            returnvalue = matcher.group();
        }
        // Ausgabe für den Logger
        String logText = "RegEx (" + p + ") String: |" + s
                + "| Treffer: |" + returnvalue + "|";
        Logger.log( "WHAT", methodenName, logText);

        return returnvalue;
    }

    /**
     * Liefert eine Emailadresse als String zurück
     *
     * @param  from  	ein Sting mit dem Inhalt der Sender-EmailAdresse
     * @return      	Sender-EmailAdresse
     */
    private static String getEmailAdress(String from) {
        String methodenName = KlassenName + ".getEmailAdress";
        // Suche die EMail-Adresse in From
        String MyEmailPattern = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";
        String returnvalue = get_match(from, MyEmailPattern);
        // Ausgabe für den Logger
        String logText = "gefundene Email: |" + returnvalue + "|";
        Logger.log( "WHAT", methodenName, logText);

        return returnvalue;
    }

    /**
     * Liefert einen String mit dem Klassenbezeichner zurück
     *
     * @param  subject  	ein Sting mit dem subject (Betreff) der Email 
     * @return      		Klassenbezeichner
     */
    private static String SubjectToKlasse(String subject){
        String methodenName = KlassenName + ".SubjectToKlasse";
        String MyPattern = "^[0-9]?[0-9][abcd]";
        String returnvalue = get_match(subject, MyPattern);
        // Ausgabe für den Logger
        String logText = "Subject (" + subject + ") to Klasse: |" + returnvalue + "|";
        Logger.log( "WHAT", methodenName, logText);

        if (returnvalue == "") {
            returnvalue = "ERROR - Klasse";
            logText = "Subject (" + subject + ") to Klasse: |" + returnvalue + "|";
            Logger.log( "warning", methodenName, logText);
        }
        return returnvalue;
    }

    /**
     * Liefert einen String mit dem Name des Schülers zurück
     *
     * @param  subject  	ein Sting mit dem subject (Betreff) der Email 
     * @return      		Name des Schülers oder ERROR, falls der Name weniger als 4 Zeichen hat
     */
    private static String SubjectToName(String subject) {
        String methodenName = KlassenName + ".SubjectToName";
        String MyPattern = "([A-Z])\\w+ ([A-Z])\\w+";
        String returnvalue = get_match(subject, MyPattern);
        // Ausgabe für den Logger
        String logText = "Subject (" + subject + ") to Name: |" + returnvalue + "|";
        Logger.log( "WHAT", methodenName, logText);

        if (returnvalue.length() < 4) {
            returnvalue = "ERROR - Name";
            logText = "Subject (" + subject + ") to Name: |" + returnvalue + "|";
            Logger.log( "warning", methodenName, logText);
        }
        return returnvalue;
    }


}