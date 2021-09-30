package com.qspiders.callrecord.room;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {CallRecordModel.class}, version = 1, exportSchema = false)
public abstract class CallRecordDatabase extends RoomDatabase {
    private static final String LOG_TAG = CallRecordDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "CallRecordsDatabase";
    private static CallRecordDatabase sInstance;

    public static CallRecordDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        CallRecordDatabase.class, CallRecordDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract CallRecordDao callRecordDao();
}