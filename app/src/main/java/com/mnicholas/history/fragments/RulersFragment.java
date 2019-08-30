package com.mnicholas.history.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mnicholas.history.R;
import com.mnicholas.history.adapters.MainListAdapter;

import org.json.JSONException;

import java.io.IOException;

public class RulersFragment extends Fragment{

    private final static int LIST_TYPE = 0;
    private Context mContext;
    private RecyclerView rulersList;

    public RulersFragment() {
    }

    public static RulersFragment newInstance() throws IOException, JSONException {
        RulersFragment fragment = new RulersFragment();
        return fragment;
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
        rulersList.setLayoutManager(new LinearLayoutManager(mContext));
        rulersList.setAdapter(new MainListAdapter(mContext, LIST_TYPE));
        return view;
    }

}

