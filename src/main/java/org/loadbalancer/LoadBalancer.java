package org.loadbalancer;

import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class LoadBalancer {

	public static final Logger LOGGER = LoggerFactory.getLogger(LoadBalancer.class);

	public static void main(String[] args) {
		try {
			//Main.main(args);
			OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
					OperatingSystemMXBean.class);
			for (int i = 0; i < 20; i++) {
				// What % CPU load this current JVM is taking, from 0.0-1.0
				System.out.println("ProcessLoad:" + osBean.getProcessCpuLoad());

				// What % load the overall system is at, from 0.0-1.0
				System.out.println("SystenCPULoad" + osBean.getSystemCpuLoad());
				System.out.println("FreeSysMem" + osBean.getFreePhysicalMemorySize());
				System.out.println("TotalMem" + osBean.getTotalPhysicalMemorySize());
				Thread.sleep(200);
			}
		} catch (Exception e) {
			LOGGER.error("Error : ", e);
		}
	}

}
