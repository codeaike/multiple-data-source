package com.springboot.demo.mq.mq.listener;

import com.springboot.demo.mq.mq.constants.Constants;
import com.springboot.demo.mq.mq.utils.MqUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;


/**
 * 异步消息的消费
 **/
@Slf4j
public abstract class AbstractMqListener implements MessageListenerConcurrently, IReadMessage {

    private String topic = Constants.MQ_TOPIC;

    @Override
    public final ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgExtList,
                                                    ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (msgExtList == null || msgExtList.size() == 0) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        log.info("Receive msg ext list, size is:{}", msgExtList.size());
        boolean result = true;
        for (MessageExt msg : msgExtList) {
            try {
                if (!msg.getTopic().equals(topic)){
                    continue;
                }
                UnifiedMessage unifiedMessage = new UnifiedMessage(msg.getMsgId(), msg.getTags(), MqUtils.convertByteArrToStr(msg.getBody()));

                // 调用具体业务类处理消息，返回处理成功或失败
                result = readMessage(unifiedMessage);

                if (!result) {
                    log.warn("Read result fail, unified message: {}", unifiedMessage);
                } else {
                    log.info("Read result success, unified message: {}", unifiedMessage);
                }
            } catch (Exception ex) {
                result = false;
                log.error("Occurs exception", ex);
            }
        }
        return result ? ConsumeConcurrentlyStatus.CONSUME_SUCCESS : ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }

}

