package org.loadbalancer;

import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadBalancer {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoadBalancer.class);

    public static void main(String[] args) throws Exception {
        String[] destinations = {
                "http://www.google.com?bridgeEndpoint=true",
                "http://www.bing.com?bridgeEndpoint=true"
        };


        Main main = new Main();
        main.enableHangupSupport();
        main.addRouteBuilder(new RoundRobinLoadBalancerRoute(destinations));
        main.addRouteBuilder(new WeightedRoundRobinLoadBalancerRoute("2,1",destinations));
        main.addRouteBuilder(new CurrentLoadLoadBalancerRoute(destinations));
        main.run();
    }

}
