package org.loadbalancer;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.management.InstrumentationProcessor;
import org.apache.camel.processor.SendProcessor;
import org.apache.camel.processor.interceptor.DefaultChannel;
import org.apache.camel.processor.loadbalancer.LoadBalancerSupport;

import javax.activation.DataHandler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class MyCustomLoadBalancerSupport extends LoadBalancerSupport {
 
    public boolean process(Exchange exchange, AsyncCallback callback) {
        String body = exchange.getIn().getBody(String.class);
        System.out.println("body"+body);
        //Get all destinations
        List<Processor> processors = getProcessors();
        LinkedList<DestinationLoad>targets=new LinkedList<DestinationLoad>();
        //Iterate over the destinations, evaluate their load and use the least busy destination
        try {
        for(Processor currentServer : processors){
                DestinationLoad targetServDestLoad=new DestinationLoad(currentServer);
                targets.add(targetServDestLoad);
        }
        targets.sort(new DestinationLoadIntegerComparator());

            targets.getFirst().processor.process(exchange);
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.done(true);
        return true;
    }

    public static String extractEndpoint(String inp){
        String endPointURIString=inp;
        endPointURIString=endPointURIString.substring(endPointURIString.indexOf("Endpoint[")+9,endPointURIString.length());
        endPointURIString=endPointURIString.substring(0,endPointURIString.indexOf("]"));
        return endPointURIString;
    }

    private class DestinationLoad {
        private Processor processor;//
        private String destinationURI; //
        private int evaluatedLoad;
        private long freeSystemMem;
        private double cpuLoad;

        public DestinationLoad(Processor processor) throws MalformedURLException {
            this.processor = processor;
            this.destinationURI = extractEndpoint(processor.toString());
            URL destinationURL=new URL(destinationURI);

            String destinationHost =destinationURL.getHost();

            //TODO Helmuth FETCH FROM SERVER (destinationHost)

            this.freeSystemMem=5000;
            this.cpuLoad=50.4;
        }

        /**
         * low load => low value
         * @return
         */
        public int evaluateLoad(){
            int result=0;
            result+=cpuLoad;
            if(freeSystemMem<1024*100)result+=80;
            if(freeSystemMem<1024*20)result+=100;
            return result;
        }

        public int getEvaluatedLoad() {
            return evaluateLoad();
        }
    }

    private class DestinationLoadIntegerComparator implements Comparator
    {
        public int compare(Object obj1, Object obj2)
        {
            DestinationLoad apk1 = (DestinationLoad) obj1;
            DestinationLoad apk2 = (DestinationLoad) obj2;
            Integer iobj1=apk1.evaluatedLoad;
            Integer iobj2=apk2.evaluatedLoad;

            int result = iobj1
                    .compareTo(iobj1);
            return result;
        }
    }
}