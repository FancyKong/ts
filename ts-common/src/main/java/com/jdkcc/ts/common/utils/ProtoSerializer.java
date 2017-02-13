/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.common.utils;


import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * @author Jiangjiaze
 * @version Id: ProtoSerializer.java, v 0.1 2016/9/20 0020 上午 11:18 FancyKong Exp $$
 */
public class ProtoSerializer {
    public static <T> byte[] obj2Bytes(T obj) {
        Schema<T> schema = RuntimeSchema
                .getSchema((Class<T>) obj.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(4096);
        byte[] protostuff = null;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            buffer.clear();
        }
        return protostuff;
    }

    public static <T> T bytes2Obj(byte[] bytes, Class<T> clazz) {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        T product = null;
        try {
            product = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        ProtostuffIOUtil.mergeFrom(bytes, product, schema);
        return product;
    }

    public static void main(String[] args) throws InterruptedException {
        /*Student student = new Student();
        student.setId(1);
        student.setName("123");
        byte[] bytes = ProtoSerializer.obj2Bytes(student,Student.class);
        Student student1 = ProtoSerializer.bytes2Obj(bytes,Student.class);
        System.out.println(student1);
        System.out.println(student1 == student);
        String studentString = JSON.toJSONString(student);
        System.out.println(studentString);*/
        //long start = System.nanoTime();
        final StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0 ; i <15000;i++){
            new Thread(() -> stringBuffer.append("dfad")).start();
        }

        final StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0 ; i <15000;i++){
            new Thread(() -> stringBuilder.append("hell")).start();
        }
        Thread.sleep(10000);
        System.out.println(stringBuffer.length());
        System.out.println(stringBuilder.length());
    }
}
