package com.baraa.software.eventhorizon.roadtomindvalley.root;

import com.baraa.software.eventhorizon.roadtomindvalley.https.module.FileApiModule;
import com.baraa.software.eventhorizon.roadtomindvalley.https.module.MediaApiModule;
import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.view.PinboardFragment;
import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.PinboardModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, MediaApiModule.class, FileApiModule.class, PinboardModule.class})
public interface ApplicationComponent {
    void inject(PinboardFragment pinboardFragment);
}
