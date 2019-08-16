package com.puma.nextwhere.database.databaseoperation;

import com.puma.nextwhere.database.DatabaseInfo;
import com.puma.nextwhere.database.tables.TravelJournalInfo;
import com.puma.nextwhere.database.tables.UnlockSurpriseData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.completable.CompletableFromAction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rajesh on 18/11/17.
 */

public class TravelJournalDbOperation {

    private CompositeDisposable subscriptions;
    private static TravelJournalDbOperation travelJournalDbOperation = new TravelJournalDbOperation();

    public static TravelJournalDbOperation getInstance() {
        return travelJournalDbOperation;
    }

    private TravelJournalDbOperation() {
        this.subscriptions = new CompositeDisposable();
    }

    public void getTravelJournalData(DatabaseCallback callBack) {
        if (callBack == null) callBack = databaseCallback;
        DatabaseCallback finalCallBack = callBack;
        Disposable disposable = DatabaseInfo.getInstance()
                .travelJournalDao()
                .getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((it) -> finalCallBack.onSuccess(it), throwable -> finalCallBack.onError(""));
        subscriptions.add(disposable);
    }

    public void insert(TravelJournalInfo apiData, DatabaseCallback callBack) {
        if (callBack == null) callBack = databaseCallback;
        DatabaseCallback finalCallBack = callBack;
        Disposable disposable = new CompletableFromAction(() -> DatabaseInfo.getInstance()
                .travelJournalDao()
                .insert(apiData))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> finalCallBack.onSuccess(true), throwable -> finalCallBack.onError(""));
        subscriptions.add(disposable);

    }


    private DatabaseCallback databaseCallback = new DatabaseCallback() {
        @Override
        public void onSuccess(Object data) {

        }

        @Override
        public void onError(String error) {

        }
    };

    public interface DatabaseCallback {
        void onSuccess(Object data);

        void onError(String error);
    }
}
