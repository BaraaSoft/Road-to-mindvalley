package com.baraa.software.eventhorizon.valleydownloader.root;


import com.baraa.software.eventhorizon.valleydownloader.core.ValleyDownloadService;
import com.baraa.software.eventhorizon.valleydownloader.https.module.FileApiModule;
import com.baraa.software.eventhorizon.valleydownloader.https.module.MediaApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, MediaApiModule.class, FileApiModule.class})
public interface ApplicationComponent {
    void inject(ValleyDownloadService valleyDownloader);
}
