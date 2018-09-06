package com.baraa.software.eventhorizon.roadtomindvalley.pinboard;

import android.graphics.Bitmap;

import com.baraa.software.eventhorizon.roadtomindvalley.https.model.BaseResponse;
import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.model.PinsViewModel;

import rx.Observable;

public interface PinboardFragmentMVP {
    interface View {

        void updateData(PinsViewModel viewModel);
        void showSnackbarMessage(String s);
        void showMainProgressbar();
        void hideProgressbar();
        void showListLoadginProgressbar();
        void hideListLoadginProgressbar();
    }

    interface Presenter {

        void loadData(int pageNum);

        void restartLoading();

        void rxUnsubscribe();

        void setView(PinboardFragmentMVP.View view);

    }

    interface Model {
        Observable<BaseResponse> getMediaInfo();
        Observable<Bitmap> getImages();
        Observable<byte[]>getFiles();
        Observable<PinsViewModel> fetch();
    }
}
