package de.projekteanderschule.weinert.ltbcontrol;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
 * Created by ralf on 15.05.17.
 *
 * Diese Klasse beinhaltet alle Konstanten, Logindatein und Pfade
 * für LTB-Control
 *
 * Diese Werte sind FEST und können nicht durch config.properies geändert werden
 * @param  slash 				das Trennzeichen eines Pfades / oder \
 * @param  prgPath			 	der Pfad zur .jar Datei, wird ermittelt
 * @param  configPath 			Pfad zum csv Verzeichnis
 * @param  configFile			Name des ConfigFiles
 * @param  pdfPath 				Pfad zum pdf Verzeichnis
 * @param  logPath 				Pfad zum log Verzeichnis
 * @param  qrPath 				Pfad zum qr-code Verzeichnis
 * @param  schuelerCSV 			csv-Datei mit den Schülerdaten, ;-getrennt
 * @param  allowedEmailCSV 		csv-Datei mit den erlaubten Emailsenderen, ;-getrennt
 * @param  host					der Mailhost
 * @param  username 				Username des Mailkontos
 * @param  password 				password des Mailkontos
 * @param  QREmail 				Emailadresse für den QR-Code zum senden der Email
 *
 * @param ltbDebugLevel			Debuglevel als Sting
 * @param aloggerMSGText			Array mit den verschiedenen DebugMessageTexten
 *
 *  @author ralf weinert - ralf.weinert@gmx.de
 *  @version 0.8.20-15052017
 */
public class ConfigData {

    private final static String KlassenName = "ConfigData";

    /*
     Diese Konstanten sind fest!
    */

    // Trennzeichen für den Pfad
   // private static String slash = System.getProperty("file.separator");

    // feste Pfade
   // private static String prgPath = "abc"; // getJarExecutionDirectory();
   // private static String configPath = prgPath + slash + "ltb_config" + slash ;

    // ConfigFile
   // private static String configFile = "config.properties";


    /*
     MailKonto
    */

    //Definitionen für das Mailkonto
    // Diese liegen in einer separaten Datei
    private static String host = "w0119648.kasserver.com";			// change accordingly
    private static String username = "untis@seewiesenschule.de";	// change accordingly
    private static String password = "";

    private static String QREmail = "untis@seewiesenschule.de";		// change accordingly


    /*
    String ChoosenClass;
    */
    private static String ChoosenClass ="Alle";


    /*
     Debug
     */

    // Debug Logger Message Category - default = ERROR
    private static String[] aloggerMSGText = {"all", "WHAT", "INFO", "WARNING", "ERROR", "CRITICAL ERROR"};


    /*
     defaults, können überschrieben werden!
    */
    //default Pfade
  //  private static String pdfPath = prgPath + slash + "ltb_pdf" + slash;
 //   private static String logPath = prgPath + slash  + "ltb_log" + slash;
 //   private static String qrPath = prgPath + slash  + "ltb_qrcode" + slash;

    // Debug
    // Logger Message Category - default = ERROR
    // {"all", "WHAT", "INFO", "WARNING", "ERROR", "CRITICAL ERROR"};
    private static String ltbDebugLevel = "INFO";
    private static String DebugMsgPrefix = "Ralf-LTBControl";

    private static String gewaehlteKlasse = "Alle";

    // in dieser Datei sind die Schüler als ";"-getrennete Liste
    // Klasse, Vorname, Name
    private static String schuelerCSV = "schueler.csv";
    // in dieser Datei sind die Erlauben Emailadressen hinterlegt als  ";"-getrennete Liste
    // Name, Emailadresse
    private static String allowedEmail = "allowedEmail.csv";


    /*
    Getter Methoden
    */

