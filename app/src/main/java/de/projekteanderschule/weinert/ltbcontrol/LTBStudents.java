package de.projekteanderschule.weinert.ltbcontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/*
 * Created by ralf on 22.05.17.
 * Diese Klasse speichert alles was zu den Schülern gehört.
 * Dazu wird ein ArrayList des Typs OneStudent erzeugt.
 *
 * @param ModifiedDate			Wann wurde die Datei das letztemal verändert?
 * @param ArrayList<OneStudent> OneStudent  		ArrayList zu den Schülern
 *
 * @return kein
 *
 *  @author ralf weinert - ralf.weinert@gmx.de
 *  @version 0.8.2-22052017
 */
public class LTBStudents {
    private final static String KlassenName = "LTBStudents";
    public static String ModifiedDate = "";
    private static ArrayList<OneStudent>Studentlist = new ArrayList<OneStudent>();


    /**
     * readLtbStudent
     * ein csv-Reader für die schueler.csv
     *
     * @param inputFile			Name der CSV-Datei
     *
     * @return ArrayList<OneStudent>
     *
     */
    public static void  readLtbStudent(String inputFile) {
        String methodenName = KlassenName + ".readLtbStudent";
        String logText = "Begin mit readLtbStudent";
        Logger.log( "info", methodenName, logText);

        File path = MainActivity.getLTBAPPPATH();
        File fullFileName = new File(path, inputFile);
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(fullFileName));
            String zeile;
            int iCount = 0;
            while ((zeile = fileReader.readLine()) != null) {
                // == 2 <> da es 2
                iCount += 1;
                if (LTBStudents.countLetter(zeile, ";".charAt(0)) == 2){
                    String[] parts = zeile.split(";", 3);
                    // Name, Email, rest?
                    Studentlist.add(new OneStudent(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                    //Logger.log( "info", methodenName, iCount + ": part[0]: " + parts[0].trim() + "\npart[1]: " + parts[1].trim() );
                } else {
                    logText = "Nicht genug ';' im String!";
                    Logger.log( "error", methodenName, logText);
                }
            }
            fileReader.close();
//            Logger.log( "info", methodenName, "Das ist das Ergebnis aus getHtml2PrintStudentList: \n" + getHtml2PrintStudentList(0, "Alle"));
            Logger.log( "info", methodenName, "Anzahl der Elemente: " + Studentlist.size());

        }
        catch (Exception e) {
            logText = e.getStackTrace()[0].getMethodName() + ": " + e.getStackTrace();
            Logger.log( "error", methodenName, logText);
            e.printStackTrace();
        }
    }




    /**
     * getHtml2PrintStudentList
     * Erzeuge aus OneStuden ein html-Body-Tabelle mit Inhalten
     * Ausgabetyp 0 -> alles, 1 -> nur abgegebene, 2 -> nur fehlende
     *
     * @param ausgabetyp
     * @param Klasse
     *
     * @return html-body
     *
     */
    public static String getHtml2PrintStudentList( int ausgabetyp, String Klasse) {
        String methodenName = KlassenName + ".getHtml2PrintStudentList";
        // alle Student ausgeben
        //String headline = HTMLformater.htmlHeadline("LTB Student", 2);
        String body = "<table><th>ID</th><th>Name</th><th>Klasse</th><th>Status</th><th>abgegeben bei</th>";
        String[] abgegeben = {"nicht abgegeben", "abgegeben"};
        for (int i = 0;i<Studentlist.size();i++){
            // Ausgabetyp 0 -> alles, 1 -> nur abgegebene, 2 -> nur fehlende
            if (ausgabetyp == 1 && Studentlist.get(i).getLtbStatus() != 1) {
                continue;
            }
            if (ausgabetyp == 2 && Studentlist.get(i).getLtbStatus() != 0) {
                continue;
            }
            if (Klasse.equals(Studentlist.get(i).getKlasse()) == true || Klasse.equals("Alle")) {
                String output = "<tr>"
                        + "<td>" + Studentlist.get(i).getID() + ": </td>"
                        + "<td>" + Studentlist.get(i).getFullname() + "</td>"
                        + "<td>" + Studentlist.get(i).getKlasse() + "</td>"
                        + "<td>" + abgegeben[Studentlist.get(i).getLtbStatus()] + "</td>"
                        + "<td>" +  Studentlist.get(i).getQRscannedBy() + "</td></tr>"; // hat " + LTBStudent.get(i).getLtbStatus() + "!";
                body = body + output;
            }
        }
        String logText = "html-String aufbereitet";
        Logger.log( "WHAT", methodenName, logText);
        return  body + "</table>";
    }
    /**
     * getter-Methode
     *
     * @return OneStudent.size();
     *
     */
    public static int getSizeOfStudentlist(){
        String methodenName = KlassenName + ".getOneStudentSize";
        String logText = "html-String aufbereitet";
        Logger.log( "WHAT", methodenName, logText);
        return Studentlist.size();
    }
    /**
     * getter-Methode
     *
     * @param i Nummer in der ArrayList OneStudent
     * @return getKlasse
     *
     */
    public static String getKlasse(int i){
        String methodenName = KlassenName + ".getKlasse";
        String logText = "html-String aufbereitet";
        Logger.log( "WHAT", methodenName, logText);
        return Studentlist.get(i).getKlasse();
    }
    /**
     * getter-Methode
     *
     * @param i Nummer in der ArrayList OneStudent
     * @return getVorname
     *
     */
    public static String getVorname(int i){
        String methodenName = KlassenName + ".getVorname";
        String logText = "html-String aufbereitet";
        Logger.log( "WHAT", methodenName, logText);
        return Studentlist.get(i).getRufname();
    }
    /**
     * getter-Methode
     *
     * @param i Nummer in der ArrayList OneStudent
     * @return getName
     *
     */
    public static String getName(int i){
        String methodenName = KlassenName + ".getName";
        String logText = "html-String aufbereitet";
        Logger.log( "WHAT", methodenName, logText);
        return Studentlist.get(i).getName();
    }
	/*
	/**
	* getter-Methode
	* @deprecated seit 0.8.0
	* @return getKlasse
	*
	*/
	/*
	public static void printArray( String[] printArray) {
		String methodenName = KlassenName + ".printArray";
		for (int i = 0;i<printArray.length;i++){
			System.out.println(printArray[i].toString());
		}
	}
	*/
    /**
     * clearOneStudent
     *
     * Lösche die ArrayListe
     *
     */
    public static void clearOneStudent(){
        String methodenName = KlassenName + ".clearOneStudent";
        String logText = "html-String aufbereitet";
        Logger.log( "info", methodenName, logText);
        Studentlist.clear();
    }

