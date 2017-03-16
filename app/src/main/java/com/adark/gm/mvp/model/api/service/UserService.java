package com.adark.gm.mvp.model.api.service;

import com.adark.gm.mvp.model.entity.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Shelly on 2017-3-13.
 */

public interface UserService {

    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";

    @Headers({HEADER_API_VERSION})
    @GET("/user") Observable<User> getLoginUser(@Header("Authorization") String pCredentials);

    @Headers({HEADER_API_VERSION})
    @GET("/users") Observable<List<User>> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);


}
