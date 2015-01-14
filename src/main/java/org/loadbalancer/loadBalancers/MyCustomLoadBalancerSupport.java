package org.loadbalancer.loadBalancers;

import api.ServerLoad;
import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.processor.loadbalancer.LoadBalancerSupport;
import org.loadbalancer.rmi.RmiClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MyCustomLoadBalancerSupport extends LoadBalancerSupport {

    public boolean process(Exchange exchange, AsyncCallback callback) {
        String body = exchange.getIn().getBody(String.class);
        //Get all destinations
        List<Processor> processors = getProcessors();
        LinkedList<DestinationLoad> targets = new LinkedList<DestinationLoad>();
        //Iterate over the destinations, evaluate their load and use the least busy destination
        try {
            for (Processor currentServer : processors) {
                DestinationLoad targetServDestLoad = new DestinationLoad(currentServer);
                targets.add(targetServDestLoad);
            }
            targets.sort(new DestinationLoadIntegerComparator());
            targets.forEach((el) -> {
                System.out.println(el.getEvaluatedLoad() + " | " + el.destinationURI);
            });
            targets.getFirst().processor.process(exchange);
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.done(true);
        return true;
    }

    public static String extractEndpoint(String inp) {
        String endPointURIString = inp;
        endPointURIString = endPointURIString.substring(endPointURIString.indexOf("Endpoint[") + 9, endPointURIString.length());
        endPointURIString = endPointURIString.substring(0, endPointURIString.indexOf("]"));
        return endPointURIString;
    }

    private class DestinationLoad {
        private Processor processor;
        private String destinationURI;
        private long freeSystemMem;
        private double cpuLoad;
        private double diskUsage;

        public DestinationLoad(Processor processor) throws MalformedURLException, RemoteException, NotBoundException {
            this.processor = processor;
            this.destinationURI = extractEndpoint(processor.toString());
            URL destinationURL = new URL(destinationURI);

            String destinationHost = destinationURL.getHost();

            System.out.println("Destination Host: " + destinationHost);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ServerLoad serverLoad = RmiClient.getServerLoad(destinationHost);

            this.freeSystemMem = serverLoad.freeSystemMem;
            this.cpuLoad = serverLoad.cpuLoad;
            this.diskUsage=serverLoad.diskUsage;
        }

        /**
         * low load => low value
         *
         * @return
         */
        public double evaluateLoad() {
            double result = 0;
            result += cpuLoad;
            if (freeSystemMem < 1024 * 100) result += 80;
            if (freeSystemMem < 1024 * 20) result += 100;
            if (diskUsage>50)result+=10;
            if (diskUsage>80)result+=40;
            if (diskUsage>90)result+=150;
            if (diskUsage>95)result+=200;
            System.out.println(diskUsage);
            return result;
        }

        public double getEvaluatedLoad() {
            return evaluateLoad();
        }
    }

    private class DestinationLoadIntegerComparator implements Comparator {
        public int compare(Object obj1, Object obj2) {
            DestinationLoad apk1 = (DestinationLoad) obj1;
            DestinationLoad apk2 = (DestinationLoad) obj2;
            Double iobj1 = apk1.getEvaluatedLoad();
            Double iobj2 = apk2.getEvaluatedLoad();

            int result = iobj1.compareTo(iobj2);
            return result;
        }
    }
}