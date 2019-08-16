package com.puma.nextwhere.database.dataaccessobject;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.puma.nextwhere.database.tables.UnlockSurpriseData;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by rajesh on 17/11/17.
 */
@Dao
public interface UnlockSupriseDao {

    @Query("SELECT * FROM unlocksurprisedata")
    Single<List<UnlockSurpriseData>> getData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UnlockSurpriseData> apiData);

    @Update
    void updateUnlockSurprise(UnlockSurpriseData unlockSurpriseData);

    @Query("DELETE FROM unlocksurprisedata")
    void nukeTable();

}
