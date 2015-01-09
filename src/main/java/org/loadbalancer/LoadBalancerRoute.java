package org.loadbalancer;

import org.apache.camel.builder.RouteBuilder;

public class LoadBalancerRoute extends RouteBuilder {


    private String[] destinations = {
            "http://www.google.com?bridgeEndpoint=true",
            "http://www.bing.com?bridgeEndpoint=true",
    };

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
