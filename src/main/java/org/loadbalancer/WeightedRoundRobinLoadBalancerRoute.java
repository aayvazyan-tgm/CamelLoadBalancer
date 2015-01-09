package org.loadbalancer;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.loadbalancer.WeightedRoundRobinLoadBalancer;

import java.util.ArrayList;

public class WeightedRoundRobinLoadBalancerRoute extends RouteBuilder {
    private String ratio;
    private String[] destinations;

    /**
     * @param destinations the destinations to Route to
     */
    public WeightedRoundRobinLoadBalancerRoute(String ratio, String... destinations){
        this.ratio = ratio;
        this.destinations=destinations;
    }

    @Override
    public void configure() throws Exception {
        System.out.println("Configure");

        from("jetty:http://0.0.0.0:8082/?matchOnUriPrefix=true")
                .routeId("WeightedLOADBALANCER")
                        //.to("log:org.loadbalancer.IN?showAll=true&multiline=true")
                .loadBalance()
                .weighted(true,ratio)
                .to(destinations)
        ;
    }
}
