package org.loadbalancer.statsExample;

import java.util.ArrayList;
import java.util.List;

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
     * @return eine Liste die alle Werte enthaelt
     */
    public List<String> cutter(String str) {
        List<String> l= new ArrayList<String>();

        StringBuilder sb= new StringBuilder(str);

        String[] ar= str.split("/");

        for(int i=0;i<ar.length;i++) {
            if(ar[i].charAt(0) >= '0' && ar[i].charAt(0) <= '9' ) {
                l.add(ar[i]);
                System.out.println("----> " + ar[i]);
            }
        }

        return l;
    }
}
