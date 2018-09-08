package com.baraa.software.eventhorizon.roadtomindvalley.pinboard.model;

import android.graphics.Bitmap;

import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.PinboardFragmentMVP;
import com.baraa.software.eventhorizon.valleydownloader.core.IDownloadService;
import com.baraa.software.eventhorizon.valleydownloader.https.model.BaseResponse;

import rx.Observable;
import rx.functions.Func2;


public class PinboardModel implements PinboardFragmentMVP.Model {
    private static final String TAG = "PinboardModel";
    IDownloadService valleyDownloadService;

    public PinboardModel(IDownloadService valleyDownloadService) {
        this.valleyDownloadService = valleyDownloadService;
    }

    @Override
    public Observable<Bitmap> getSingleImage(String url) {
        return valleyDownloadService.getImage(url);
    }

    @Override
    public Observable<byte[]> getSingleFile(String url) {
        return valleyDownloadService.getFile(url);
    }

    @Override
    public Observable<BaseResponse> getMediaInfo(String baseJsonUrl) {
        return valleyDownloadService.getMedia(baseJsonUrl);
    }

    @Override
    public Observable<Bitmap> getImages(String baseJsonUrl) {
        return valleyDownloadService.getImages(baseJsonUrl);
    }

    @Override
    public Observable<byte[]> getFiles(String baseJsonUrl) {
        return getFiles(baseJsonUrl);
    }

    @Override
    public Observable<PinsViewModel> fetch(String baseJsonUrl) {
        return Observable.zip(getMediaInfo(baseJsonUrl), getImages(baseJsonUrl), new Func2<BaseResponse, Bitmap, PinsViewModel>() {
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
