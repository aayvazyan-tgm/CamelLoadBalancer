package org.loadbalancer;

import org.apache.camel.builder.RouteBuilder;

public class LoadBalancerRoute extends RouteBuilder {


    private String inputPort = "15000";


    private String[] destinations = {
            "http://www.google.com:8080",
            "http://www.bing.com:8080",
            "http://www.duckduckgo.com:8080"
    };

    @Override
    public void configure() throws Exception {
        from("jetty:http://localhost:8080/apache-camel-Load-Balancer/")
                .routeId("LOADBALANCER")
                .to("http://www.duckduckgo.com:8080")
                        //.to("log:org.loadbalancer.IN?showAll=true&multiline=true")
                .loadBalance()
                .roundRobin()
                .to(destinations);
    }

}
