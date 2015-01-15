package drainer;

/**
 * @author Ari Michael Ayvazyan
 * @version 15.01.2015
 */
public class Drainer {
    public static void main (String args[]){

        Thread t= null;
        try {
            t= new Thread(new Load());
            t.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.stop();

    }
}