   /**
     * print-Methode
     *
     * @return  body, ein html-konfigurierter String mit Konfigurationsdaten
     */
    public static String getConfig2html(){
        String body = "Konfiguration von LTB-Kontrol Verison " + MainActivity.getVersion() + "<br />";
      //  body = body + "Ordner-Trennzeichen: " + slash + "<br />";
     //   body = body + "Programm-Path: " + prgPath + "<br />";
     //   body = body + "Pfad zum Configordner: " + configPath + "<br />";
     //   body = body + "Config-Datei: " + configFile + "<br />";
        body = body + "Mail-Host: " + host + "<br />";
        body = body + "Mail Login: " + username + "<br />";
        body = body + "Mail Passwort: ****** <br />";
        body = body + "QRSende Adresse: " + QREmail + "<br />";
        body = body + "<br /> defaults / überschreibbar <br /><hr /><br />";
        body = body + "DebugLevel: " + ltbDebugLevel + "<br />";
     //   body = body + "Pfad zum PDF-Ordner: " + pdfPath + "<br />";
     //   body = body + "Pfad zum Log-Ordner: " + logPath + "<br />";
     //   body = body + "Pfad zum QR-Code-Ordner: " + qrPath + "<br />";
        body = body + "Datei: schuelerCSV: " + getSchuelerCSV() + "<br />";
        body = body + "Datei: allowedEmailCSV: " + getAllowedEmailCSV() + "<br />";
        //body = body + "Sprache: " + Sprache + "<br />";
        return body;
    }
   /**
     * getter-Methode
     *
     * @return host, der Mail host
     */
    public static String getHost() {
        return host;
    }
   /**
     * getter-Methode
     *
     * @return username, username des Mailkonto
     */
    public static String getUsername() {
        return username;
    }
   /**
     * getter-Methode
     *
     * @return password, password des Mailkonto
     */
    public static String getPassword() {
        return password;
    }

   /**
     * getter-Methode
     *
     * @return  pfad und Dateiname zur schuler.csv
     */
    public static String getSchuelerCSV (){
        return schuelerCSV;
    }
    /**
     * getter-Methode
     *
     * @return  pfad und Dateiname zur schuler.csv
     */
    public static String getChoosenClass (){

        return ChoosenClass;
    }

    /**
     * getter-Methode
     *
     * @return  pfad und Dateiname zur allowedemail.csv
     */
    public static String getAllowedEmailCSV (){
        return allowedEmail;
    }
   /**
     * getter-Methode
     *
     * @return  pfad qr-code-Verzeichnis
     */
 /*   public static String getQRCodePath(){
        return qrPath;
    }
    */
   /**
     * getter-Methode
     *
     * @return  pfad pdf-Verzeichnis
     */
    /*public static String getPDFPath() {
        return pdfPath;
    }
    */
   /**

     * getter-Methode
     *
     * @return  pfad log-Verzeichnis
     */
    /*public static String getLogPath() {
        return logPath;
    }*/
   /**
     * getter-Methode
     *
     * @return  pfad config-Verzeichnis
     */
    /*public static String getConfigPath() {
        return configPath;
    }*/
   /**
     * getter-Methode
     *
     * @return  QRCode-Emailadresse
     */
    public static String getEmailToSendFromQRCode() {
        return QREmail;
    }
   /**
     * getter-Methode
     *
     * @param  loggerMSGText
     * @return  Integer mit der Nummer des loggerMSGText
     */
    public static int loggerMSGTextToInt(String loggerMSGText) {
        for (int i = 0; i < aloggerMSGText.length; i++ )
        {
            if ( aloggerMSGText[i].equals(loggerMSGText)) {
                return i;
            }
        }
        return 0; // nichts gefunden dann 0 für all = alles
    }
    /**
  * getter-Methode
  *
  * @return host, der Mail host
  */
    public static String getDebugTAG() {
        return DebugMsgPrefix;
    }
   /**
     * getter-Methode
     *
     * @param  msgCategory
     * @return msgText	ein Sting mit der Textausgabe der LOGGERMSG
     */
    public static String getMesageTextCategory(int msgCategory) {
        return aloggerMSGText[msgCategory];
    }

