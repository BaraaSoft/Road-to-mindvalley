package com.baraa.software.eventhorizon.valleydownloader.core;

import android.graphics.Bitmap;

import com.baraa.software.eventhorizon.valleydownloader.https.model.BaseResponse;

import rx.Observable;

public interface IDownloadService {

    /** Get  json data from network **/
    Observable<BaseResponse> getMediaFromNetwork(String baseJsonUrl);
    /** Get cached json data in the memory **/
    Observable<BaseResponse> getCachedMedia();
    /**
    * Get cached json data within 20sec from last fetch,
    * if last fetch happen more than 20sec ago fetch new data from a network **/
    Observable<BaseResponse> getMedia(String baseJsonUrl);


    /** Get images from network **/
    Observable<Bitmap> getImagesFromNetwork(String baseJsonUrl);
    /** Get a cached images in the memory **/
    Observable<Bitmap> getCachedImages();
    /** Get cached images within 20sec from last fetch,
     * if last fetch happen more than 20sec ago fetch new data from a network **/
    Observable<Bitmap> getImages(String baseJsonUrl);



    /** Get files(example *.pdf , *.txt) from a network.
     * if we say it has same Url path as small image url in base Json response. **/
    Observable<byte[]> getFilesFromNetwork(String baseJsonUrl);
    /** Get cached files in the memory **/
    Observable<byte[]> getCachedFiles();
    /** Get a cached files within 20sec from last fetch,
     * if last fetch happen more than 20sec ago fetch new data from a network **/
    Observable<byte[]> getFiles(String baseJsonUrl);



    /** get a single file from given url **/
    Observable<byte[]> getFileFromNetwork(String fileUrl);
    /** get a cached file in memory **/
    Observable<byte[]> getCachedFile();
    /** Get a cached file within 20sec from last fetch,
     * if last fetch happen more than 20sec ago fetch a new data from a network **/
    Observable<byte[]> getFile(String fileUrl);

    /** Get a single image from network **/
    Observable<Bitmap> getImageFromNetwork(String imageUrl);
    /** Get a single cached image in the memory **/
    Observable<Bitmap> getCachedImage();
    /** Get a single cached image within 20sec from last fetch,
     * if last fetch happen more than 20sec ago fetch new data from a network **/
    Observable<Bitmap> getImage(String imageUrl);
}
