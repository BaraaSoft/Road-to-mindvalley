package com.baraa.software.eventhorizon.customdownloader.root;

import android.app.Application;

import com.baraa.software.eventhorizon.roadtomindvalley.https.module.FileApiModule;
import com.baraa.software.eventhorizon.roadtomindvalley.https.module.MediaApiModule;
import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.PinboardModule;

public class App extends Application {
    ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .mediaApiModule(new MediaApiModule())
                .fileApiModule(new FileApiModule())
                .pinboardModule(new PinboardModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }
}
