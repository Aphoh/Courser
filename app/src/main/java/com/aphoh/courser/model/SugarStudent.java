package com.aphoh.courser.model;

import com.aphoh.courser.db.DataInteractor;
import com.orm.SugarRecord;

/**
 * Created by Will on 9/4/15.
 */
public class SugarStudent extends SugarRecord<SugarStudent> implements DataInteractor.Student {
    String name;
    int age;

    public SugarStudent() {
    }

    public SugarStudent(String name, int age) {
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
