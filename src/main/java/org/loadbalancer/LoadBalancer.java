package org.loadbalancer;

import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class LoadBalancer {

	public static final Logger LOGGER = LoggerFactory.getLogger(LoadBalancer.class);

	public static void main(String[] args) throws Exception{
			Main main=new Main();
			main.enableHangupSupport();
			main.addRouteBuilder(new LoadBalancerRoute());
			main.run();

			//Main.main(args);
	}

}
