package com.qspiders.callrecord.callList.view.adapter;

import android.content.Context;
import android.provider.CallLog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.qspiders.callrecord.R;
import com.qspiders.callrecord.callList.model.CallLogInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.CallLogViewHolder>{

    ArrayList<CallLogInfo> callLogInfoArrayList;
    Context context;
    OnCallLogItemClickListener onItemClickListener;
    String day;

    public interface OnCallLogItemClickListener {
        void onItemClicked(CallLogInfo callLogInfo);
    }

    public CallLogAdapter(Context context, String day){
        callLogInfoArrayList = new ArrayList<>();
        this.context = context;
        this.day=day;
    }

    public void setOnItemClickListener(OnCallLogItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @Override
    public CallLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
// infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
// set the view's size, margins, paddings and layout parameters
        CallLogViewHolder vh = new CallLogViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull CallLogViewHolder holder, int position) {
        holder.bind(callLogInfoArrayList.get(position));
    }

    public void addCallLog(CallLogInfo callLogInfo){
        callLogInfoArrayList.add(callLogInfo);
    }

    public void addAllCallLog(ArrayList<CallLogInfo> list){
        callLogInfoArrayList.clear();
        callLogInfoArrayList.addAll(list);
    }

    @Override
    public int getItemCount() {
        return callLogInfoArrayList.size();
    }

    class CallLogViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        ImageView profileImg;
        TextView nameTxt, callDateTxt,callNumberTxt,callTypeTxt;
        public CallLogViewHolder(View view) {
            super(view);
            itemView=view;
// get the reference of item view's
            profileImg=(ImageView)itemView.findViewById(R.id.imageViewProfile);
            nameTxt = (TextView) itemView.findViewById(R.id.textViewName);
            callDateTxt = (TextView) itemView.findViewById(R.id.textViewCallDate);
            callNumberTxt = (TextView) itemView.findViewById(R.id.textViewCallNumber);
            callTypeTxt=(TextView)itemView.findViewById(R.id.call_type_tv);

        }


        public void bind(final CallLogInfo callLog){
            switch(Integer.parseInt(callLog.getCallType()))
            {
                case CallLog.Calls.OUTGOING_TYPE:
                    profileImg.setImageResource(R.drawable.outgoing_call);
                    DrawableCompat.setTint(profileImg.getDrawable(), ContextCompat.getColor(context, R.color.colorPrimary));
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    profileImg.setImageResource(R.drawable.incoming_call);
                    DrawableCompat.setTint(profileImg.getDrawable(), ContextCompat.getColor(context, R.color.colorPrimary));
                    //  itemBinding.textViewCallDuration.setText(Utils.formatSeconds(callLog.getDuration()));
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    profileImg.setImageResource(R.drawable.incoming_call);
                    DrawableCompat.setTint(profileImg.getDrawable(), ContextCompat.getColor(context, R.color.red));
                    break;
            }

            if(TextUtils.isEmpty(callLog.getName()))
            {  nameTxt.setText(callLog.getNumber());
                callTypeTxt.setText("Query"+"\n"+"call");}
            else {
                nameTxt.setText(callLog.getName());
                callTypeTxt.setText("Internal"+"\n"+"call");
            }
            //  itemBinding.textViewCallDuration.setText(Utils.formatSeconds(callLog.getDuration()));
            Date dateObj = new Date(callLog.getDate());
            SimpleDateFormat formatter = new SimpleDateFormat(" hh:mm a, dd-MM-yyyy");
            callNumberTxt.setText(callLog.getNumber());
            if(day.equals("Today")){
                callDateTxt.setText(formatter.format(dateObj));

            }
            if(day.equals("Yesterday")){
                callDateTxt.setText(formatter.format(dateObj)+", "+day);
            }
            if (day.equals("DayBeforeYesterday")){
                callDateTxt.setText(formatter.format(dateObj)+", Day Before Yesterday");

            }


        }
    }
}