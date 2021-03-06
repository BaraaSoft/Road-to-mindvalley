package com.baraa.software.eventhorizon.valleydownloader.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.baraa.software.eventhorizon.valleydownloader.https.IFileApiServices;
import com.baraa.software.eventhorizon.valleydownloader.https.IMediaJsonApiServices;
import com.baraa.software.eventhorizon.valleydownloader.https.model.BaseResponse;
import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class ValleyDownloadService implements IDownloadService {
    IFileApiServices fileApiServices;
    IMediaJsonApiServices mediaApiServices;

    // how long cached data remain before fetching new one
    public static final long UPDATE_PERIOD = 20 * 1000;

    // List used for caching
    List<BaseResponse> mediaList= new ArrayList<>();
    List<Bitmap> imagesList = new ArrayList<>();
    List<byte[]> fileList = new ArrayList<>();

    byte[] bytes = new byte[20000];
    Bitmap image;




    long timeStamp;


    public ValleyDownloadService(IFileApiServices fileApiService, IMediaJsonApiServices mediaJsonApiServices) {
        this.fileApiServices = fileApiService;
        this.mediaApiServices = mediaJsonApiServices;
        this.timeStamp = System.currentTimeMillis();
    }


    private boolean isUpTodate(){
        return System.currentTimeMillis() - timeStamp < UPDATE_PERIOD;
    }


    /** Get images or files from the given Url. Json parsing based on that endpoint response **/

    @Override
    public Observable<BaseResponse> getMediaFromNetwork(String baseJsonUrl) {
        return mediaApiServices.getMediaInfo(baseJsonUrl).flatMap((list)->{
            return Observable.from(list);
        }).doOnNext(baseResponse -> {
            mediaList.add(baseResponse);
        });
    }

    @Override
    public Observable<BaseResponse> getCachedMedia() {
        if(isUpTodate()){
            return Observable.from(mediaList);
        }else {
            timeStamp = System.currentTimeMillis();
            mediaList.clear();
        }
        return Observable.empty();
    }

    // get base media file (details from json response)  from memory switch to get it from network after
    // certain period of time > UPDATE_PERIOD

    @Override
    public Observable<BaseResponse> getMedia(String baseJsonUrl) {
        return getCachedMedia().switchIfEmpty(getMediaFromNetwork(baseJsonUrl));
    }

    @Override
    public Observable<Bitmap> getImagesFromNetwork(String baseJsonUrl) {
        return getMedia(baseJsonUrl).concatMap(baseResponse -> {
            return Observable.just(baseResponse.getUrls().getSmall());
        }).concatMap(url -> {
            return fileApiServices.getfile(url);
        }).flatMap( responseBodyResponse -> {
            if(responseBodyResponse.body() == null)
                return Observable.empty();
            int width, height;
            Bitmap bMap = BitmapFactory.decodeStream(responseBodyResponse.body().byteStream());
            width = bMap.getWidth();
            height = bMap.getHeight();
            Bitmap bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false);
            return Observable.just(bMap2);
        }).doOnNext(bitmap -> {
            imagesList.add(bitmap);
        });
    }




    @Override
    public Observable<Bitmap> getCachedImages() {
        if(isUpTodate()){
            return Observable.from(imagesList);
        }else {
            timeStamp = System.currentTimeMillis();
            imagesList.clear();
            return Observable.empty();
        }
    }

    // get Images from memory switch to to network if certain period > UPDATE_PERIOD
    @Override
    public Observable<Bitmap> getImages(String baseJsonUrl) {
        return getCachedImages().switchIfEmpty(getImagesFromNetwork(baseJsonUrl));
    }

    @Override
    public Observable<byte[]> getFilesFromNetwork(String baseJsonUrl) {
        return getMedia(baseJsonUrl).concatMap(baseResponse -> {
            return Observable.just(baseResponse.getUrls().getSmall());
        }).concatMap(url -> {
            return fileApiServices.getfile(url);
        }).flatMap( responseBodyResponse -> {
            if(responseBodyResponse.body() == null)
                return Observable.empty();
            InputStream inputStream =responseBodyResponse.body().byteStream();
            try {
                byte[] bytes = ByteStreams.toByteArray(inputStream);
                return Observable.just(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return Observable.empty();
        }).doOnNext(bytes -> {
            fileList.add(bytes);
        });
    }

    @Override
    public Observable<byte[]> getCachedFiles() {
        if(isUpTodate()) {
            return Observable.from(fileList);
        }else {
            timeStamp = System.currentTimeMillis();
            fileList.clear();
            return Observable.empty();
        }
    }

    // get files(example *.pdf *.txt files) from memory switch to get it from network after certain
    // period of time > UPDATE_PERIOD
    @Override
    public Observable<byte[]> getFiles(String baseJsonUrl) {
        return getCachedFiles().switchIfEmpty(getFilesFromNetwork(baseJsonUrl));
    }


    /***************** Get single image or single file from any Url *************/

    @Override
    public Observable<byte[]> getFileFromNetwork(String fileUrl) {
        return fileApiServices.getfile(fileUrl).flatMap(responseBodyResponse -> {
            if(responseBodyResponse.body() == null)
                return Observable.empty();
            InputStream inputStream =responseBodyResponse.body().byteStream();
            try {
                byte[] bytes = ByteStreams.toByteArray(inputStream);
                return Observable.just(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return Observable.empty();
        }).doOnNext(bytes -> {
            this.bytes = bytes;
        });
    }

    @Override
    public Observable<byte[]> getCachedFile() {
        if(isUpTodate()) {
            return Observable.just(bytes);
        }else {
            timeStamp = System.currentTimeMillis();
            bytes = new byte[20000];
            return Observable.empty();
        }
    }

    @Override
    public Observable<byte[]> getFile(String fileUrl) {
        return getCachedFile().switchIfEmpty(getFileFromNetwork(fileUrl));
    }

    @Override
    public Observable<Bitmap> getImageFromNetwork(String imageUrl) {
        return fileApiServices.getfile(imageUrl).flatMap(responseBodyResponse -> {
            if(responseBodyResponse.body() == null)
                return Observable.empty();
            int width, height;
            Bitmap bMap = BitmapFactory.decodeStream(responseBodyResponse.body().byteStream());
            width = bMap.getWidth();
            height = bMap.getHeight();
            Bitmap bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false);
            return Observable.just(bMap2);
        }).doOnNext( bitmap -> {
            this.image = bitmap;
        });
    }

    @Override
    public Observable<Bitmap> getCachedImage() {
        if(isUpTodate()) {
            return Observable.just(image);
        }else {
            timeStamp = System.currentTimeMillis();
            image = null;
            return Observable.empty();
        }
    }

    @Override
    public Observable<Bitmap> getImage(String imageUrl) {
        return getCachedImage().switchIfEmpty(getImageFromNetwork(imageUrl));
    }


}
