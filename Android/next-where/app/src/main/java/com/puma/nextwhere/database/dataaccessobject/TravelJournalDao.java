package com.puma.nextwhere.database.dataaccessobject;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.puma.nextwhere.database.tables.TravelJournalInfo;
import com.puma.nextwhere.database.tables.UnlockSurpriseData;

import io.reactivex.Single;

/**
 * Created by rajesh on 17/11/17.
 */
@Dao
public interface TravelJournalDao {

    @Query("SELECT * FROM traveljournalinfo limit 1")
    Single<TravelJournalInfo> getData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TravelJournalInfo apiData);


    @Query("DELETE FROM traveljournalinfo")
    void nukeTable();

}
