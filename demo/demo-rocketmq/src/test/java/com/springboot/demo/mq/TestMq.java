package com.springboot.demo.mq;

import com.springboot.demo.mq.busi.type.EnumBusinessType;
import com.springboot.demo.mq.mq.MqProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class TestMq {
    @Resource
    private MqProducer mqProducer;

    @Test
    public void produceMsg() throws InterruptedException {
        mqProducer.sendMessage(EnumBusinessType.LOGIN, "", "test", 0);
        // 休眠5秒钟，等待消费者消费数据
        TimeUnit.SECONDS.sleep(5);
    }

}
