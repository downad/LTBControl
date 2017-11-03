package de.projekteanderschule.weinert.ltbcontrol;

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
 * Die Klasse CheckEmail 
 * Aufgaben:
 *  überprüft das Mailkonto und 
 *  - hole alle Mail-Subjects
 *  -- Zerlege das Mail-Subject in 
 *  --- Klasse (des Schülers)
 *  --- Name (des Schülers)
 *  - hole die Sender-EmailAdresse
 *
 *  Lass die Sender-EmailAdresse prüfen (ist sie erlaubt?)
 *  Lass Name und Klasse prüfen (gibt es einen Schüler mit diesem Namen in dieser Klasse?)
 *  - Erfolg -> Fülle MailStorage
 *  		und setze den Schlerstatus "Hat LTB abgegeben"
 *  - Misserfolg -> Fülle MailError
 *
 *  @author ralf weinert - ralf.weinert@gmx.de
 *  @version 0.8.0-05052017
 */
public class CheckEmail {
    private final static String KlassenName = "CheckEmail";

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
     * @return	kein
     *
     */
    public static void  checkMails() throws RuntimeException { //Exception { 

        // Methodenname und Text für den Logger
        String methodenName = KlassenName + ".checkMails";
        String logText = "Versuche das Emailpostfach zu checken....";
        Logger.log( "info", methodenName, logText);

        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            Properties props = ConfigData.getMailProperties();
            Session session = Session.getInstance( props, new javax.mail.Authenticator() {
                @Override protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication( ConfigData.getMailProperties().getProperty( "mail.pop3.user" ),
                            ConfigData.getMailProperties().getProperty( "mail.pop3.password" ) );
                }
            } );
            // Debug On / Off?
            // session.setDebug( true );
            String ErrorText = "falscher Sender";

            Store store = session.getStore("pop3");
            store.connect();
            Folder inboxfolder = store.getFolder("INBOX");
            if(inboxfolder == null) {
                // Ausgabe für den Logger
                logText = "CheckEmail: NO Inbox";
                Logger.log( "info", methodenName, logText);

            } else {
                inboxfolder.open(Folder.READ_ONLY);
                Message message[] = inboxfolder.getMessages();
                logText = "hole die Mails ab.";
                Logger.log( "info", methodenName, logText);
                for ( int i = 0; i < message.length; i++ )
                {
                    // Hole alle Mails, message[i] enthält alle Mails
                    Message m = message[i];
                    // extrahiere eMail von From
                    String email = getEmailAdress(Arrays.toString(m.getFrom()));
                    String subject = m.getSubject();
                    String sendDate = sd.format(m.getSentDate());
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
                        logText = "aus Subject wurde, Klasse: |" + klasse + "| Name: |" + name + "|";
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
        } catch (AuthenticationFailedException e) {
            // Ausgabe für den Logger
            logText = "CheckEMail: \nFehler bei der Authentifikation! \nPasswort und Username überprüfen.";
            Logger.log( "warning", methodenName, logText);
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            // Ausgabe für den Logger
            logText = "CheckEMail: \nNoSuchProviderException: 002";
            Logger.log( "warning", methodenName, logText);
            e.printStackTrace();
        } catch (MessagingException e) {
            // Ausgabe für den Logger
            logText = "CheckEMail: \nMessagingException: 003";
            Logger.log( "warning", methodenName, logText);
            e.printStackTrace();
        }  catch (Exception e) {
            // Ausgabe für den Logger
            logText = "CheckEMail: \nProblem beim Mail-Empfang" + e.getMessage();
            Logger.log( "error", methodenName, logText);
            e.printStackTrace();
        }
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