package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Ari Michael Ayvazyan
 * @version 14.01.2015
 */
public interface IRemoteListener extends Remote {
    /**
     *
     * @return returns the System Load
     * @throws RemoteException
     */
    public ServerLoad getServerLoad() throws RemoteException;

}