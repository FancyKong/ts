/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.kafka.common;

import com.jdkcc.ts.common.utils.ProtoSerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * @author Jiangjiaze
 * @version Id: ProtoSerializerForKafka.java, v 0.1 2016/10/14 13:40 FancyKong Exp $$
 */
public class ProtoSerializerForKafka<T> implements Serializer<T>,Deserializer<T>{

    private Class<T> clazz;

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null)
            return null;
        else
            return ProtoSerializer.bytes2Obj(data,clazz);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Object classType = configs.get("object.class");
        if (classType == null)
            classType = configs.get("deserializer.encoding");
        if (classType != null && classType instanceof Class)
            clazz = (Class<T>) classType;
    }

    @Override
    public byte[] serialize(String topic, T data) {
        if(data == null)
            return null;
        else return ProtoSerializer.obj2Bytes(data);
    }

    @Override
    public void close() {
        //nothing to do ;
    }
}
