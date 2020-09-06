package com.xs.other.rocketmq.v4;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xs on 2019/1/30
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("groupname");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        //Launch the instance.
        producer.start();
        Message msg = new Message("TopicTest_2" /* Topic */,
                "TagA" /* Tag */,
                ("Hello RocketMQ " +
                        (1)).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );
        SendResult sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);
        msg = new Message("TopicTest_2" /* Topic */,
                "TagA" /* Tag */,
                ("Hello RocketMQ " +
                        (2)).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );
        sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);
//        for (int i=56225;; i++) {
//            //Create a message instance, specifying topic, tag and message body.
//            Message msg = new Message("TopicTest_1" /* Topic */,
//                    "TagA" /* Tag */,
//                    ("Hello RocketMQ " +
//                            (i)).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
//            );
//            //Call send message to deliver message to one of brokers.
//            SendResult sendResult = producer.send(msg);
//            System.out.printf("%s%n", sendResult);
//            Thread.sleep(1000);
//        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
