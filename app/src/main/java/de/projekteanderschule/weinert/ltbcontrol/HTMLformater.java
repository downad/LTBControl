package de.projekteanderschule.weinert.ltbcontrol;

/**
 * Created by ralf on 15.05.17.
 */

public class HTMLformater {

    /**
     * htmlHeadline
     * baut aus dem htmlBody eine fertige html-Seite mit doc, html und head
     *
     * @param text 			Text für eine Überschrift
     * @param height			Category der Überschrift, 2 = <h2>, 3 = <h3< usw.
     * @return htmlTitle		eine html-formatierte Headline
     */
    public static String htmlHeadline (String text, int height){
        return "<h" + height + " align='center'>" + text + "</h" + height + ">";
    }

    /**
     * htmlBasic
     * baut aus dem htmlBody eine fertige html-Seite mit doc, html und head
     *
     * @param headline      ein String-Text
     * @param body 			ein html-formatierter String-Text
     *
     * @return htmlBasic		eine fertige html-Seite mit doc, html und head
     */
    public static String createHtml (String headline, String body) {
        String htmlBasic = "<!doctype html>"
                +"<html lang='de'>"
                +"  <head>"
                +"    <meta charset='utf-8' />"
                +"    <title>" + headline + "</title>"
                +"  </head>"
                +"  <body>"
                +"      <h2 align=\"center\" >" +  headline + "</h2>"
                + body
                +"  </body>"
                +"</html>";
        return htmlBasic;
    }
    public static String createHtmlWithoutHeadline (String body) {
        String htmlBasic = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
                +"<html xmlns=\"http://www.w3.org/1999/xhtml\">" //<!doctype html>"
               // +"<html lang='de'>"
                +"  <head>"
                +"    <meta charset='utf-8' />"
                +"    <title>" + "</title>"
                +"  </head>"
                +"  <body>"
                + body
                +"  </body>"
                +"</html>";
        return htmlBasic;
    }
}
