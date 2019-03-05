package com.xs.rocketmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

/**
 * Created by xs on 2018/9/12
 */
public class Producer {

    private static DefaultMQProducer producer = null;
    private static DefaultMQProducer producer1 = null;

    private static void initProducer() throws MQClientException {
        producer = new DefaultMQProducer("producer1");
        producer.setNamesrvAddr("");
        producer.start();
        producer.shutdown();
        producer = new DefaultMQProducer("producer1");
        producer.setNamesrvAddr("");
        producer.start();
    }
    public static void main(String[] args) throws MQClientException, InterruptedException {
        initProducer();
        JSONObject obj = new JSONObject();
        obj.put("bankcardId",325582);
        obj.put("bussType","SUKOU");
        obj.put("consumeId","1053566195");
        obj.put("investorId", "208");
        obj.put("repaymentId", "1022311793");
        //发送10条消息到Topic为TopicTest，tag为TagA，消息内容为“Hello RocketMQ”拼接上i的值
//        for (int i = 0; i < 1; i++) {
            try {
                Message msg = new Message("xs-topic-test",// topic
                        "a",// tag
                        obj.toJSONString().getBytes()
                );
                //调用producer的send()方法发送消息
                //这里调用的是同步的方式，所以会有返回结果
                SendResult sendResult = producer.send(msg);
                //打印返回结果，可以看到消息发送的状态以及一些相关信息
                System.out.println("msg:" + obj.toJSONString());
                System.out.println("sendResult:" + JSON.toJSONString(sendResult));
                producer.shutdown();
                initProducer();
                //这里调用的是同步的方式，所以会有返回结果
                sendResult = producer.send(msg);
                //打印返回结果，可以看到消息发送的状态以及一些相关信息
                System.out.println("msg:" + obj.toJSONString());
                System.out.println("sendResult:" + JSON.toJSONString(sendResult));
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
//        }

        //发送完消息之后，调用shutdown()方法关闭producer
        producer.shutdown();
    }
}
