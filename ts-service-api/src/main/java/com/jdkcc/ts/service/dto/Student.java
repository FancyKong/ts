package com.jdkcc.ts.service.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/1 0001.
 * only for the kafka test
 */
@Data
public class Student implements Serializable{


    private static final long serialVersionUID = 4223397103175060844L;
    private int id;

    private String name;

    public static void main(String[] args) {
        Student student = new Student();
        Student student1 = student.get();
        String string = student.get();
    }

    public <K,V> V get(){
        V v = (V) new Student();
        return v;
    }

}
