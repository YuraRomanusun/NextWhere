package com.puma.nextwhere.database.databaseoperation;

import com.puma.nextwhere.database.DatabaseInfo;
import com.puma.nextwhere.database.tables.UnlockSurpriseData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.completable.CompletableFromAction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rajesh on 18/11/17.
 */

public class UnlockSurpriseDbOperation {

    private CompositeDisposable subscriptions;
    private static UnlockSurpriseDbOperation unlockSurpriseDbOperation = new UnlockSurpriseDbOperation();

    public static UnlockSurpriseDbOperation getInstance() {
        return unlockSurpriseDbOperation;
    }

    private UnlockSurpriseDbOperation() {
        this.subscriptions = new CompositeDisposable();
    }

    public void getSupriseData(DatabaseCallback callBack) {
        if (callBack == null) callBack = databaseCallback;
        DatabaseCallback finalCallBack = callBack;
        Disposable disposable = DatabaseInfo.getInstance()
                .unlockSupriseData()
                .getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((it) -> finalCallBack.onSuccess(it), throwable -> finalCallBack.onError(""));
        subscriptions.add(disposable);
    }

    public void insertAll(List<UnlockSurpriseData> apiData, DatabaseCallback callBack) {
        if (callBack == null) callBack = databaseCallback;
        DatabaseCallback finalCallBack = callBack;
        Disposable disposable = new CompletableFromAction(() -> DatabaseInfo.getInstance()
                .unlockSupriseData()
                .insertAll(apiData))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> finalCallBack.onSuccess(true), throwable -> finalCallBack.onError(""));
        subscriptions.add(disposable);

    }

    public void updateUnlockSurprise(UnlockSurpriseData unlockSurpriseData, DatabaseCallback callBack) {
        if (callBack == null) callBack = databaseCallback;
        DatabaseCallback finalCallBack = callBack;
        Disposable disposable = new CompletableFromAction(() -> DatabaseInfo.getInstance()
                .unlockSupriseData()
                .updateUnlockSurprise(unlockSurpriseData))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> finalCallBack.onSuccess(true), throwable -> finalCallBack.onError(""));
        subscriptions.add(disposable);

    }

    public void nukeTable(DatabaseCallback callBack) {
        if (callBack == null) callBack = databaseCallback;
        DatabaseCallback finalCallBack = callBack;
        Disposable disposable = new CompletableFromAction(() -> DatabaseInfo.getInstance()
                .unlockSupriseData()
                .nukeTable())
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
