package com.haobin.jvmstudy.concurrent;

import com.lmax.disruptor.RingBuffer;

/**
 * @Author HaoBin
 * @Create 2020/3/27 11:52
 * @Description: 无锁队列
 **/
public class Disruptor {


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
}
