package com.baraa.software.eventhorizon.roadtomindvalley.pinboard;

import android.graphics.Bitmap;

import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.model.PinsViewModel;
import com.baraa.software.eventhorizon.valleydownloader.https.model.BaseResponse;

import rx.Observable;


public interface PinboardFragmentMVP {
    interface View {

        void updateData(PinsViewModel viewModel);
        void showSnackbarMessage(String s);
        void showMainProgressbar();
        void hideProgressbar();
        void showListLoadginProgressbar();
        void hideListLoadginProgressbar();
        void hideSwipeToRefreshAnimation();
    }

    interface Presenter {

        void setBaseUrl(String url);

        void loadData(int pageNum);

        void restartLoading();

        void rxUnsubscribe();

        void setView(PinboardFragmentMVP.View view);

    }

    interface Model {
        Observable<Bitmap> getSingleImage(String url);
        Observable<byte[]> getSingleFile(String url);

        Observable<BaseResponse> getMediaInfo(String baseJsonUrl);
        Observable<Bitmap> getImages(String baseJsonUrl);
        Observable<byte[]>getFiles(String baseJsonUrl);
        Observable<PinsViewModel> fetch(String baseJsonUrl);
    }
}
