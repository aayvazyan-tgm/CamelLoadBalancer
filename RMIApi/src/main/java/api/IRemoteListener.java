package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteListener extends Remote {

    public ServerLoad getServerLoad() throws RemoteException;

}