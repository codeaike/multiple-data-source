package com.springboot.demo.mq.mq.listener;

public interface IReadMessage {
    /**
     * 具体业务逻辑重写这个方法就可以啦
     * @param unifiedMessage 读取到的消息统一模型
     * @return 读取数据是否成功
     */
    boolean readMessage(UnifiedMessage unifiedMessage);
}
