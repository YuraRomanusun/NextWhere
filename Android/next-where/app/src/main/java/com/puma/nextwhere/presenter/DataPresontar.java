package com.puma.nextwhere.presenter;


import com.puma.nextwhere.apicallbacks.ICityGuideCallback;
import com.puma.nextwhere.apicallbacks.IDirectionDataCallback;
import com.puma.nextwhere.apicallbacks.IEventGuideCallback;
import com.puma.nextwhere.apicallbacks.ILoginCallbackView;
import com.puma.nextwhere.apicallbacks.ITripDetailCallback;
import com.puma.nextwhere.apicallbacks.IUnlockSupriseCallback;
import com.puma.nextwhere.apicallbacks.IVideoCallback;
import com.puma.nextwhere.database.databaseoperation.UnlockSurpriseDbOperation;
import com.puma.nextwhere.database.tables.UnlockSurpriseData;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.model.DirectionData;
import com.puma.nextwhere.model.ExploreCityGuideApiResponse;
import com.puma.nextwhere.model.HotelDetails;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.model.TripVideoModel;
import com.puma.nextwhere.network.NetworkError;
import com.puma.nextwhere.network.ServiceNetwork;
import com.puma.nextwhere.preference.Preference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.puma.nextwhere.helper.Utils.getDateFromString;
import static com.puma.nextwhere.helper.Utils.isWithInGiveDate;

public class DataPresontar {
    private final ServiceNetwork service;
    private CompositeDisposable subscriptions;
    private ILoginCallbackView loginCallBack;
    private ITripDetailCallback iTripDetailCallback;
    private ICityGuideCallback iCityGuideCallback;
    private IVideoCallback iVideoCallBack;
    private IUnlockSupriseCallback iuiUnlockSupriseCallback;
    private IEventGuideCallback iEventGuideCallback;
    private IDirectionDataCallback iDirectionDataCallback;

    public DataPresontar(ServiceNetwork service) {
        this.service = service;
        this.subscriptions = new CompositeDisposable();
    }

    public void initializeLoginCallBack(ILoginCallbackView loginCallBack) {
        this.loginCallBack = loginCallBack;
    }

    public void initializeVideoCallback(IVideoCallback iVideoCallBack) {
        this.iVideoCallBack = iVideoCallBack;
    }

    public void initializeViewMyTripCallback(ITripDetailCallback iTripDetailCallback) {
        this.iTripDetailCallback = iTripDetailCallback;
    }

    public void initializeExploreCityGuideCallback(ICityGuideCallback iCityGuideCallback) {
        this.iCityGuideCallback = iCityGuideCallback;
    }

    public void login(String code, String email) {
        loginCallBack.showWait();
        Disposable disposable = service.login(new ServiceNetwork.DataCallBack() {
            @Override
            public void onSuccess(Object data) {
                loginCallBack.removeWait();
                if (data instanceof LoginApiResponse) {
                    LoginApiResponse loginApiResponse = (LoginApiResponse) data;
                    if (loginApiResponse.getError().isEmpty()) {
                        loginCallBack.onSuccess((LoginApiResponse) data);
                    } else {
                        loginCallBack.onFailure(AppConstants.EMPTY_STRING);
                    }
                }
            }

            @Override
            public void onError(NetworkError networkError) {
                loginCallBack.removeWait();
                loginCallBack.onFailure(networkError.toString());
            }
        }, code, email);
        subscriptions.add(disposable);
    }

    /**
     * @param id
     */
    public void getCityGuideData(String id) {
        iCityGuideCallback.showWait();
        Disposable disposable = service.getCityGuideData(new ServiceNetwork.DataCallBack() {
            @Override
            public void onSuccess(Object data) {
                iCityGuideCallback.removeWait();
                if (Utils.validateList(data)) {
                    iCityGuideCallback.cityGuideResponse(((List<ExploreCityGuideApiResponse>) data));
                } else {
                    iCityGuideCallback.showNoDataView();
                }
            }

            @Override
            public void onError(NetworkError networkError) {
                iCityGuideCallback.removeWait();
                iCityGuideCallback.onFailure(networkError.toString());
            }
        }, id);
        subscriptions.add(disposable);
    }

