package de.bischinger.parrot.commands.handler;

import java.lang.invoke.MethodHandles;

import java.net.DatagramSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;


/**
 * @author  Tobias Schneider
 */
public class ProducerConsumer {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().toString());

    private final BlockingQueue<DatagramSocket> queue;
    private final List<MyConsumer> consumers;
    private final DatagramSocket controller2DeviceSocket;

    public ProducerConsumer(int queueSize, DatagramSocket controller2DeviceSocket) {

        queue = new ArrayBlockingQueue<>(queueSize);
        consumers = new ArrayList<>();
        this.controller2DeviceSocket = controller2DeviceSocket;
    }

//    public MyProducer createProducer() {
//
//        MyProducer tMyProducer = new MyProducer<>(queue);
//        producers.add(tMyProducer);
//
//        return tMyProducer;
//    }

    public MyConsumer createConsumer() {

        MyConsumer tMyConsumer = new MyConsumer(queue, controller2DeviceSocket);
        consumers.add(tMyConsumer);

        return tMyConsumer;
    }


    public void printInfo() {

        LOGGER.info("#Consumers: " + consumers.size());
    }


    public void addCommand(DatagramSocket command) {

        try {
            queue.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
//    public static class MyProducer implements Runnable {
//
//        private BlockingQueue queue;
//
//        public MyProducer(BlockingQueue queue) {
//
//            this.queue = queue;
//        }
//
//        @Override
//        public void run() {
//
//            final T msg = supplier.get();
//
//            try {
//                queue.put(msg);
//                out.println("Added command: " + msg);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public static class MyConsumer implements Runnable {

        private final BlockingQueue<DatagramSocket> queue;
        private final DatagramSocket controller2DeviceSocket;

        public MyConsumer(BlockingQueue<DatagramSocket> queue, DatagramSocket controller2DeviceSocket) {

            this.queue = queue;
            this.controller2DeviceSocket = controller2DeviceSocket;
        }

        @Override
        public void run() {

//            while (true) {
//                try {
//                    controller2DeviceSocket.send(queue.take());
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
        }
    }
}
