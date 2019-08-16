package com.puma.nextwhere.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.puma.nextwhere.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rajesh on 17/2/17.
 */

class RetrofitInitializer {
    private static RetrofitInitializer networkModule;
    private Retrofit retrofitInstance;

    static RetrofitInitializer getInstance() {
        if (networkModule == null)
            networkModule = new RetrofitInitializer();
        return networkModule;
    }

    private RetrofitInitializer() {
    }

    Retrofit provideCall() {
        if (retrofitInstance == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASEURL)
                    .client(httpClient.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofitInstance;
    }

}
