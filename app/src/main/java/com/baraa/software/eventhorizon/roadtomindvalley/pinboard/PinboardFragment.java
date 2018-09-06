package com.baraa.software.eventhorizon.roadtomindvalley.pinboard;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.baraa.software.eventhorizon.roadtomindvalley.R;
import com.baraa.software.eventhorizon.roadtomindvalley.root.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PinboardFragment extends Fragment implements PinboardFragmentMVP.View {
    private static final String TAG = "PinboardFragment";
    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    PinboardRecyclerViewAdapter adapter;
    List<PinsViewModel> pinsViewModelList = new ArrayList<>();

    @Inject
    PinboardFragmentMVP.Presenter mPresenter;


    public PinboardFragment() {
        // Required empty public constructor
    }

    public static PinboardFragment newInstance() {
        PinboardFragment fragment = new PinboardFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getActivity().getApplication()).getApplicationComponent().inject(this);
        mPresenter.setView(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pinboard, container, false);
        unbinder = ButterKnife.bind(this,view);

        return view;
    }

    public void loadMore(){
        mPresenter.loadData(0);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new PinboardRecyclerViewAdapter(pinsViewModelList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mPresenter.loadData(0);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder != null){
            unbinder.unbind();
            unbinder = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.rxUnsubscribe();
    }

    @Override
    public void updateData(PinsViewModel viewModel) {
        Log.d(TAG, "updateData: "+viewModel.getCategory());
        pinsViewModelList.add(viewModel);
        adapter.notifyItemInserted(pinsViewModelList.size() - 1);
    }

    @Override
    public void showSnackbarMessage(String s) {

    }

    @Override
    public void showProgressbar() {

    }

    @Override
    public void hideProgressbar() {

    }
}
