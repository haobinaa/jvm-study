package com.haobin.jvmstudy.concurrent;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * @Author HaoBin
 * @Create 2020/3/27 11:52
 * @Description: 无锁队列
 *
 * 参考资料: https://tech.meituan.com/2016/11/18/disruptor.html   美团-高性能队列 Disruptor
 **/
public class DisruptorT {


    static class OrderEvent {
        private String id;

        String getId() {
            return id;
        }

        void setId(String id) {
            this.id = id;
        }
    }

    static class OrderEventProducer {
        private final RingBuffer<OrderEvent> ringBuffer;
        OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }
        public void onData(String orderId) {
            long sequence = ringBuffer.next();
            try {
                OrderEvent orderEvent = ringBuffer.get(sequence);
                orderEvent.setId(orderId);
            } finally {
                ringBuffer.publish(sequence);
            }
        }
    }

    static class OrderEventHandler implements EventHandler<OrderEvent>, WorkHandler<OrderEvent> {

        @Override
        public void onEvent(OrderEvent orderEvent, long sequence, boolean endOfBatch) throws Exception {
            System.out.println("event:" + orderEvent + ", sequence:" + sequence + ", endOfBatch:" + endOfBatch);
        }

        @Override
        public void onEvent(OrderEvent orderEvent) throws Exception {
            System.out.println("event:" + orderEvent);
        }
    }

    public static void main(String[] args) {
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(
            OrderEvent::new,
            1024 * 1024,
            Executors.defaultThreadFactory(),
            ProducerType.SINGLE,
            new YieldingWaitStrategy()
        );
        disruptor.handleEventsWith(new OrderEventHandler());
        disruptor.start();
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        OrderEventProducer eventProducer = new OrderEventProducer(ringBuffer);
        eventProducer.onData(UUID.randomUUID().toString());
    }
}
