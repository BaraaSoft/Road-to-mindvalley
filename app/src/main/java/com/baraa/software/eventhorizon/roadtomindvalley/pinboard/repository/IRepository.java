package com.baraa.software.eventhorizon.roadtomindvalley.pinboard.repository;

import android.graphics.Bitmap;

import com.baraa.software.eventhorizon.roadtomindvalley.https.model.BaseResponse;

import rx.Observable;

public interface IRepository {
    Observable<BaseResponse> getMediaFromNetwork();
    Observable<BaseResponse> getCachedMedia();
    Observable<BaseResponse> getMedia();

    Observable<Bitmap> getImagesFromNetwork();
    Observable<Bitmap> getCachedImages();
    Observable<Bitmap> getImages();

    Observable<byte[]> getFilesFromNetwork();
    Observable<byte[]> getCachedFiles();
    Observable<byte[]> getFiles();
}
