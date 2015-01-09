package org.loadbalancer;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.processor.loadbalancer.LoadBalancerSupport;

public class MyCustomLoadBalancerSupport extends LoadBalancerSupport {
 
    public boolean process(Exchange exchange, AsyncCallback callback) {
        String body = exchange.getIn().getBody(String.class);
        try {
            if ("x".equals(body)) {
                getProcessors().get(0).process(exchange);
            } else if ("y".equals(body)) {
                getProcessors().get(1).process(exchange);
            } else {
                getProcessors().get(2).process(exchange);
            }
        } catch (Throwable e) {
            exchange.setException(e);
        }
        callback.done(true);
        return true;
    }
}