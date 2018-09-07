package com.baraa.software.eventhorizon.customdownloader.core;

import android.graphics.Bitmap;

import com.baraa.software.eventhorizon.customdownloader.https.model.BaseResponse;

import rx.Observable;

public interface IDownloadService {
    // Get  json data from network
    Observable<BaseResponse> getMediaFromNetwork(String url);
    // Get cached json data in the memory
    Observable<BaseResponse> getCachedMedia();
    // Get cached json data within 20sec from last fetch,
    // if last fetch happen more than 20sec ago fetch new data from a network
    Observable<BaseResponse> getMedia(String url);


    // Get image from network
    Observable<Bitmap> getImagesFromNetwork(String url);
    // Get a cached images in the memory
    Observable<Bitmap> getCachedImages();
    // Get cached media within 20sec from last fetch,
    // if last fetch happen more than 20sec ago fetch new data from a network
    Observable<Bitmap> getImages(String url);


    // Get files(example *.pdf , *.txt) from a network
    Observable<byte[]> getFilesFromNetwork(String url);
    // Get cached files in the memory
    Observable<byte[]> getCachedFiles();
    // Get a cached file within 20sec from last fetch,
    // if last fetch happen more than 20sec ago fetch new data from a network
    Observable<byte[]> getFiles(String url);
}
