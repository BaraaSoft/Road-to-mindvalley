package com.baraa.software.eventhorizon.roadtomindvalley.pinboard.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.baraa.software.eventhorizon.roadtomindvalley.https.IFileApiServices;
import com.baraa.software.eventhorizon.roadtomindvalley.https.IMediaApiServices;
import com.baraa.software.eventhorizon.roadtomindvalley.https.model.BaseResponse;
import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class Repository implements IRepository {
    private static final String TAG = "Repository";
    public static final long UPDATE_PERIOD = 20 * 1000;
    IMediaApiServices mediaApiServices;
    IFileApiServices fileApiServices;

    List<BaseResponse> mediaList= new ArrayList<>();
    List<Bitmap> imagesList = new ArrayList<>();
    List<byte[]> fileList = new ArrayList<>();

    long timeStamp;

    public Repository(IMediaApiServices mediaApiServices, IFileApiServices fileApiServices) {
        this.mediaApiServices = mediaApiServices;
        this.fileApiServices = fileApiServices;
        this.timeStamp = System.currentTimeMillis();
    }

    public boolean isUpTodate(){
        return System.currentTimeMillis() - timeStamp < UPDATE_PERIOD;
    }


    @Override
    public Observable<BaseResponse> getMediaFromNetwork() {
        return mediaApiServices.getMediaInfo().flatMap((list)->{
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
    public Observable<BaseResponse> getMedia() {
        return getCachedMedia().switchIfEmpty(getMediaFromNetwork());
    }

    @Override
    public Observable<Bitmap> getImagesFromNetwork() {
        return getMedia().concatMap(baseResponse -> {
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
    public Observable<Bitmap> getImages() {
        return getCachedImages().switchIfEmpty(getImagesFromNetwork());
    }

    @Override
    public Observable<byte[]> getFilesFromNetwork() {
        return getMedia().concatMap(baseResponse -> {
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
    public Observable<byte[]> getFiles() {
        return getCachedFiles().switchIfEmpty(getFilesFromNetwork());
    }


}

//    public Bitmap decodeURI(InputStream inputStream) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(inputStream,new Rect(),options);
//
//        // Only scale if we need to
//        // (16384 buffer for img processing)
//        Boolean scaleByHeight = Math.abs(options.outHeight - 100) >= Math.abs(options.outWidth - 100);
//        if(options.outHeight * options.outWidth * 2 >= 16384){
//            // Load, scaling to smallest power of 2 that'll get it <= desired dimensions
//            double sampleSize = scaleByHeight
//                    ? options.outHeight / 1000
//                    : options.outWidth / 1000;
//            options.inSampleSize =
//                    (int)Math.pow(2d, Math.floor(
//                            Math.log(sampleSize)/Math.log(2d)));
//        }
//
//        options.inJustDecodeBounds = false;
//        options.inTempStorage = new byte[512];
//        Bitmap output = BitmapFactory.decodeStream(inputStream,new Rect(),options);
//        return output;
//    }
//if(responseBodyResponse.body() == null)
//        return Observable.empty();
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//
//        InputStream is = responseBodyResponse.body().byteStream();
//        int nRead;
//        byte[] data = new byte[16384];
//        try {
//        nRead = is.read(data, 0, data.length);
//        while ( nRead != -1) {
//        buffer.write(data, 0, nRead);
//        }
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
//
//
//        try {
//        buffer.flush();
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
//        return Observable.just(buffer.toByteArray());
