package com.springboot.demo.mq.mq;

import com.springboot.demo.mq.busi.type.EnumBusinessType;
import com.springboot.demo.mq.mq.constants.Constants;
import com.springboot.demo.mq.mq.listener.AbstractMqListener;
import com.springboot.demo.mq.mq.listener.impl.UserLoginListener;
import com.springboot.demo.mq.mq.listener.impl.UserLoginOutListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;


/**
 * 消息消费
 **/
@Service
@Slf4j
public class MqConsumer {

    private static List<MQPushConsumer> consumers = new ArrayList<>();

    private String rocketMQNameServer = Constants.MQ_SERVER;

    private String consumerGroupNamePrefix = Constants.MQ_CONSUMER_PREFIX;

    private String topic = Constants.MQ_TOPIC;

    /**
     * 需要确保依赖的所有的子类Listener在本类初始化之前初始化完成
     */

    @Autowired
    private UserLoginListener userLoginListener;

    @Autowired
    private UserLoginOutListener userLoginOutListener;

    @PostConstruct
    public void init() {
        try {
            log.info("Start mq consumers begin, Mq.name.server {}.", rocketMQNameServer);

            // 启动多个consumer(对应多个tag)
            for (EnumBusinessType msgType : EnumBusinessType.values()) {
                startMqConsumer(msgType);
            }

            log.info("Start mq consumers end");
        } catch (Exception e) {
            log.error("Mq consumer loading error!", e);
            throw new RuntimeException("Rocket mq consumer start failed");
        }
    }

    private void startMqConsumer(EnumBusinessType msgTag) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setConsumerGroup(consumerGroupNamePrefix + msgTag);
        consumer.setNamesrvAddr(rocketMQNameServer);

        // Subscribe topic with tag
        consumer.subscribe(topic, String.valueOf(msgTag));
        consumer.registerMessageListener(getMqListenerInstance(msgTag));

        //Launch the consumer instance.
        consumer.start();

        // add to list
        consumers.add(consumer);

        log.info("Start mq consumer success, subscribe topic: {}, tag: {}", topic, msgTag);
    }

    private AbstractMqListener getMqListenerInstance(EnumBusinessType type) {
        if (EnumBusinessType.LOGIN == type) {
            return userLoginListener;
        } else {
            return userLoginOutListener;
        }
    }

    @PreDestroy
    public void stop() {
        for (MQPushConsumer consumer : consumers) {
            consumer.shutdown();
        }
        log.info("Shutdown mq consumers!");
    }

}

