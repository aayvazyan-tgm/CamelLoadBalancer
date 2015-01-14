package org.loadbalancer.rmi;

import api.IRemoteListener;
import api.ServerLoad;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiClient {
    /**
     * //@param serverSocket should have the format hostAdress:port - example: localhost:1234
     */
    public static ServerLoad getServerLoad(String host) throws RemoteException, MalformedURLException, NotBoundException {
        //String[] args=serverSocket.split(":");
        //String url = "rmi://"+args[0]+":"+args[1]+"/myRmi";
        int port = 7771;
        String url = "rmi://" + host + ":" + port + "/myRmi";
        IRemoteListener listener = (IRemoteListener) Naming.lookup(url);
        return listener.getServerLoad();
    }
}