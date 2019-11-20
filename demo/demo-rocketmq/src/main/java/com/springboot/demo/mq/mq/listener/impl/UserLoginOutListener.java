package com.springboot.demo.mq.mq.listener.impl;

import com.springboot.demo.mq.mq.listener.AbstractMqListener;
import com.springboot.demo.mq.mq.listener.UnifiedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserLoginOutListener extends AbstractMqListener {

    @Override
    public boolean readMessage(UnifiedMessage unifiedMessage) {
        log.info(unifiedMessage.getMsgBody());
        return true;
    }

}
