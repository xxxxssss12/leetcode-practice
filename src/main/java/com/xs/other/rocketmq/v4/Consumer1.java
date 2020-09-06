package com.xs.other.rocketmq.v4;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created by xs on 2018/9/12
 */
public class Consumer1 {
    public static void main(String[] args) throws InterruptedException, MQClientException {

        //声明并初始化一个consumer
        //需要一个consumer group名字作为构造方法的参数，这里为xs-local
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xs_local");

        //同样也要设置NameServer地址
        consumer.setNamesrvAddr("10.6.24.99:9876");

        //这里设置的是一个consumer的消费策略
        //CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，即跳过历史消息
        //CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
        //CONSUME_FRO2M_TIMESTAMP 从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        //设置consumer所订阅的Topic和Tag，*代表全部的Tag
//        consumer.subscribe("xs-topic-test", "*");
//        System.out.println("subscribe :xs-topic-test");
//        consumer.subscribe("xs-topic-test-hehehe", "*");
//        System.out.println("subscribe :xs-topic-test-hehehe");
        consumer.subscribe("TopicTest_2", "*");
        //设置一个Listener，主要进行消息的逻辑处理
        consumer.registerMessageListener(
        (MessageListenerConcurrently) (msgs, context) -> {
            System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs);
            consume(msgs);
            //返回消费状态
            //CONSUME_SUCCESS 消费成功
            //RECONSUME_LATER 消费失败，需要稍后重新消费
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        );

        //调用start()方法启动consumer
        consumer.start();

        System.out.println("Consumer Started.");
    }

    private static void consume(List<MessageExt> msgs) {
        if (msgs == null || msgs.isEmpty()) {
            System.out.println("msgs is null");
            return;
        }
        msgs.forEach(msg -> {
            try {
                String body = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                System.out.println("msg :" + body);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }
}
