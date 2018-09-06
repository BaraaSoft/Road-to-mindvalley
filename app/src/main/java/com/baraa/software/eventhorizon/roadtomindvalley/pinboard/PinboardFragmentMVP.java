package com.baraa.software.eventhorizon.roadtomindvalley.pinboard;

import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.repository.PinsViewModel;

import rx.Observable;

public interface PinboardFragmentMVP {
    interface View {

        void updateData(PinsViewModel viewModel);
        void showSnackbarMessage(String s);
        void showProgressbar();
        void hideProgressbar();

    }

    interface Presenter {

        void loadData();

        void rxUnsubscribe();

        void setView(PinboardFragmentMVP.View view);

    }

    interface Model {

        Observable<PinsViewModel> fetch();

    }
}
