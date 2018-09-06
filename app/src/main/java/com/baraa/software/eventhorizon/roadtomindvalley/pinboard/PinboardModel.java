package com.baraa.software.eventhorizon.roadtomindvalley.pinboard;

import android.graphics.Bitmap;

import com.baraa.software.eventhorizon.roadtomindvalley.https.model.BaseResponse;
import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.repository.IRepository;

import rx.Observable;
import rx.functions.Func2;

public class PinboardModel implements PinboardFragmentMVP.Model {
    private static final String TAG = "PinboardModel";
    IRepository repository;

    public PinboardModel(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<BaseResponse> getMediaInfo() {
        return repository.getMedia();
    }

    @Override
    public Observable<Bitmap> getImages() {
        return repository.getImages();
    }

    @Override
    public Observable<byte[]> getFiles() {
        return repository.getFiles();
    }

    @Override
    public Observable<PinsViewModel> fetch() {
        return Observable.zip(getMediaInfo(), getImages(), new Func2<BaseResponse, Bitmap, PinsViewModel>() {
            @Override
            public PinsViewModel call(BaseResponse baseResponse, Bitmap bitmap) {
                String category = "General";
                if(baseResponse.getCategories() != null) {
                    category = "Category "+ baseResponse.getCategories().get(0).getTitle();
                }
                PinsViewModel viewModel = new PinsViewModel(bitmap,category);
                viewModel.setId(baseResponse.getId());
                return viewModel;
            }
        });
    }

}
