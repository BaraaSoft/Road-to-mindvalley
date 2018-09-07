package com.baraa.software.eventhorizon.customdownloader.root;


import com.baraa.software.eventhorizon.customdownloader.core.ValleyDownloadService;
import com.baraa.software.eventhorizon.customdownloader.https.module.FileApiModule;
import com.baraa.software.eventhorizon.customdownloader.https.module.MediaApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, MediaApiModule.class, FileApiModule.class})
public interface ApplicationComponent {
    void inject(ValleyDownloadService valleyDownloader);
}
