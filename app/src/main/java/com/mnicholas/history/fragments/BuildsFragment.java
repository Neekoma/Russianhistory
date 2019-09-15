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

public class BuildsFragment extends Fragment {
    private static BuildsFragment mInstance;
    private static final int LIST_TYPE = 3;
    private Context mContext;
    private RecyclerView buildsList;

    public BuildsFragment() {}

    public static BuildsFragment newInstance() throws IOException, JSONException {
        if(mInstance == null)
            mInstance = new BuildsFragment();
        return mInstance;
    }

    public RecyclerView getBuildsList(){return buildsList;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_builds, container, false);
        mContext = container.getContext();
        buildsList = view.findViewById(R.id.buildsRecyclerView);
        buildsList.setHasFixedSize(true);
        buildsList.setLayoutManager(new LinearLayoutManager(container.getContext()));
        buildsList.setAdapter(new MainListAdapter(mContext, LIST_TYPE));
        return view;
    }

}
