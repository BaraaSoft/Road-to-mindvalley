package com.baraa.software.eventhorizon.roadtomindvalley.pinboard.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.baraa.software.eventhorizon.roadtomindvalley.R;
import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.PinboardFragmentMVP;
import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.model.PinsViewModel;
import com.baraa.software.eventhorizon.roadtomindvalley.root.App;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PinboardFragment extends Fragment implements PinboardFragmentMVP.View,View.OnClickListener {
    private static final String TAG = "PinboardFragment";

    Unbinder unbinder;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progressbarMain) ProgressBar progressbarMain;
    @BindView(R.id.progressbar) ProgressBar progressbarList;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefreshLayout;


    View view;
    GridLayoutManager layoutManager;
    PinboardRecyclerViewAdapter adapter;
    List<PinsViewModel> pinsViewModelList = new ArrayList<>();
    // Endless scroll
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

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
        view = inflater.inflate(R.layout.fragment_pinboard, container, false);
        setHasOptionsMenu(true);
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
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mPresenter.loadData(0);

        progressbarList.setVisibility(View.GONE);
        progressbarMain.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable(getResources().getIntArray(R.array.progress_bar_colors)));

        fab.setOnClickListener(this);


        // load new item on scrolling
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.loadData(page);
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);

        // pull to refresh
        swipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.progress_bar_colors));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                endlessRecyclerViewScrollListener.resetState();
                pinsViewModelList.clear();
                adapter.notifyDataSetChanged();
                mPresenter.restartLoading();
            }
        });


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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnuViewGrid) {
            layoutManager.setSpanCount(2);
            return true;
        }else if(id == R.id.mnuViewList){
            layoutManager.setSpanCount(1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void updateData(PinsViewModel viewModel) {
        Log.d(TAG, "updateData: "+viewModel.getCategory());
        pinsViewModelList.add(viewModel);
        adapter.notifyItemInserted(pinsViewModelList.size() - 1);
    }

    @Override
    public void showSnackbarMessage(String s) {
        Snackbar.make(view , s, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showMainProgressbar() {
        progressbarMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        progressbarMain.setVisibility(View.GONE);
    }

    @Override
    public void showListLoadginProgressbar() {
        progressbarList.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideListLoadginProgressbar() {
        progressbarList.setVisibility(View.GONE);
    }

    @Override
    public void hideSwipeToRefreshAnimation() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.fab){
            endlessRecyclerViewScrollListener.resetState();
            pinsViewModelList.clear();
            adapter.notifyDataSetChanged();
            mPresenter.restartLoading();
        }
    }
}
