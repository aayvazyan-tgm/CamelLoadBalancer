package org.loadbalancer.statsExample;

/**
 * Eine Klasse die die Nachrichten vom Server verarbeitet.
 *
 * Created by helmuthbrunner on 14/01/15.
 */
public class MessageCutter {

    /**
     * Default Konstruktor
     * hat keine Funktion
     */
    public MessageCutter() {
    }

    /**
     * Eine Methode die den Text nach einer bestimmten Textsequenz herausnimmt und zurueck gibt.
     * @param str der gesamte Text
     * @param seq die Sequenz in dem Text
     * @return
     */
    public String cutter(String str, String seq) {
        String result="";

        StringBuilder sb= new StringBuilder(str);

        int index= sb.indexOf(seq);
        result= sb.substring(index+2);

        return result;
    }

    /**
     * Wandelt den Wert in eine Zahl um.
     * @param str der Text von dem die Sequenzheraus geschnitten werden soll
     * @param seq die Sequenz die angibt ab wann die Zahlen beginnen
     * @return den die ganzzahlige Zahl
     */
    public int getIntValue(String str, String seq) {
        return Integer.valueOf(this.cutter(str, seq));
    }

    /**
     * Wandelt den Wert in ein Gleitkommazahl um.
     * @param str der Text von dem die Sequenzheraus geschnitten werden soll
     * @param seq die Sequenz die angibt ab wann die Zahlen beginnen
     * @return die Gleitkommazahl
     */
    public double getDoubleValue(String str, String seq) {
        return Double.valueOf(this.cutter(str, seq));
    }
}
