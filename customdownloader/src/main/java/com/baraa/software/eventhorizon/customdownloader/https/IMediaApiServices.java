package com.baraa.software.eventhorizon.customdownloader.https;

import com.baraa.software.eventhorizon.roadtomindvalley.https.model.BaseResponse;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface IMediaApiServices {
    @GET("raw/wgkJgazE/")
    Observable<List<BaseResponse>> getMediaInfo();

}
