package org.loadbalancer.statsExample;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by helmuthbrunner on 13/01/15.
 */
public class Server {
    public static void main(String[] args) throws Exception {

        ServerSocket servSocket;
        Socket fromClientSocket;
        int cTosPortNumber = 1777;
        String str;
        Stats comp;

        servSocket = new ServerSocket(cTosPortNumber);
        System.out.println("Waiting for a connection on " + cTosPortNumber);

        fromClientSocket = servSocket.accept();

        ObjectOutputStream oos = new ObjectOutputStream(fromClientSocket.getOutputStream());

        ObjectInputStream ois = new ObjectInputStream(fromClientSocket.getInputStream());

        while ((comp = (Stats) ois.readObject()) != null) {
            System.out.println( comp.toString() );
            break;
        }
        oos.close();

        fromClientSocket.close();
    }
}
