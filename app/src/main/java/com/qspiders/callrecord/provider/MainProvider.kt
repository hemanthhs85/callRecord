package com.qspiders.callrecord.provider

import android.content.*
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.qspiders.callrecord.helper.PrefManager
import com.qspiders.callrecord.provider.utill.fromContentValues
import com.qspiders.callrecord.room.AppExecutors
import com.qspiders.callrecord.room.CallRecordDao
import com.qspiders.callrecord.room.CallRecordDatabase

class MainProvider : ContentProvider() {

    private val uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private val authority = "com.qspiders.callrecord"
    private val userId = 1

    private lateinit var callRecordDao: CallRecordDao

    override fun onCreate(): Boolean {
        context?.let { callRecordDao = CallRecordDatabase.getInstance(it)?.callRecordDao()!! }

        uriMatcher.addURI(authority, "callrecord_table", userId)

        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? =
            context?.let {
                when (uriMatcher.match(uri)) {
                    userId -> {
                        val id: Long = callRecordDao.insertCall(fromContentValues(values))
                        it.contentResolver
                                .notifyChange(uri, null)
                        return@let ContentUris.withAppendedId(uri, id)
                    }

                    else -> throw IllegalArgumentException("Unknown Uri: $uri")
                }
            }

    override fun query(

            uri: Uri,
            projection: Array<String>?,
            selection: String?,
            selectionArgs: Array<String>?,
            sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
         val uriType:String?
        if(uri.toString()=="content://com.qspiders.callrecord/login_detail"){
            uriType="2"
        }
        else uriType= "1"

        when (uriType) {
            "1" -> cursor = callRecordDao.selectAll()

            else -> throw IllegalArgumentException("Unknown uri $uri")
        }
        return cursor
    }

    override fun update(
            uri: Uri,
            values: ContentValues?,
            selection: String?,
            selectionArgs: Array<String>?
    ): Int {
        var count = 0
        val match: Int = uriMatcher.match(uri)

        when (match) {
            1 -> {
            }
            2 -> {
            }
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
        context.contentResolver.notifyChange(uri, null)
        return count
    }


//    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
//        return 0
//    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun delete(uri: Uri?, where: String?, whereArgs: Array<String?>?): Int {
//        database.delete(ENTRIES_TABLE_NAME, where, whereArgs)
//        callRecordDao.deleteAll()
//        Log.d(where!!.split("=")[1],"gygygygy")
        Log.d(uri.toString(), "gygygygy")
        if(uri.toString() == "content://com.qspiders.callrecord/callrecord_table"){
            callRecordDao.deleteAll()
        }


//        context.contentResolver.notifyChange(uri!!,null)
        return 0
    }
//    override fun delete(uri: Uri?, where: String?, whereArgs: Array<String?>?): Int {
////        database.delete(ENTRIES_TABLE_NAME, where, whereArgs)
////        callRecordDao.deleteAll()
//        Log.d(where!!.split("=")[1],"gygygygy")
//        callRecordDao.deleteByUserId(Integer.parseInt(where!!.split("=")[1]))
////        context.contentResolver.notifyChange(uri!!,null)
//        return 0
//    }

}