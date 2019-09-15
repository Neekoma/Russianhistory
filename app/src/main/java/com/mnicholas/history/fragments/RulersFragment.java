package com.mnicholas.history.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mnicholas.history.R;
import com.mnicholas.history.adapters.MainListAdapter;

import org.json.JSONException;

import java.io.IOException;

public class RulersFragment extends Fragment{

    private static RulersFragment mInstance;
    private final static int LIST_TYPE = 0;
    private Context mContext;
    private RecyclerView rulersList;
    private MainListAdapter mAdapter;
    private LinearLayoutManager layoutManager;


    public RulersFragment() {
    }

    public static RulersFragment newInstance() throws IOException, JSONException {
        if(mInstance == null)
            mInstance = new RulersFragment();
        return mInstance;
    }

    public RecyclerView getRulersList(){return rulersList;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rulers, container, false);
        mContext = container.getContext();
        rulersList = view.findViewById(R.id.rulersRecyclerView);
        rulersList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        rulersList.setLayoutManager(layoutManager);
        mAdapter = new MainListAdapter(mContext, LIST_TYPE);
        rulersList.setAdapter(mAdapter);
        return view;
    }

}

