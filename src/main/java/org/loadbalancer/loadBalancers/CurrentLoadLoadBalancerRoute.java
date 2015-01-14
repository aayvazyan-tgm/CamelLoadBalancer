package org.loadbalancer.loadBalancers;

import org.apache.camel.builder.RouteBuilder;

public class CurrentLoadLoadBalancerRoute extends RouteBuilder {
    private String[] destinations;

    /**
     * @param destinations the destinations to Route to
     */
    public CurrentLoadLoadBalancerRoute(String... destinations){
        this.destinations=destinations;
    }

    @Override
    public void configure() throws Exception {
        from("jetty:http://0.0.0.0:8081/c?matchOnUriPrefix=true")
                        //.to("log:org.loadbalancer.IN?showAll=true&multiline=true")
                .loadBalance(new MyCustomLoadBalancerSupport())
                .to(destinations)
        ;
    }
}
