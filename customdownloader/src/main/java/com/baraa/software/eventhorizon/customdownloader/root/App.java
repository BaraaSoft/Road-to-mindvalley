package com.baraa.software.eventhorizon.customdownloader.root;

import android.app.Application;

import com.baraa.software.eventhorizon.customdownloader.https.module.FileApiModule;
import com.baraa.software.eventhorizon.customdownloader.https.module.MediaApiModule;

public class App extends Application {
    ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .mediaApiModule(new MediaApiModule())
                .fileApiModule(new FileApiModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }
}
