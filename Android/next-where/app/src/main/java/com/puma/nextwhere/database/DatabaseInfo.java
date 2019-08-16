package com.puma.nextwhere.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.puma.nextwhere.NextwhereApplication;
import com.puma.nextwhere.database.dataaccessobject.TravelJournalDao;
import com.puma.nextwhere.database.dataaccessobject.UnlockSupriseDao;
import com.puma.nextwhere.database.tables.TravelJournalInfo;
import com.puma.nextwhere.database.tables.UnlockSurpriseData;

/**
 * Created by rajesh on 17/11/17.
 */
@Database(entities = {UnlockSurpriseData.class, TravelJournalInfo.class}, version = 1)
public abstract class DatabaseInfo extends RoomDatabase {
    public abstract UnlockSupriseDao unlockSupriseData();

    public abstract TravelJournalDao travelJournalDao();

    private static volatile DatabaseInfo INSTANCE;

    public static DatabaseInfo getInstance() {
        if (INSTANCE == null) {
            synchronized (DatabaseInfo.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(NextwhereApplication.getInstance().getContext(),
                            DatabaseInfo.class, "next-where")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

