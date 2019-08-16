package com.puma.nextwhere.network;


import com.puma.nextwhere.database.tables.UnlockSurpriseData;
import com.puma.nextwhere.model.DirectionData;
import com.puma.nextwhere.model.ExploreCityGuideApiResponse;
import com.puma.nextwhere.model.HotelDetails;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.model.TripVideoModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rajesh on 1/3/17.
 */

interface Api {
    @GET("loginApi.php")
    Single<LoginApiResponse> performLogin(@Query("code") String code, @Query("email") String email);

    @GET("cityGuideApi.php")
    Single<List<ExploreCityGuideApiResponse>> getCityGuideData(@Query("id") String id);

    @GET("baresApi.php")
    Single<List<ExploreCityGuideApiResponse>> getBarData(@Query("id") String id);

    @GET("restaurantApi.php")
    Single<List<ExploreCityGuideApiResponse>> getRestrauntData(@Query("id") String id);

    @GET("eventsGuideApi.php")
    Single<List<ExploreCityGuideApiResponse>> getEventGuideData(@Query("id") String id);

    @GET("destinationApi.php")
    Single<List<TripVideoModel>> getTripVideo(@Query("id") String id);

    @GET("hotelApi.php")
    Single<HotelDetails> getHotelInfo(@Query("id") String idHotel);

    @GET("destinationPrendasApi.php")
    Single<List<UnlockSurpriseData>> getUnlockSupriseData(@Query("id") String idDestino);

    @GET("https://maps.googleapis.com/maps/api/directions/json")
    Single<DirectionData> getDirectionData(@Query("origin") String origin, @Query("destination") String destination, @Query("sensor") boolean sensor, @Query("key") String key);
}
