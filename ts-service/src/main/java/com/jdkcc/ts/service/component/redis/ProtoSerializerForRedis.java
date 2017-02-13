/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.redis;

import com.jdkcc.ts.common.utils.ProtoSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author Jiangjiaze
 * @version Id: ProtoSerializer.java, v 0.1 2016/10/13 16:15 FancyKong Exp $$
 */
public class ProtoSerializerForRedis<T> implements RedisSerializer<T> {

    public ProtoSerializerForRedis(Class<T> clazz){
        this.clazz = clazz;
    }

    @Getter
    @Setter
    private Class<T> clazz;


    @Override
    public byte[] serialize(T t) throws SerializationException {
        if(t == null) return null;
        return ProtoSerializer.obj2Bytes(t);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if(bytes == null) return null;
        return ProtoSerializer.bytes2Obj(bytes,clazz);
    }
}
