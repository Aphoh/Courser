package com.aphoh.courser.model;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Will on 9/4/15.
 */
public class Student extends SugarRecord<Student>{
    String name;
    int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
