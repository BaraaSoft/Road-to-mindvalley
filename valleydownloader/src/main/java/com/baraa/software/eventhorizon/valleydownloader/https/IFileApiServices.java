package com.baraa.software.eventhorizon.valleydownloader.https;

import android.graphics.Bitmap;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface IFileApiServices {
    @Streaming
    @GET
    Observable<Response<ResponseBody>> getfile(@Url String url);

    @GET
    Observable<Bitmap> getImage(@Url String url);

}
