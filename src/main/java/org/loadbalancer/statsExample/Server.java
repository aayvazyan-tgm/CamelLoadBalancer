package org.loadbalancer.statsExample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The the Server class
 *
 * @author Helmuth Brunner, helmuth.brunner@student.tgm.ac.at
 * @version Jan 13, 2015
 */
public class Server {

    private Stats stats;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private ServerSocket servSocket;
    private Socket fromClientSocket;
    private int portNumber = 1777;

    /**
     * Constructor
     */
    public Server(int portNumber) {
        this.portNumber= portNumber;

        try {
            servSocket = new ServerSocket(this.portNumber);
            System.out.println("Waiting for a connection on " + this.portNumber); // for debug

            fromClientSocket = servSocket.accept();

            oos = new ObjectOutputStream(fromClientSocket.getOutputStream());

            ois = new ObjectInputStream(fromClientSocket.getInputStream());
        } catch(BindException e) {
            System.out.println("There is a bind execption");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    @Override
    public void run() {

        try {
            servSocket = new ServerSocket(this.portNumber);
            System.out.println("Waiting for a connection on " + this.portNumber); // for debug

            fromClientSocket = servSocket.accept();

            oos = new ObjectOutputStream(fromClientSocket.getOutputStream());

            ois = new ObjectInputStream(fromClientSocket.getInputStream());
        } catch(BindException e) {
            System.out.println("There is a bind execption");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    */

    /**
     * Get the stats from the client
     *
     * @return the stats
     */
    public Stats getStats() {
        try {
            while ((stats = (Stats) ois.readObject()) != null) {
                break;
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return stats;
    }

    /**
     * Closes all connections.
     *
     * @return
     */
    public boolean close() {
        try {
            oos.close();
            ois.close();
            fromClientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
