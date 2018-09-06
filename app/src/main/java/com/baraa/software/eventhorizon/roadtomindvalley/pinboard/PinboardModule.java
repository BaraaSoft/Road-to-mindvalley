package com.baraa.software.eventhorizon.roadtomindvalley.pinboard;

import com.baraa.software.eventhorizon.roadtomindvalley.https.IFileApiServices;
import com.baraa.software.eventhorizon.roadtomindvalley.https.IMediaApiServices;
import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.repository.IRepository;
import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.repository.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PinboardModule  {
    @Provides
    public PinboardFragmentMVP.Presenter providesPinboardMVPPresenter(PinboardFragmentMVP.Model model){
        return new PinboardPresenter(model);
    }


    @Provides
    public PinboardFragmentMVP.Model providesPinboardMVPModel(IRepository repository){
        return new PinboardModel(repository);
    }

    @Provides
    @Singleton
    public IRepository providesPinboardMVPRepository(IMediaApiServices mediaApiServices, IFileApiServices fileApiServices){
        return new Repository(mediaApiServices,fileApiServices);
    }
}
