package serverLoadRmi;

import api.IRemoteListener;
import api.ServerLoad;
import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteListener extends UnicastRemoteObject implements IRemoteListener {

    public RemoteListener() throws RemoteException {

    }

    /**
     *
     * @return returns the load of the machine that it is executed
     * @throws RemoteException
     */
    @Override
    public ServerLoad getServerLoad() throws RemoteException {
        ServerLoad serverLoad=new ServerLoad();

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);

        serverLoad.cpuLoad=osBean.getSystemCpuLoad();
        serverLoad.freeSystemMem=osBean.getFreePhysicalMemorySize();
        ///
        ///Disk usage
        ///
        File f= null;
        if(System.getProperty("os.name").toLowerCase().contains("mac")) {
            f= new File("/");
        }else if(System.getProperty("os.name").toLowerCase().contains("linux")) {
            f= new File("/");
        }else if(System.getProperty("os.name").toLowerCase().contains("windows")) {
            f= new File("C:");
        }else {
            System.out.println("Go Home, your OS is not supported");
        }

        serverLoad.diskUsage = ((f.getTotalSpace() / 1024/1024/1024 - f.getFreeSpace() /1024/1024/1024 ) * 100 ) / ((double)(f.getTotalSpace() / 1024/1024/1024)) ; // Prints in %
        return serverLoad;
    }
}