/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.kafka.manager;

import com.jdkcc.ts.dal.entities.JUser;
import com.jdkcc.ts.service.component.kafka.common.BussinessProcess;
import com.jdkcc.ts.service.component.kafka.common.GroupName;
import com.jdkcc.ts.service.component.kafka.common.Topic;
import com.jdkcc.ts.service.component.kafka.process.UserCreateProcess;
import com.jdkcc.ts.service.component.kafka.process.UserProcess;
import com.jdkcc.ts.service.dto.request.UserCreateReqDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author Jiangjiaze
 * @version Id: MessageConsumer.java, v 0.1 2016/8/17 0017 下午 3:17 FancyKong Exp $$
 */
@Slf4j
@Component
public class MsgConsumer {

    @Autowired
    ApplicationContext applicationContext;

    @Value("${kafka.sessionTimeout}")
    private String sessionTimeout;

    @Value("${kafka.brokers}")
    private String brokerList;

    @Value("${kafka.serializer}")
    private String deserializer;

    private MsgConsumer() {

    }

    private <K, V> void consume(String topicName, String groupName, Class<? extends BussinessProcess> clazz, Class dtoClazz) {
        Properties props = new Properties();
        props.put("bootstrap.servers", brokerList);
        props.put("group.id", groupName);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", deserializer);
        props.put("object.class", dtoClazz);
        KafkaConsumer<K, V> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topicName));
        while (true) {
            ConsumerRecords<K, V> records = consumer.poll(100);
            for (ConsumerRecord<K, V> record : records) {
                log.info("key={},value={}", record.key(), record.value());
                applicationContext.getBean(clazz).doBussiness(record.value());
            }
        }
    }

    /**
     * 创建三个consumer
     * 其实并没有太大必要，因为对于kafka来说，及时性就不是太重要了
     * 所以单线程还是可以的.但是我为了装逼还是搞了三个
     */
    /*@PostConstruct
    public void consumeDefault() {
        for (int i = 0; i < 3; i++)
            new Thread(() -> consume(Topic.DEFAULT.getCode(), GroupName.DEFAULT.getCode(), DeaultProcess.class)).start();
    }*/
    @PostConstruct
    public void consumerUser() {
        new Thread(() -> consume(Topic.USER.getCode(), GroupName.USER.getCode(), UserProcess.class, JUser.class)).start();
    }

    @PostConstruct
    public void consumerUserCreate() {
        new Thread(() -> consume(Topic.USER_CREATE.getCode(), GroupName.USER_CREATE.getCode(), UserCreateProcess.class, UserCreateReqDto.class)).start();
    }

    /**
            * 创建一个consumer
    */
    /*@PostConstruct
    public void consumeStudent() {
       for (int i = 0; i < 3; i++)
            new Thread(() -> consume(Topic.STUDENT.getCode(), GroupName.STUDENT.getCode(), StudentProcess.class)).start();
    }*/
}
