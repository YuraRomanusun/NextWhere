package com.puma.nextwhere.network;

import android.location.Location;

import com.puma.nextwhere.fragment.ExploreCityGuideFragment;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.model.ExploreCityGuideApiResponse;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.preference.Preference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rajesh on 17/2/17.
 */

public class ServiceNetwork {
    private final Api networkService;

    public ServiceNetwork() {
        this.networkService = RetrofitInitializer.getInstance().provideCall().create(Api.class);
    }

    public Disposable login(DataCallBack callback, String code, String email) {
        return networkService.performLogin(code, email)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess, throwable -> callback.onError(new NetworkError(throwable)));

    }

    private SingleSource<?> getDistance(List<ExploreCityGuideApiResponse> exploreCityGuideApiResponses) {
        Location tempLocation = ExploreCityGuideFragment.location;
        if (tempLocation == null) {
            return Single.fromCallable((Callable<Object>) () -> exploreCityGuideApiResponses);
        } else {
            List<ExploreCityGuideApiResponse> dataToSend = new ArrayList<>();
            float[] result = new float[1];
            for (ExploreCityGuideApiResponse data : exploreCityGuideApiResponses) {
                Location.distanceBetween(Utils.parseDouble(data.getLatitud()),
                        Utils.parseDouble(data.getLongitud()),
                        tempLocation.getLatitude(),
                        tempLocation.getLongitude(),
                        result);
                // Converting kilometer to miles
                double distance = result[0] * 0.621371;
                data.setDistanceDouble(distance);
                data.setDistance(String.format(Locale.getDefault(), "%.2f", distance) + " mi");
                dataToSend.add(data);
            }
            Collections.sort(dataToSend, new SortAccordingToDistance());
            return Single.fromCallable((Callable<Object>) () -> dataToSend);
        }
    }

    private class SortAccordingToDistance implements Comparator<ExploreCityGuideApiResponse> {

        @Override
        public int compare(ExploreCityGuideApiResponse o1, ExploreCityGuideApiResponse o2) {
            if (o1 == null || o2 == null) return 0;
            if (o2.getDistanceDouble() == o1.getDistanceDouble()) return 0;
            else if ((o2.getDistanceDouble() - o1.getDistanceDouble()) > -1) return 1;
            else return -1;
        }
    }

    /**
     * @param callback
     * @param id
     * @return
     */
    public Disposable getCityGuideData(DataCallBack callback, String id) {
        return networkService.getCityGuideData(id)
                .flatMap((Function<List<ExploreCityGuideApiResponse>, SingleSource<?>>) this::getDistance)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess, throwable -> callback.onError(new NetworkError(throwable)));

    }

    /**
     * @param callback
     * @param id
     * @return
     */
    public Disposable getBarData(DataCallBack callback, String id) {
        return networkService.getBarData(id)
                .flatMap((Function<List<ExploreCityGuideApiResponse>, SingleSource<?>>) this::getDistance)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess, throwable -> callback.onError(new NetworkError(throwable)));
    }

    /**
     * @param callback
     * @param id
     * @return
     */
    public Disposable getRestrauntData(DataCallBack callback, String id) {
        return networkService.getRestrauntData(id)
                .flatMap((Function<List<ExploreCityGuideApiResponse>, SingleSource<?>>) this::getDistance)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess, throwable -> callback.onError(new NetworkError(throwable)));
    }

    /**
     * @param callback
     * @param id
     * @return
     */
    public Disposable getEventGuideData(DataCallBack callback, String id) {
        return networkService.getEventGuideData(id)
                .flatMap((Function<List<ExploreCityGuideApiResponse>, SingleSource<?>>) this::getDistance)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess, throwable -> callback.onError(new NetworkError(throwable)));
    }



    public Disposable getTripVideo(DataCallBack callback, String id) {
        return networkService.getTripVideo(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess, throwable -> callback.onError(new NetworkError(throwable)));
    }

    public Disposable getHotelInfo(DataCallBack callback, String idHotel) {
        return networkService.getHotelInfo(idHotel)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess, throwable -> callback.onError(new NetworkError(throwable)));
    }

    public Disposable getUnlockSupriseData(DataCallBack callback, String idDestino) {
        return networkService.getUnlockSupriseData(idDestino)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess, throwable -> callback.onError(new NetworkError(throwable)));

    }

    public Disposable getDirectionData(DataCallBack callback, String origin, String destination, boolean sensor, String key) {

        return networkService.getDirectionData(origin, destination, sensor, key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess, throwable -> callback.onError(new NetworkError(throwable)));
    }

    public interface DataCallBack {
        void onSuccess(Object data);

        void onError(NetworkError networkError);
    }
}
