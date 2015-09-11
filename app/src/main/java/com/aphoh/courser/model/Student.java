package com.aphoh.courser.model;

import com.orm.SugarRecord;

/**
 * Created by Will on 9/4/15.
 */
public class Student extends SugarRecord<Student> {
    String name;
    int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