   /**
     * getter-Methode
     *
     * @return msgText	ein String mit der Textausgabe der LOGGERMSG
     */
    public static String getLTBDebugLevelAsString() {
        return ltbDebugLevel;
    }
   /**
     * getter-Methode
     *
     * @return msgText	ein String mit der Textausgabe der LOGGERMSG
     */
    public static int getLTBDebugLevelAsInt() {
        return loggerMSGTextToInt(ltbDebugLevel);
    }
   /**
     * getter-Methode
     *
     * @return props - die MailProperties
     */
    public static Properties  getMailProperties() {
        Properties props = new Properties();
        props.setProperty( "mail.pop3.host", host );
        props.setProperty( "mail.pop3.user", username);
        props.setProperty( "mail.pop3.password", password);
        props.setProperty( "mail.pop3.port", "995" );
        props.setProperty( "mail.pop3.auth", "true" );
        props.setProperty( "mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory" );
        return props;
    }


   /**
     * Returns the path where the currently running JAR-file is located.
     * Example value: C:\MyProject\build\jar\
     *
     * @return Path of the JAR-file
     *
     * angelehnt an
     * http://www.bennyn.de/programmierung/java
     * 	/aktuellen-pfad-einer-java-anwendung-ermitteln.html
     * vom: - 20.04.2017
     */
    public static String getJarExecutionDirectory()
    {
        String jarFile = null;
        String jarDirectory = null;
        int cutFileSeperator = 0;
        int cutSemicolon = -1;

        jarFile = System.getProperty("java.class.path");
        // Cut seperators
        cutFileSeperator = jarFile.lastIndexOf(System.getProperty("file.separator"));
        jarDirectory = jarFile.substring(0, cutFileSeperator);
        // Cut semicolons
        cutSemicolon = jarDirectory.lastIndexOf(';');
        jarDirectory = jarDirectory.substring(cutSemicolon+1, jarDirectory.length());

        String[] pathParts = jarDirectory.split(":");

        return pathParts[pathParts.length-1]; //
    }
   /**
     * Lade die Konfiguration und lasse die Variablen setzen
     *
     * @return keine
     */
   /*
    private static void readPropertyFromFile() throws IOException {
        String methodenName = KlassenName + ".readPropertyFromFile";
        String file = configPath + slash + configFile;
        String logText = "config-file: " + file;
        try {
            Properties properties = new Properties();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream( file ));
            properties.load(stream);
            stream.close();
            Logger.log( "info", methodenName, logText);
            //
            setMyParam(properties);
        } catch (Exception e) {
            logText = e.getStackTrace()[0].getMethodName() + ": " + e.getStackTrace();
            Logger.log( "error", methodenName, logText);
            e.printStackTrace();
        }
    }
    */
    // Methode zum setzen der ConfigParamaeter
    private static void setMyParam(Properties properties) {
        ltbDebugLevel = properties.getProperty("DebugLevel");
        schuelerCSV = properties.getProperty("CSV_Schueler");
        allowedEmail = properties.getProperty("CSV_SenderEmails");
    }

   /**
     * öffentliche methode zum
     * Laden der Konfiguration
     *
     * @return keine
     */
   /*
    public static void loadConfig() {
        String methodenName = KlassenName + ".loadConfig";
        String logText = "Lade die Konfiguration.";
        Logger.log( "info", methodenName, logText);
        try {
            readPropertyFromFile();
        }
        catch (Exception e) {
            logText = e.getStackTrace()[0].getMethodName() + ": " + e.getStackTrace();
            Logger.log( "error", methodenName, logText);
            e.printStackTrace();
        }
    }
    */
   /**
     * getter-Methode
     *
     * @return mKlasse	ein Sting der Klasse
     */
    public static String getKlasse(){
        return gewaehlteKlasse;
    }
   /**
     * getter-Methode
     *
     * @return mKlasse	ein Sting der Klasse
     */
    public static String getKlassenFiter(){
        String returnvalue = "";
        if (gewaehlteKlasse.equals("Alle")){
            returnvalue = "(alle Schüler)";
        } else {
            returnvalue = "(Klasse " + gewaehlteKlasse + ")";
        }
        return returnvalue;
    }
   /**
     * setter-Methode
     *
     * @param  mKlasse
     * @return mKlasse	ein Sting der Klasse
     */
    public static void setKlasse(String mKlasse){
        gewaehlteKlasse = mKlasse;
    }

}
