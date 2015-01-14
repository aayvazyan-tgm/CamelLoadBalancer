package org.loadbalancer.statsExample;

import com.sun.management.OperatingSystemMXBean;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.Socket;

/**
 * The Client which sends the stats to the server
 *
 * Created by helmuthbrunner on 13/01/15.
 */
public class Client {

    private Socket clientSocket;
    private ObjectOutputStream ops;
    private ObjectInputStream ois;
    private Stats s;

    public Client(String host, int port) {

        try {

            clientSocket= new Socket(InetAddress.getLocalHost(), port);
            ops= new ObjectOutputStream(clientSocket.getOutputStream());
            ois= new ObjectInputStream(clientSocket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Invokes to send the current stats from the client to the server
     */
    public void sendStats() {

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        Stats s= new Stats();

        s.setFreePhysicalMemorySize(osBean.getFreePhysicalMemorySize());
        s.setProcessCpuLoad(osBean.getProcessCpuLoad());
        s.setSystemCpuLoad(osBean.getSystemCpuLoad());
        s.setTotalPhysicalMemorySize(osBean.getTotalPhysicalMemorySize());

        try {

            ops.writeObject(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}