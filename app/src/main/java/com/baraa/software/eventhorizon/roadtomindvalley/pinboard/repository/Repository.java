package com.baraa.software.eventhorizon.roadtomindvalley.pinboard.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.baraa.software.eventhorizon.roadtomindvalley.https.IFileApiServices;
import com.baraa.software.eventhorizon.roadtomindvalley.https.IMediaApiServices;
import com.baraa.software.eventhorizon.roadtomindvalley.https.model.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;

public class Repository implements IRepository {
    private static final String TAG = "Repository";
    IMediaApiServices mediaApiServices;
    IFileApiServices fileApiServices;

    List<BaseResponse> mediaList= new ArrayList<>();
    List<Bitmap>ImagesList = new ArrayList<>();

    public Repository(IMediaApiServices mediaApiServices, IFileApiServices fileApiServices) {
        this.mediaApiServices = mediaApiServices;
        this.fileApiServices = fileApiServices;
    }


    @Override
    public Observable<BaseResponse> getMediaFromNetwork() {
        Log.d(TAG, "getMediaFromNetwork: >>>>>>>");
        return mediaApiServices.getMediaInfo().flatMap((list)->{
            return Observable.from(list);
        });
    }

    @Override
    public Observable<BaseResponse> getCachedMedia() {
        return null;
    }

    @Override
    public Observable<BaseResponse> getMedia() {
        return null;
    }

    @Override
    public Observable<Bitmap> getImagesFromNetwork() {
        return getMediaFromNetwork().concatMap(baseResponse -> {
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
        });
    }




    @Override
    public Observable<Bitmap> getCachedImages() {
        return null;
    }

    @Override
    public Observable<Bitmap> getImages() {
        return null;
    }

    @Override
    public Observable<Response<ResponseBody>> getFilesFromNetwork() {
        return null;
    }

    @Override
    public Observable<Response<ResponseBody>> getCachedFiles() {
        return null;
    }

    @Override
    public Observable<Response<ResponseBody>> getFiles() {
        return null;
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
