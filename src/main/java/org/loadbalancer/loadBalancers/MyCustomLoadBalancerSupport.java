package org.loadbalancer.loadBalancers;

import api.ServerLoad;
import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.http.HttpMessage;
import org.apache.camel.processor.loadbalancer.LoadBalancerSupport;
import org.loadbalancer.rmi.RmiClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * @author Ari Michael Ayvazyan
 * @version 14.01.2015
 */
public class MyCustomLoadBalancerSupport extends LoadBalancerSupport {
    private HashMap<Integer,Processor> stickyUserMapping=new HashMap<Integer,Processor>();

    /**
     * @see org.apache.camel.processor.loadbalancer.LoadBalancerSupport
     */
    public boolean process(Exchange exchange, AsyncCallback callback) {
        String body = exchange.getIn().getBody(String.class);
        //Get all destinations
        List<Processor> processors = getProcessors();
        LinkedList<DestinationLoad> targets = new LinkedList<DestinationLoad>();
        //Iterate over the destinations, evaluate their load and use the least busy destination
        String remoteIP=exchange.getIn(HttpMessage.class).getRequest().getRemoteAddr();
        int userIDHash=+Objects.hash(
                exchange.getIn().getHeader("User-Agent"),
                remoteIP
                );
        try {
            if(stickyUserMapping.containsKey(userIDHash)){
                System.out.println("User with ID: "+userIDHash+" is being redirected to his bound server");
                stickyUserMapping.get(userIDHash).process(exchange);
            }else{
                System.out.println("Finding a Server for user: "+userIDHash);
                for (Processor currentServer : processors) {
                    DestinationLoad targetServDestLoad = new DestinationLoad(currentServer);
                    targets.add(targetServDestLoad);
                }
                targets.sort(new DestinationLoadIntegerComparator());
                for (DestinationLoad el : targets) {
                    System.out.println(el.getEvaluatedLoad() + " | " + el.destinationURI);
                }
                Processor leastBusyServer=targets.getFirst().processor;
                stickyUserMapping.put(userIDHash,leastBusyServer);
                System.out.println("User with ID: "+userIDHash+" is now Bound to the currently least busy server: "+targets.getFirst().destinationURI);
                leastBusyServer.process(exchange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.done(true);
        return true;
    }

    /**
     *
     * @param inp the Endpoint from which the uri should be extracted
     * @return the URI as a String
     */
    public static String extractEndpoint(String inp) {
        String endPointURIString = inp;
        endPointURIString = endPointURIString.substring(endPointURIString.indexOf("Endpoint[") + 9, endPointURIString.length());
        endPointURIString = endPointURIString.substring(0, endPointURIString.indexOf("]"));
        return endPointURIString;
    }

    /**
     * Defines the load of a Server
     */
    private class DestinationLoad {
        private Processor processor;
        private String destinationURI;
        private long freeSystemMem;
        private double cpuLoad;
        private double diskUsage;

        /**
         *
         * @param processor the server representation
         * @throws MalformedURLException
         * @throws RemoteException
         * @throws NotBoundException
         */
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
         * low load results in a low value
         *
         * @return a rating where a high value represents a high load
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

    /**
     * compares the load of the Servers
     */
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