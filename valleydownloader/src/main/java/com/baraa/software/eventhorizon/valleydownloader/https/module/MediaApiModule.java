package com.baraa.software.eventhorizon.valleydownloader.https.module;


import com.baraa.software.eventhorizon.valleydownloader.https.IMediaJsonApiServices;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MediaApiModule {
    public final String BASE_URL = "http://pastebin.com/";


    @Provides
    public OkHttpClient providesHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();
    }

    @Provides
    public Retrofit providesRetrofit(String baseUrl, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public IMediaJsonApiServices providesBookSearchService(){
        return providesRetrofit(BASE_URL,providesHttpClient()).create(IMediaJsonApiServices.class);
    }
}
