package com.aphoh.courser.base;

/**
 * Created by Will on 9/5/15.
 */
public class Injector {
    private static AppComponent appComponent;

    public static AppComponent get(){
        return appComponent;
    }

    public static void set(AppComponent component){
        appComponent = component;
    }
}
