package api;

import java.io.Serializable;

/**
 * @author Ari Michael Ayvazyan
 * @version 14.01.2015
 */
public class ServerLoad implements Serializable{
    static final long serialVersionUID=412345;

    public long freeSystemMem;
    public double cpuLoad;
    public double diskUsage;
}
