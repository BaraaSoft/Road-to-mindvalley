package com.baraa.software.eventhorizon.roadtomindvalley;

import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.PinboardFragmentMVP;
import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.presenter.PinboardPresenter;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class PinboardPresenterTest {

    PinboardFragmentMVP.View mockView;
    PinboardFragmentMVP.Model mockModel;
    PinboardPresenter presenter;


    Observable viewModels;



    @Before
    public void setup(){
        viewModels = mock(Observable.class);
        mockModel = mock(PinboardFragmentMVP.Model.class);
        mockView = mock(PinboardFragmentMVP.View.class);
        presenter = new PinboardPresenter(mockModel);
        presenter.setView(mockView);

    }


    @Test
    public void loadDataFromModelFristPage(){
        when(mockModel.fetch()).thenReturn(viewModels);
        presenter.loadData(0);
        //verify model being called
        verify(mockModel, times(1)).fetch();

        //verify views method called successfully
        verify(mockView, times(1)).showMainProgressbar();
        verify(mockView, never()).showListLoadginProgressbar();
        verify(mockView, times(1)).hideListLoadginProgressbar();
        verify(mockView, times(1)).hideProgressbar();
    }

    @Test
    public void loadDataFromModelSecondPage(){
        when(mockModel.fetch()).thenReturn(viewModels);
        presenter.loadData(1);
        //verify model being called
        verify(mockModel, times(1)).fetch();

        //verify views method called successfully
        verify(mockView, never()).showMainProgressbar();
        verify(mockView).showListLoadginProgressbar();
        verify(mockView).hideListLoadginProgressbar();
        verify(mockView).hideProgressbar();
    }
    @Test
    public void errorLoading(){

        when(mockModel.fetch()).thenReturn(Observable.error(new Exception("Error")));
        presenter.loadData(1);
        //verify model being called
        verify(mockModel, times(1)).fetch();

        //verify views method called successfully
        verify(mockView, never()).showMainProgressbar();
        verify(mockView).showListLoadginProgressbar();
        verify(mockView).hideListLoadginProgressbar();
        verify(mockView).hideProgressbar();
        verify(mockView,never()).showSnackbarMessage("Error Loading! check connection");

    }


}
