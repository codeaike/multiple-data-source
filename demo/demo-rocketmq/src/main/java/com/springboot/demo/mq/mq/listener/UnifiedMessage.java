package com.springboot.demo.mq.mq.listener;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnifiedMessage {
    private String msgId;

    private String msgTags;

    private String msgBody;
}
