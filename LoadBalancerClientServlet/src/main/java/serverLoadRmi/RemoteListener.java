package serverLoadRmi;

import api.IRemoteListener;
import api.ServerLoad;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteListener extends UnicastRemoteObject implements IRemoteListener {

    public RemoteListener() throws RemoteException {

    }

    @Override
    public ServerLoad getServerLoad() throws RemoteException {
        ServerLoad serverLoad=new ServerLoad();

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);

        serverLoad.cpuLoad=osBean.getSystemCpuLoad();
        serverLoad.freeSystemMem=osBean.getFreePhysicalMemorySize();
        serverLoad.diskUsage=0;

        return serverLoad;
    }
}