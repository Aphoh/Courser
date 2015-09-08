package com.aphoh.courser;

import com.aphoh.courser.base.AppComponent;
import com.aphoh.courser.base.DaggerAppComponent;
import com.aphoh.courser.base.DataModule;
import com.facebook.stetho.Stetho;
import com.orm.SugarApp;

/**
 * Created by Will on 9/4/15.
 */
public class App extends SugarApp {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .dataModule(new DataModule())
                .build();
        if (BuildConfig.DEBUG) Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );

    }

    public static AppComponent getAppComponent() {
        return component;
    }
}