    public void getDirectionData(String origin, String destination, boolean sensor, String key) {
        iDirectionDataCallback.showWait();
        Disposable disposable = service.getDirectionData(new ServiceNetwork.DataCallBack() {
            @Override
            public void onSuccess(Object data) {
                iDirectionDataCallback.removeWait();
                iDirectionDataCallback.setDirectionData((DirectionData) data);
//                if (Utils.validateList(data)) {
//
//                } else {
//                    iDirectionDataCallback.showNoDataView();
//                }
            }

            @Override
            public void onError(NetworkError networkError) {
                iDirectionDataCallback.removeWait();
                iDirectionDataCallback.onFailure(networkError.toString());
            }
        }, origin, destination, sensor, key);
        subscriptions.add(disposable);
    }

    /**
     * @param id
     */
    public void getBarData(String id) {

        iCityGuideCallback.showWait();
        Disposable disposable = service.getBarData(new ServiceNetwork.DataCallBack() {
            @Override
            public void onSuccess(Object data) {
                iCityGuideCallback.removeWait();
                if (Utils.validateList(data)) {
                    iCityGuideCallback.setBarData(((List<ExploreCityGuideApiResponse>) data));
                } else {
                    iCityGuideCallback.showNoDataView();
                }
            }

            @Override
            public void onError(NetworkError networkError) {
                iCityGuideCallback.removeWait();
                iCityGuideCallback.onFailure(networkError.toString());
            }
        }, id);
        subscriptions.add(disposable);
    }

    /**
     * @param id
     */
    public void getRestrauntData(String id) {

        iCityGuideCallback.showWait();
        Disposable disposable = service.getRestrauntData(new ServiceNetwork.DataCallBack() {
            @Override
            public void onSuccess(Object data) {
                iCityGuideCallback.removeWait();
                if (Utils.validateList(data)) {
                    iCityGuideCallback.setRestrauntData(((List<ExploreCityGuideApiResponse>) data));
                } else {
                    iCityGuideCallback.showNoDataView();
                }
            }

            @Override
            public void onError(NetworkError networkError) {
                iCityGuideCallback.removeWait();
                iCityGuideCallback.onFailure(networkError.toString());
            }
        }, id);
        subscriptions.add(disposable);
    }

    /**
     * @param id
     */
    public void getEventGuideData(String id) {
        iEventGuideCallback.showWait();
        Disposable disposable = service.getEventGuideData(new ServiceNetwork.DataCallBack() {
            @Override
            public void onSuccess(Object data) {
                iEventGuideCallback.removeWait();
                if (Utils.validateList(data)) {
                    sortByDate(((List<ExploreCityGuideApiResponse>) data));
                } else {
                    iEventGuideCallback.showNoDataView();
                }
            }

            @Override
            public void onError(NetworkError networkError) {
                iEventGuideCallback.removeWait();
                iEventGuideCallback.onFailure(networkError.toString());
            }
        }, id);
        subscriptions.add(disposable);
    }

