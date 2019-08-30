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
import com.mnicholas.history.models.MyItem;
import com.mnicholas.history.providers.JsonAssetsProvider;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class BattlesFragment extends Fragment {
    private final static int LIST_TYPE = 2;
    private Context mContext;
    private RecyclerView battlesList;

    public BattlesFragment() {
    }

    public static BattlesFragment newInstance() throws IOException, JSONException {
        BattlesFragment fragment = new BattlesFragment();
        return fragment;
    }

    public RecyclerView getBattlesList(){
        return battlesList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_battles, container, false);
        mContext = container.getContext();
        battlesList = view.findViewById(R.id.battlesRecyclerView);
        battlesList.setHasFixedSize(true);
        battlesList.setLayoutManager(new LinearLayoutManager(container.getContext()));
        battlesList.setAdapter(new MainListAdapter(mContext, LIST_TYPE));
        return view;
    }


}
