package com.baraa.software.eventhorizon.valleydownloader.https;


import com.baraa.software.eventhorizon.valleydownloader.https.model.BaseResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

public interface IMediaJsonApiServices {
    @GET
    Observable<List<BaseResponse>> getMediaInfo(@Url String url);
}
