package loadgenarator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.Random;

/**
 * Erzeugt unnoeting System load
 *
 * Created by helmuthbrunner on 15/01/15.
 */
public class Load {

    private boolean running;
    private File f;
    private BigInteger bi, bi2;
    private RandomAccessFile raf;
    private Random r;

    public Load() throws FileNotFoundException {
        running= true;
        bi= new BigInteger(String.valueOf(Long.MAX_VALUE));
        f= new File(System.getProperty("user.home") + "/out.txt");
        raf= new RandomAccessFile(f, "rw");
        r= new Random();
    }

    public void run() {
        while (running) {

            bi2 = bi.pow(33 * r.nextInt(3333));
            try {
                raf.writeUTF(bi2.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
