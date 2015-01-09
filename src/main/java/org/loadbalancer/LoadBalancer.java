package org.loadbalancer;

import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadBalancer {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoadBalancer.class);

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.enableHangupSupport();
        main.bind("foo", new MyBean());
        main.addRouteBuilder(new LoadBalancerRoute());
        main.run();
    }
    public static class MyBean {
        public void callMe() {
            System.out.println("MyBean.callMe method has been called");
        }
    }
}
