package org.loadbalancer;

import com.sun.management.OperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;

/**
 * @author Ari Michael Ayvazyan
 * @version 08.01.2015
 */
public class SystemStatChecker {
    public static final Logger LOGGER = LoggerFactory.getLogger(SystemStatChecker.class);

    public static void printStats() {
        try {
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                    OperatingSystemMXBean.class);

            // The % CPU load this current JVM is using, from 0.0-1.0
            System.out.println("ProcessLoad:" + osBean.getProcessCpuLoad());

            // The % load the overall system is at, from 0.0-1.0
            System.out.println("SystenCPULoad" + osBean.getSystemCpuLoad());

            //
            System.out.println("FreeSysMem" + osBean.getFreePhysicalMemorySize());

            //
            System.out.println("TotalMem" + osBean.getTotalPhysicalMemorySize());
        } catch (Exception e) {
            LOGGER.error("Error : ", e);
        }
    }
}
