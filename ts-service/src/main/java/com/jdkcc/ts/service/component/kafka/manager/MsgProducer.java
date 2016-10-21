/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.kafka.manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

/**
 * @author Jiangjiaze
 * @version Id: MessageProducer.java, v 0.1 2016/8/17 0017 下午 3:17 FancyKong Exp $$
 * 消息发出者,我这里的设计是整个当前webapp只有一个producer,个人觉得足够了.
 * 以后还需要增加一些批量发送的方法,还有异步发送的方法.
 *
 */
@Slf4j
@Component
public class MsgProducer {

    @Value("${kafka.brokers}")
    private String brokerList;

    @Value("${kafka.serializer}")
    private String serializer;

    private static Map<Class<?>,KafkaProducer> producerMap;
    Properties props;

    public MsgProducer() {
        props = new Properties();
    }

    /**
     * 放在构造函数@Value有可能取不到值,顺便提下@Value配置了property-placeholder就可以了
     * 网上那些博客都是过时的.
     */
    @PostConstruct
    public void initProps(){
        props.put("bootstrap.servers", brokerList);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", serializer);
        props.put("object.class","java.lang.String");

        producerMap = new HashMap<>();
    }

    public <K, V> void send(String topicName, K key, V value , Class<V> clazz) {
        log.info("sending message topicName={},key={},value={}",topicName,key,value);
        getProducer(clazz).send(new ProducerRecord<>(topicName, key, value));
    }

    private KafkaProducer getProducer(Class<?> clazz){
        KafkaProducer kafkaProducer = producerMap.get(clazz);
        if(kafkaProducer == null){
            props.put("object.class",clazz);
            kafkaProducer = new KafkaProducer(props);
            producerMap.put(clazz,kafkaProducer);
            return kafkaProducer;
        }else{
            return kafkaProducer;
        }
    }

    @PreDestroy
    public void closeProducer() {
        producerMap.forEach((aClass, kafkaProducer) -> kafkaProducer.close());
    }
}
