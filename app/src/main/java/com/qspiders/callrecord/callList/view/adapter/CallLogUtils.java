package com.qspiders.callrecord.callList.view.adapter;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import com.qspiders.callrecord.callList.model.CallLogInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class
CallLogUtils {

    private static CallLogUtils instance;
    private Context context;
    private ArrayList<CallLogInfo> today = null;
    private ArrayList<CallLogInfo> missedCallList = null;
    private ArrayList<CallLogInfo> yesterdayCallList = null;
    private ArrayList<CallLogInfo> dayBeforeYesterdayList = null;

    private CallLogUtils(Context context){
        this.context = context;
    }

    public static CallLogUtils getInstance(Context context){
        if(instance == null)
            instance = new CallLogUtils(context);
        return instance;
    }

    private void loadData(){
        today = new ArrayList<>();
        missedCallList = new ArrayList<>();
        yesterdayCallList = new ArrayList<>();
        dayBeforeYesterdayList = new ArrayList<>();

        String projection[] = {"_id", CallLog.Calls.NUMBER,CallLog.Calls.DATE,CallLog.Calls.DURATION,CallLog.Calls.TYPE,CallLog.Calls.CACHED_NAME};
        ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI,projection,null,null,CallLog.Calls.DEFAULT_SORT_ORDER);

        if(cursor == null){
            Log.d("CALLLOG","cursor is null");
            return;
        }

        if(cursor.getCount() == 0){
            Log.d("CALLLOG","cursor size is 0");
            return;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            CallLogInfo callLogInfo = new CallLogInfo();
            callLogInfo.setName(cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
            callLogInfo.setNumber(cursor.getString(cursor.getColumnIndex( CallLog.Calls.NUMBER )));
            callLogInfo.setCallType(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));
            callLogInfo.setDate(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
            callLogInfo.setDuration(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION)));

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            Date dateObj = new Date(callLogInfo.getDate());
            if(String.valueOf(formatter.format(dateObj)).equals(formatter.format(date).toString())){
                 today.add(callLogInfo);
            }
         if(String.valueOf(formatter.format(dateObj)).equals(getYesterdayDateString())){
                yesterdayCallList.add(callLogInfo);
            }
             if (String.valueOf(formatter.format(dateObj)).equals(getDayBeforeYesterdayDateString()))
            {
                dayBeforeYesterdayList.add(callLogInfo);
            }
//            switch(Integer.parseInt(callLogInfo.getCallType()))
//            {
//                case CallLog.Calls.OUTGOING_TYPE:
//                   if(String.valueOf(formatter.format(dateObj)).equals("07/07/2020")){
//                        outgoingCallList.add(callLogInfo);
//                    }
//                    break;
//
//                case CallLog.Calls.INCOMING_TYPE:
//                    if(String.valueOf(formatter.format(dateObj)).equals("07/07/2020")){
//                        incomingCallList.add(callLogInfo);
//                    }
//
//                    break;
//
//                case CallLog.Calls.MISSED_TYPE:
//                    if(String.valueOf(formatter.format(dateObj)).equals("08/07/2020")){
//                        missedCallList.add(callLogInfo);
//                    }
//                    break;
//
//
//            }
            cursor.moveToNext();
        }
        cursor.close();
    }
    //for yesterday
    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }
    private String getDayBeforeYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        return dateFormat.format(cal.getTime());
    }

    public ArrayList<CallLogInfo> readCallLogs(){
        if(today == null)
            loadData();
        return today;
    }

    public ArrayList<CallLogInfo> getMissedCalls(){
        if(today == null)
            loadData();
        return missedCallList;
    }

    public ArrayList<CallLogInfo> getDayBeforeYesterdayListCalls(){
        if(dayBeforeYesterdayList == null)
            loadData();
        return dayBeforeYesterdayList;
    }

    public ArrayList<CallLogInfo> getYesterdayCallList(){
        if(yesterdayCallList == null)
            loadData();
        return yesterdayCallList;
    }

    public long[] getAllCallState(String number){
        long output[] = new long[2];
        for(CallLogInfo callLogInfo : today){
            if(callLogInfo.getNumber().equals(number)){
                output[0]++;
                if(Integer.parseInt(callLogInfo.getCallType()) != CallLog.Calls.MISSED_TYPE)
                    output[1]+= callLogInfo.getDuration();
            }
        }
        return output;
    }

    public long[] getIncomingCallState(String number){
        long output[] = new long[2];
        for(CallLogInfo callLogInfo : dayBeforeYesterdayList){
            if(callLogInfo.getNumber().equals(number)){
                output[0]++;
                output[1]+= callLogInfo.getDuration();
            }
        }
        return output;
    }

    public long[] getOutgoingCallState(String number){
        long output[] = new long[2];
        for(CallLogInfo callLogInfo : yesterdayCallList){
            if(callLogInfo.getNumber().equals(number)){
                output[0]++;
                output[1]+= callLogInfo.getDuration();
            }
        }
        return output;
    }

    public int getMissedCallState(String number){
        int output =0;
        for(CallLogInfo callLogInfo : missedCallList){
            if(callLogInfo.getNumber().equals(number)){
                output++;
            }
        }
        return output;
    }
}