    /**
     * setter-MEthode
     *
     * @param id				- Schülernummer
     * @param QRscannedBy	- wer hat den QR-gescannt?
     *
     */
    public static void setLTBAbgegeben(int id, String QRscannedBy) {
        String methodenName = KlassenName + ".setLTBAbgegeben";
        String logText = "html-String aufbereitet";
        Logger.log( "WHAT", methodenName, logText);
        Studentlist.get(id).setLtbStatus(1, QRscannedBy);
    }
    /**
     * CheckKlasseUndName
     * gibt es den Namen in der Klasse?
     *
     * @return OK oder  ERROR
     *
     */
    public static String[] CheckKlasseUndName (String klasse, String name) {
        String methodenName = KlassenName + ".CheckKlasseUndName";
        String logText = "";
        // Prüfen ob Klasse, die Name und Absender Email erlaubt sind
        String[] returnvalue = {"NOTHING", "-1"};
        boolean testKlasse = false;
        boolean testName = false;

        for (int i = 0;i< Studentlist.size();i++){
            String ltbklasse = Studentlist.get(i).getKlasse();
            String ltbname = Studentlist.get(i).getFullname();
            if (ltbklasse.equals(klasse)){
                testKlasse = true;
                if (ltbname.equals(name)) {
                    // Klasse und name stimmt
                    testName = true;
                    returnvalue[1] = Integer.toString( i);
                    break;
                }
            }
        }
        if (testName == true){
            returnvalue[0] = "OK";
        } else if (testKlasse == true) {
            returnvalue[0] = "ERROR - Name nicht gefunden!";
        } else {
            returnvalue[0] = "ERROR - Klasse nicht gefunden!";
        }
        logText = "Gibt es " + name + " in der Klasse " + klasse + "? Antwort: " + returnvalue[0];
        Logger.log( "WHAT", methodenName, logText);
        return returnvalue;
    }
    /**
     * getKlassenByName
     * Hole einen Array über alle Klassen
     *
     * @return Klassenbezeichner
     *
     */
    public static String[] getKlassenByName() {
        String methodenName = KlassenName + ".getKlassenByName";
        ArrayList<String> KlassenNamen = new ArrayList<String>();
        for (int i = 0;i<Studentlist.size();i++){
            String Klasse = Studentlist.get(i).getKlasse();
            //System.out.println("Klasse = " + Klasse);
            if ( contains(KlassenNamen, Klasse) == false) {
                //System.out.println("Neuen Eintrag (Klasse); gefunden");
                KlassenNamen.add(Klasse);
            }
        }
        String[] Klassenbezeichner = KlassenNamen.toArray(new String[KlassenNamen.size()]);
        //System.out.println("Klassenbezeichner");
        //printArray(Klassenbezeichner);
        String logText = "Klassen nach Namen geholt.";
        Logger.log( "WHAT", methodenName, logText);
        return Klassenbezeichner;

    }
    /**
     * contains
     * enthält die ArrayList den String?
     *
     * @return true / false
     *
     */
    public static boolean contains(ArrayList<String> ArrayNamen, String stringToSearch)	{
        String methodenName = KlassenName + ".contains";
        String[] stringArray = ArrayNamen.toArray(new String[ArrayNamen.size()]);
        boolean result = false;
        for (String element:stringArray) {
            if ( element.equals( stringToSearch )) {
                result = true;
                break;
            }
        }
        String logText = "Die ArrayListe enthält den String " + stringToSearch + ".";
        Logger.log( "WHAT", methodenName, logText);
        return result;
    }

    /**
     * countLetter
     * Wie oft kommt das Zeichen letter im String vor?
     *
     * @return Anzahl der Treffer
     *
     *http://www.java-forum.org/thema/anzahl-eines-buchstaben-in-einem-string-zaehlen.82757/
     */
    public static int countLetter(String str, char letter) {
        String methodenName = KlassenName + ".countLetter";
        str = str.toLowerCase();
        letter = Character.toLowerCase(letter);
        int count = 0;
        for (int pos = -1; (pos = str.indexOf(letter, pos+1)) != -1; count++);
        String logText = "Der String |" + str + "| enthält das Zeichen |" + letter + "| " + count + " mal.";
        Logger.log( "WHAT", methodenName, logText);
        return count;
    }
}