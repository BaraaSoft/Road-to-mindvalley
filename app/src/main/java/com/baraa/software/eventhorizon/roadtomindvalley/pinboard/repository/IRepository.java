package com.baraa.software.eventhorizon.roadtomindvalley.pinboard.repository;

import android.graphics.Bitmap;

import com.baraa.software.eventhorizon.roadtomindvalley.https.model.BaseResponse;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;

public interface IRepository {
    Observable<BaseResponse> getMediaFromNetwork();
    Observable<BaseResponse> getCachedMedia();
    Observable<BaseResponse> getMedia();

    Observable<Bitmap> getImagesFromNetwork();
    Observable<Bitmap> getCachedImages();
    Observable<Bitmap> getImages();

    Observable<Response<ResponseBody>> getFilesFromNetwork();
    Observable<Response<ResponseBody>> getCachedFiles();
    Observable<Response<ResponseBody>> getFiles();
}
