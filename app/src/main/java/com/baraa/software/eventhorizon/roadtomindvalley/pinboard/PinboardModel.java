package com.baraa.software.eventhorizon.roadtomindvalley.pinboard;

import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.repository.IRepository;
import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.repository.PinsViewModel;

import rx.Observable;

public class PinboardModel implements PinboardFragmentMVP.Model {
    IRepository repository;

    public PinboardModel(IRepository repository) {
        this.repository = repository;
    }

//    @Override
//    public Observable<PinsViewModel> fetch() {
//        return repository.getMediaFromNetwork().flatMap((baseResponse)->{
//            PinsViewModel viewModel = new PinsViewModel(null,baseResponse.getCategories().get(0).getTitle());
//            return Observable.just(viewModel);
//        });
//    }

    @Override
    public Observable<PinsViewModel> fetch() {
        return repository.getImagesFromNetwork().flatMap((bitmap)->{
            PinsViewModel viewModel = new PinsViewModel(bitmap,"IMAGE");
            return Observable.just(viewModel);
        });
    }
}
