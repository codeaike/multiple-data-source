package com.springboot.demo.mq.mq;

import com.springboot.demo.mq.busi.type.EnumBusinessType;
import com.springboot.demo.mq.mq.constants.Constants;
import com.springboot.demo.mq.mq.utils.MqUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * 异步消息发送
 * 此项目中暂不使用生产者，故使用@Lazy服务启动不初始化该实例
 **/
@Service
@Slf4j
public class MqProducer {

    private static final String PRODUCER_INSTANCE_NAME = Constants.MQ_PRODUCER_INSTANCE;

    private static final String MSG_KEY_CONNECT = "-";

    private static DefaultMQProducer producer;

    private String rocketMQNameServer = Constants.MQ_SERVER;

    private String producerGroupName = Constants.MQ_PRODUCER_GROUP;

    private String topic = Constants.MQ_TOPIC;

    @PostConstruct
    public void init() {
        try {
            log.info("Start mq producer begin");
            producer = new DefaultMQProducer();
            producer.setProducerGroup(producerGroupName); // 设置MQ生产者组名
            log.info("Mq.name.server {}", rocketMQNameServer);
            producer.setNamesrvAddr(rocketMQNameServer);
            producer.setInstanceName(PRODUCER_INSTANCE_NAME);
            producer.start();
            log.info("Start mq producer end");
        } catch (Exception e) {
            log.error("Mq loading error!", e);
            throw new RuntimeException("Rocket mq producer start failed");
        }
    }

    @PreDestroy
    public void stop() {
        log.info("Shutdown mq producer!");
        producer.shutdown();
    }

    /**
     * 发送不保证顺序性的消息
     *
     * @param key 消息的key（用来判重）
     * @param msg 消息的内容
     * @param delayTimeLevel 时间延迟级别
     * @return true:处理成功，不需要重发 false:处理失败, 暂不重发
     */
    public boolean sendMessage(EnumBusinessType msgTag, String key, String msg, int delayTimeLevel) {
        log.info("Send mq[topic={} msgTag={} key={} msg={} delayTimeLevel={}]", topic, msgTag, key, msg, delayTimeLevel);
        byte[] msgBytes = MqUtils.convertStrToByteArr(msg);
        boolean result = sendMessage(topic, msgTag, key, msgBytes, delayTimeLevel);
        if (!result) {
            log.error("[WARN]send mq-msg fail!, topic:{}, tag:{}, key:{}, serialize msg:{}", topic, msgTag, key, msg);
        }
        return result;
    }

    /**
     * 发送不保证顺序性的消息
     *
     * @param topic 消息的topic
     * @param msgTag 消息的tag
     * @param key 消息的key（用来判重）
     * @param msgBytes 消息的内容
     * @return true:处理成功 false:处理失败，暂不考虑重发
     */
    private boolean sendMessage(String topic, EnumBusinessType msgTag, String key, byte[] msgBytes, int delayTimeLevel) {
        String msgKey = topic + MSG_KEY_CONNECT + key;
        try {
            Message rocketMsg = new Message(topic, String.valueOf(msgTag), msgKey, msgBytes);
            rocketMsg.setDelayTimeLevel(delayTimeLevel);
            SendResult sendResult = producer.send(rocketMsg);
            if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
                log.info("Send msg status success! topic:{}, tag:{}, key:{}", topic, msgTag, msgKey);
            } else {
                log.warn("Send msg status fail! status:{}, topic:{}, tag:{}, key:{}, msgID:{}", sendResult.getSendStatus(),
                        topic, msgTag, key, sendResult.getMsgId());
                return false;
            }
        } catch (Exception e) {
            log.error("Send msg occurs exception! topic:{}, tag:{}, key:{}", topic, msgTag, key, e);
            return false;
        }
        return true;
    }

}

