package com.qspiders.callrecord.room

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CallRecordDao {

    @Insert
    fun insertCall(callRecordModel: CallRecordModel):Long

    @Query("SELECT * from callrecord_table ORDER BY id ASC")
    fun getCall() : List<CallRecordModel>

    @Query("DELETE FROM callrecord_table WHERE id LIKE :id")
    fun deleteSyncedRecordings(id: Int) : Int

    @Query("SELECT * from callrecord_table ORDER BY id ASC")
    fun selectAll(): Cursor?

    @Query("DELETE FROM callrecord_table")
    fun deleteAll()
}