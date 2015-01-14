package serverLoadRmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String[] args) {
        try {
            int port = 7771;
            LocateRegistry.createRegistry(port);
            Naming.rebind("rmi://localhost:"+port+"/myRmi", new RemoteListener());
            System.out.println("RMI Server started");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}