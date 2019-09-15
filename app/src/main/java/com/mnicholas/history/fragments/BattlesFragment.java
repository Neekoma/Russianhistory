package com.mnicholas.history.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnicholas.history.R;
import com.mnicholas.history.adapters.MainListAdapter;

import org.json.JSONException;

import java.io.IOException;

public class BattlesFragment extends Fragment {
    private static BattlesFragment mInstance;
    private final static int LIST_TYPE = 2;
    private Context mContext;
    private RecyclerView battlesList;

    public BattlesFragment() {
    }

    public static BattlesFragment newInstance() throws IOException, JSONException {
        if(mInstance == null)
            mInstance = new BattlesFragment();
        return mInstance;
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
