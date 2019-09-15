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

public class EventsFragment extends Fragment {
    private static EventsFragment mInstance;
    private static final int LIST_TYPE = 1;
    private Context mContext;
    private RecyclerView eventsList;

    public EventsFragment(){

    }

    public static EventsFragment newInstance() throws IOException, JSONException {
        if(mInstance == null)
            mInstance = new EventsFragment();
        return mInstance;
    }

    public RecyclerView getEventsList(){return eventsList;}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        mContext = container.getContext();
        eventsList = view.findViewById(R.id.eventsRecyclerView);
        eventsList.setHasFixedSize(true);
        eventsList.setLayoutManager(new LinearLayoutManager(container.getContext()));
        eventsList.setAdapter(new MainListAdapter(mContext, LIST_TYPE));
        return view;
    }
}
