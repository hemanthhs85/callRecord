package com.qspiders.callrecord.provider.utill

import android.content.ContentValues
import androidx.room.ColumnInfo
import com.qspiders.callrecord.room.CallRecordModel

fun fromContentValues(contentValues: ContentValues?): CallRecordModel {
    val callRecordModel = CallRecordModel()
    contentValues?.apply {

        if (containsKey("id"))
            callRecordModel.id = getAsInteger("id")

        if (containsKey("number"))
            callRecordModel.number = getAsString("number")

        if (containsKey("time"))
            callRecordModel.time = getAsString("time")

        if (containsKey("duration"))
            callRecordModel.duration = getAsString("duration")
        if (containsKey("call_type"))
            callRecordModel.call_type = getAsString("call_type")

        if (containsKey("comment"))
            callRecordModel.comment = getAsString("comment")
        if (containsKey("audio"))
            callRecordModel.audio = getAsString("audio")



    }
    return callRecordModel
}

