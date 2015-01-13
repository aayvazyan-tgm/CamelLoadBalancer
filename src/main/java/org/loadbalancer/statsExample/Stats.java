package org.loadbalancer.statsExample;

import com.sun.management.OperatingSystemMXBean;
import java.io.Serializable;

/**
 * A class which stores the System stats
 *
 * Created by helmuthbrunner on 13/01/15.
 */
public class Stats implements Serializable {

    private double processCpuLoad, systemCpuLoad;
    private long freePhysicalMemorySize, totalPhysicalMemorySize;

    public Stats() {
    }

    public void setProcessCpuLoad(double processCpuLoad) {
        this.processCpuLoad = processCpuLoad;
    }

    public void setSystemCpuLoad(double systemCpuLoad) {
        this.systemCpuLoad = systemCpuLoad;
    }

    public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
        this.freePhysicalMemorySize = freePhysicalMemorySize;
    }

    public void setTotalPhysicalMemorySize(long totalPhysicalMemorySize) {
        this.totalPhysicalMemorySize = totalPhysicalMemorySize;
    }

    public double getProcessCpuLoad() {
        return this.processCpuLoad;
    }

    public double getSystemCpuLoad() {
        return this.systemCpuLoad;
    }

    public long getFreePhysicalMemorySize() {
        return this.freePhysicalMemorySize;
    }

    public long getTotalPhysicalMemorySize() {
        return this.totalPhysicalMemorySize;
    }

    @Override
    public String toString() {
        return  "\nProcessCpuLoad: " 			+ this.getProcessCpuLoad() +
                "\nSystemCpuLoad: " 			+ this.getSystemCpuLoad() +
                "\nFreePhysicalMemorySize: "	+ this.getFreePhysicalMemorySize() +
                "\nTotalPhysicalMemorySize: " 	+ this.getTotalPhysicalMemorySize();
    }

}
