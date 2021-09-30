package com.qspiders.callrecord.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


//@Parcelize
//@Entity (tableName ="callrecord_table" )
//data class CallRecordModel(
//
//        @PrimaryKey(autoGenerate = true)
//        @ColumnInfo(name = "id") var id:Int,
//
//        @ColumnInfo(name = "number") var number:String,
//
//        @ColumnInfo(name = "time") var time:String,
//
//        @ColumnInfo(name = "duration") var duration:String,
//
//        @ColumnInfo(name = "call_type") var call_type:String,
//
//        @ColumnInfo(name = "comment") var comment:String,
//        @ColumnInfo(name = "audio") var audio:String
//
//): Parcelable {
//        constructor() : this(0, "","","","","","")
//}

@Parcelize
@Entity(tableName = "callrecord_table")
data class CallRecordModel(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") var id: Int,

        @ColumnInfo(name = "number")
        var number: String,

        @ColumnInfo(name = "time")
        var time: String,

        @ColumnInfo(name = "duration")
        var duration: String,

        @ColumnInfo(name = "call_type")
        var call_type: String,

        @ColumnInfo(name = "comment")
        var comment: String,

        @ColumnInfo(name = "audio")
        var audio: String

): Parcelable {
        constructor() : this(0, "","","","","","")
}