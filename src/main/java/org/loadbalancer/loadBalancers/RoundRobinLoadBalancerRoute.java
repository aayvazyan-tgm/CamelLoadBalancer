package org.loadbalancer.loadBalancers;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author Ari Michael Ayvazyan
 * @version 14.01.2015
 */
public class RoundRobinLoadBalancerRoute extends RouteBuilder {
    private String[] destinations;

    /**
     * @param destinations the destinations to Route to
     */
    public RoundRobinLoadBalancerRoute(String ... destinations){
        this.destinations=destinations;
    }

    @Override
    public void configure() throws Exception {
        from("jetty:http://0.0.0.0:8081/rr?matchOnUriPrefix=true")
                .routeId("LOADBALANCER")
                        //.to("log:org.loadbalancer.IN?showAll=true&multiline=true")
                .loadBalance()
                .roundRobin()
                .to(destinations)
        ;
    }
}
