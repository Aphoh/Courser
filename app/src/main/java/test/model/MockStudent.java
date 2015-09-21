package test.model;

import com.aphoh.courser.db.DataInteractor;

/**
 * Created by Will on 9/13/15.
 */
public class MockStudent implements DataInteractor.Student {
    private static int idCounter = 1;
    long id;
    String name;
    int age;

    public MockStudent(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    public static MockStudent newInstance(String name, int age) {
        MockStudent student = new MockStudent(idCounter, name, age);
        idCounter++;
        return student;
    }
}
