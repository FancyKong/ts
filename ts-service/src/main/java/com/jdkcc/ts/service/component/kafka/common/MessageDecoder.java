/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.kafka.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.*;
import java.util.Map;

/**
 * @author Jiangjiaze
 * @version Id: MessageDecoder.java, v 0.1 2016/8/23 0023 上午 9:03 FancyKong Exp $$
 * 消息(反)序列化类 实现kafka的各种接口么么哒
 */
@Slf4j
public class MessageDecoder implements Serializer, Deserializer{
    private String encoding = "UTF8";

    @Override
    public Object deserialize(String topic, byte[] data) {
        Object obj = null;
        try {
            // bytearray to object
            ByteArrayInputStream bi = new ByteArrayInputStream(data);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void configure(Map configs, boolean isKey) {
        //nothing to do
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(data);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (IOException e) {
            log.error("kafka serialize failed ,e = {}", e);
        }
        return bytes;
    }

    @Override
    public void close() {
        //nothing to do
    }

}
