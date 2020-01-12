package com.example;

import java.util.LinkedList;

interface IProducer<T, E extends Exception> extends AutoCloseable {
    T produce() throws E;
}

interface IConsumer<T, E extends Exception> extends AutoCloseable {
    void consume(T t) throws E;
}

class IntProducer implements IProducer<Integer, InterruptedException> {
    Integer count = 0;
    public IntProducer(int start) {
        count = start;
    }

    @Override
    public Integer produce() throws InterruptedException {
        Integer value = count;
        System.out.println("Produced : " + value);
        Thread.sleep(1000);
        count++;
        return value;
    }

    @Override
    public void close() throws Exception {
    }
}

class PrintConsumer<T> implements IConsumer<T, InterruptedException> {

    @Override
    public void consume(T t) throws InterruptedException {
        System.out.println("Consumed : " + t.toString());
        Thread.sleep(1000);
    }

    @Override
    public void close() throws Exception {
    }
}

public class ProducerConsumer<T, PE extends Exception, CE extends Exception> {
    LinkedList<T> list = new LinkedList<>();
    int capacity;
    IProducer <T,PE> producer;
    IConsumer <T,CE> consumer;

    ProducerConsumer(int capacity, IProducer<T, PE> producer, IConsumer<T, CE> consumer) {
        this.capacity = capacity;
        this.producer = producer;
        this.consumer = consumer;
    }

    public void run() {
        Thread pthread = new Thread(new Runnable() {
			@Override
			public void run() {
                try{
                    while(true) {
                        synchronized(ProducerConsumer.this) {
                            while(list.size() == capacity) {
                                ProducerConsumer.this.wait();
                            }
                        }
                        T value = null;
                        try {
                            value = producer.produce();
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                        synchronized(ProducerConsumer.this) {
                            list.add(value);
                            ProducerConsumer.this.notify();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}
        });
        Thread cthread = new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    while(true) {
                        T value = null;
                        synchronized(ProducerConsumer.this) {
                            while(list.size() == 0) {
                                ProducerConsumer.this.wait();
                            }
                            value = list.removeFirst();
                            ProducerConsumer.this.notify();
                        }
                        try {
                            consumer.consume(value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }        
            }
        });

        pthread.start();
        cthread.start();
        
        try {
            pthread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        try {
            cthread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        IProducer<Integer, InterruptedException> producer = new IntProducer(1);
        IConsumer<Integer, InterruptedException> consumer = new PrintConsumer<Integer>();
        ProducerConsumer<Integer, InterruptedException, InterruptedException> pc =
            new ProducerConsumer<>(10, producer, consumer);
        pc.run();
    }
}