    private void sortByDate(List<ExploreCityGuideApiResponse> exploreCityGuideApiResponses) {
        Single.fromCallable(() -> {
                    LoginApiResponse loginApiResponse = Preference.getInstance().getUserInfo();
                    List<ExploreCityGuideApiResponse> tempRecord = new ArrayList<>();
                    for (ExploreCityGuideApiResponse exploreCityGuideApiResponse : exploreCityGuideApiResponses) {
                        Date startDate = getDateFromString(loginApiResponse.getFechaDesde(), AppConstants.DATE_FORMAT_LOGIN);
                        Date endDate = getDateFromString(loginApiResponse.getFechaHasta(), AppConstants.DATE_FORMAT_LOGIN);
                        Date event = getDateFromString(exploreCityGuideApiResponse.getFecha(), AppConstants.DATE_FORMAT_EXPLORE_CITY_GUIDE);
                        if (isWithInGiveDate(event, startDate, endDate)) {
                            tempRecord.add(exploreCityGuideApiResponse);
                        }
                    }
                    Collections.sort(tempRecord, new SortAccordingToDate());
                    return tempRecord;
                }
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if (iEventGuideCallback != null) {
                        iEventGuideCallback.removeWait();
                        iEventGuideCallback.setEventGuideData(o);
                    }
                }, throwable -> {
                    if (iEventGuideCallback != null) {
                        iEventGuideCallback.removeWait();
                        iEventGuideCallback.onFailure(throwable.toString());
                    }

                });

    }


    public void getTripVideo(String userId) {
        iVideoCallBack.showWait();
        Disposable disposable = service.getTripVideo(new ServiceNetwork.DataCallBack() {
            @Override
            public void onSuccess(Object data) {
                iVideoCallBack.removeWait();
                List<TripVideoModel> tripVideoModels = (List<TripVideoModel>) data;
                if (tripVideoModels != null && tripVideoModels.size() > 0) {
                    TripVideoModel tripVideoModel = tripVideoModels.get(0);
                    if (tripVideoModel.getVideo() != null && !tripVideoModel.getVideo().isEmpty()) {
                        iVideoCallBack.showVideo(Utils.extractVideoIdFromUrl(tripVideoModel.getVideo()), tripVideoModel.getVideo());
                    }
                }
            }

            @Override
            public void onError(NetworkError networkError) {
                iVideoCallBack.removeWait();
                iVideoCallBack.onFailure(networkError.toString());
            }
        }, userId);
        subscriptions.add(disposable);
    }

    public void setHotelInfo(String idHotel) {
        iTripDetailCallback.showWait();
        Disposable disposable = service.getHotelInfo(new ServiceNetwork.DataCallBack() {
            @Override
            public void onSuccess(Object data) {
                iTripDetailCallback.removeWait();
                iTripDetailCallback.setHotelDetails((HotelDetails) data);
            }

            @Override
            public void onError(NetworkError networkError) {
                iTripDetailCallback.removeWait();
                iTripDetailCallback.onFailure(networkError.toString());
            }
        }, idHotel);
        subscriptions.add(disposable);
    }

    public void initializeUnlockSupriseCallback(IUnlockSupriseCallback iUnlockSupriseCallback) {
        this.iuiUnlockSupriseCallback = iUnlockSupriseCallback;
    }

    public void initializeDirectionDataCallBack(IDirectionDataCallback iDirectionDataCallback) {
        this.iDirectionDataCallback = iDirectionDataCallback;
    }

    public void getUnlockSupriseData(String idDestino) {
        iuiUnlockSupriseCallback.showWait();
        Disposable disposable = service.getUnlockSupriseData(new ServiceNetwork.DataCallBack() {
            @Override
            public void onSuccess(Object temp) {
                List<UnlockSurpriseData> apiData = (List<UnlockSurpriseData>) temp;
                iuiUnlockSupriseCallback.removeWait();
                // Fetching Data from local database and validating data from API
                UnlockSurpriseDbOperation.getInstance().getSupriseData(new UnlockSurpriseDbOperation.DatabaseCallback() {
                    @Override
                    public void onSuccess(Object data) {
                        ArrayList<UnlockSurpriseData> finalData = new ArrayList<>();
                        // Data fetched from localDatabase
                        ArrayList<UnlockSurpriseData> localData = (ArrayList<UnlockSurpriseData>) data;
                        // If reservation code contains 3 then we will show only 3 task
                        boolean isThreeTask = Preference.getInstance().getReservationCode().contains("S");
                        if (isThreeTask && apiData.size() > 0) {
                            apiData.subList(3, apiData.size()).clear();
                        }
                        // Traversing api data and validating and checking if it exist in local database or not
                        for (UnlockSurpriseData unlockSurpriseData : apiData) {
                            if (localData.contains(unlockSurpriseData)) {
                                // Adding local database value to final List
                                finalData.add(localData.get(localData.indexOf(unlockSurpriseData)));
                            }
                        }

                        if (finalData.size() != apiData.size()) {
                            finalData.clear();
                            finalData.addAll(apiData);
                            UnlockSurpriseDbOperation.getInstance().nukeTable(null);
                            UnlockSurpriseDbOperation.getInstance().insertAll(apiData, null);
                        }

                        iuiUnlockSupriseCallback.unlockSupriseData(finalData);

                    }

                    @Override
                    public void onError(String error) {
                        iuiUnlockSupriseCallback.unlockSupriseData(apiData);
                    }
                });
            }

            @Override
            public void onError(NetworkError networkError) {
                iuiUnlockSupriseCallback.removeWait();
                iuiUnlockSupriseCallback.onFailure(networkError.toString());
            }
        }, idDestino);
        subscriptions.add(disposable);
    }

    public void initializeEventCallback(IEventGuideCallback iEventGuideCallback) {
        this.iEventGuideCallback = iEventGuideCallback;
    }

    public class SortAccordingToDate implements Comparator<ExploreCityGuideApiResponse> {
        @Override
        public int compare(ExploreCityGuideApiResponse o1, ExploreCityGuideApiResponse o2) {
            if (o1 == null || o2 == null)
                return -1;
            Date start = Utils.getDateFromString(o1.getFecha(), AppConstants.DATE_FORMAT_EXPLORE_CITY_GUIDE);
            Date end = Utils.getDateFromString(o2.getFecha(), AppConstants.DATE_FORMAT_EXPLORE_CITY_GUIDE);
            if (start == null || end == null) return -1;
            return start.compareTo(end);
        }
    }
}
