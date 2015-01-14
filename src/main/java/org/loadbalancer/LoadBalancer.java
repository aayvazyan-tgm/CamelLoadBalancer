package org.loadbalancer;

import org.apache.camel.main.Main;
import org.loadbalancer.statsExample.Client;
import org.loadbalancer.statsExample.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadBalancer {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoadBalancer.class);

    public static void main(String[] args) throws Exception {
        String[] destinations = {
                "http://localhost:8082?bridgeEndpoint=true",
                "http://localhost:8083?bridgeEndpoint=true"
        };

        Main main = new Main();
        main.enableHangupSupport();
        main.addRouteBuilder(new RoundRobinLoadBalancerRoute(destinations));
        main.addRouteBuilder(new WeightedRoundRobinLoadBalancerRoute("2,1",destinations));
        main.addRouteBuilder(new CurrentLoadLoadBalancerRoute(destinations));
        main.run();
    }

}
