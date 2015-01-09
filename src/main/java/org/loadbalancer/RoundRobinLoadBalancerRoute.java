package org.loadbalancer;

import org.apache.camel.builder.RouteBuilder;

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
        System.out.println("Configure");
        from("jetty:http://0.0.0.0:8081/?matchOnUriPrefix=true")
                .routeId("LOADBALANCER")
                        //.to("log:org.loadbalancer.IN?showAll=true&multiline=true")
                .loadBalance()
                .roundRobin()
                .to(destinations)
        ;
    }
}
