package org.loadbalancer;

import org.apache.camel.main.Main;
import org.loadbalancer.loadBalancers.CurrentLoadLoadBalancerRoute;
import org.loadbalancer.loadBalancers.RoundRobinLoadBalancerRoute;
import org.loadbalancer.loadBalancers.WeightedRoundRobinLoadBalancerRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ari Michael Ayvazyan
 * @version 14.01.2015
 */
public class LoadBalancer {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoadBalancer.class);

    /**
     *
     * @param args socket Strings (host:port)
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String[] destinations = new String[args.length];
        if (args.length==0){
            System.out.println("defaulting destinations");
            destinations=new String[]{
                    "http://localhost:8083?bridgeEndpoint=true",
                    "http://localhost:8084?bridgeEndpoint=true"
            };
        }else {
            for (int i = 0; i < args.length; i++) {
                destinations[i] = "http://" + args[i] + "?bridgeEndpoint=true";
                System.out.println("registered destination: "+destinations[i]);
            }
        }

        Main main = new Main();
        main.enableHangupSupport();
        main.addRouteBuilder(new RoundRobinLoadBalancerRoute(destinations));
        main.addRouteBuilder(new WeightedRoundRobinLoadBalancerRoute("2,1",destinations));
        main.addRouteBuilder(new CurrentLoadLoadBalancerRoute(destinations));
        main.run();
        System.out.println("Started Load Balancer");
    }

}
