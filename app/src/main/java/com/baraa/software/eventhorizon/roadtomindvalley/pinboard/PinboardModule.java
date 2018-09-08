package com.baraa.software.eventhorizon.roadtomindvalley.pinboard;



import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.model.PinboardModel;
import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.presenter.PinboardPresenter;
import com.baraa.software.eventhorizon.valleydownloader.core.IDownloadService;
import com.baraa.software.eventhorizon.valleydownloader.core.ValleyDownloadService;
import com.baraa.software.eventhorizon.valleydownloader.https.IFileApiServices;
import com.baraa.software.eventhorizon.valleydownloader.https.IMediaJsonApiServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PinboardModule {
    @Provides
    public PinboardFragmentMVP.Presenter providesPinboardMVPPresenter(PinboardFragmentMVP.Model model){
        return new PinboardPresenter(model);
    }


    @Provides
    public PinboardFragmentMVP.Model providesPinboardMVPModel(IDownloadService valleyDownloadService){
        return new PinboardModel(valleyDownloadService);
    }


    @Provides
    @Singleton
    public IDownloadService providesValleyDownloadServices(IMediaJsonApiServices mediaJsonApiServices, IFileApiServices fileApiServices){
        return new ValleyDownloadService(fileApiServices,mediaJsonApiServices);
    }

}
