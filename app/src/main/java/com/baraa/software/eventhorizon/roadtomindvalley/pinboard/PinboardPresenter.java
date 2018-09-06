package com.baraa.software.eventhorizon.roadtomindvalley.pinboard;

import android.util.Log;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PinboardPresenter implements PinboardFragmentMVP.Presenter {
    private static final String TAG = "PinboardPresenter";
    PinboardFragmentMVP.Model model;
    PinboardFragmentMVP.View view;
    Subscription subscription;

    public PinboardPresenter(PinboardFragmentMVP.Model model) {
        this.model = model;
    }

    @Override
    public void loadData(int pageNum) {
        int from = pageNum * 10;
        view.showProgressbar();
        subscription = model.fetch().skip(from).take(10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PinsViewModel>() {
                    @Override
                    public void onCompleted() {
                        view.hideProgressbar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showSnackbarMessage("Error Loading! check connection");
                        Log.e(TAG, "onError: ",e );
                    }

                    @Override
                    public void onNext(PinsViewModel pinsViewModel) {
                        view.hideProgressbar();
                        view.updateData(pinsViewModel);
                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        if (subscription != null){
            subscription.unsubscribe();
            subscription = null;
        }
    }

    @Override
    public void setView(PinboardFragmentMVP.View view) {
        this.view = view;
    }
}