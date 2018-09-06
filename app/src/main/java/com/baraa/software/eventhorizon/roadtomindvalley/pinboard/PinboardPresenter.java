package com.baraa.software.eventhorizon.roadtomindvalley.pinboard;

import android.util.Log;

import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.repository.PinsViewModel;

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
    public void loadData() {
        subscription = model.fetch().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PinsViewModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ",e );
                    }

                    @Override
                    public void onNext(PinsViewModel pinsViewModel) {

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
