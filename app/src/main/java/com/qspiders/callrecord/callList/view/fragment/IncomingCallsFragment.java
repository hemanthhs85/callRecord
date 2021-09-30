package com.qspiders.callrecord.callList.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qspiders.callrecord.R;
import com.qspiders.callrecord.callList.view.adapter.CallLogAdapter;
import com.qspiders.callrecord.callList.view.adapter.CallLogUtils;


public class IncomingCallsFragment extends Fragment {
    CallLogAdapter adapter;
    CallLogAdapter.OnCallLogItemClickListener onItemClickListener;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.call_log_fragment, container, false);
// Inflate the layout for this fragment
        initComponents(view);

        return view;
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initComponents(View view){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CallLogAdapter(getContext(),"Today");
        recyclerView.setAdapter(adapter);
        loadData();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void loadData(){
        CallLogUtils callLogUtils = CallLogUtils.getInstance(getContext());
        adapter.addAllCallLog(callLogUtils.readCallLogs());
        adapter.notifyDataSetChanged();
    }
}
