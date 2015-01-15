package drainer;

import java.io.FileNotFoundException;

/**
 * @author Ari Michael Ayvazyan
 * @version 15.01.2015
 */
public class Drainer {
    public static void main (String args[]){

        Thread t= null;

        Load load= null;
        try {
            load = new Load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        t= new Thread(load);
        t.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        load.stop();

    }
}
