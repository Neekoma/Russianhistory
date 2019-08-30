package com.mnicholas.history.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mnicholas.history.R;
import com.mnicholas.history.adapters.DBHelper;
import com.mnicholas.history.adapters.MainListAdapter;

public class FavoritesFragment extends Fragment {

    private static View staticview;
    private RecyclerView favsList;
    private Context mContext;
    private TextView emptyListTip;

    public FavoritesFragment() {
    }

    public RecyclerView getFavsList(){return favsList;}

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_favorites, container, false);
        staticview = view;
        mContext = view.getContext();
        favsList = view.findViewById(R.id.favsRecyclerView);
        emptyListTip = view.findViewById(R.id.tv_favs_empty);
        if(haveFavs(mContext))
            switchViews( 0);
        else
            switchViews(1);
        favsList.setHasFixedSize(true);
        favsList.setLayoutManager(new LinearLayoutManager(mContext));
        favsList.setAdapter(new MainListAdapter(mContext));
        return view;
    }

    public static boolean haveFavs(Context context){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_FAVORITES_NAME, null, null, null, null, null, null);
        if(cursor.moveToFirst())
            return true;
        else
            return false;
    }
    public static void switchViews(int a){
        RecyclerView list = staticview.findViewById(R.id.favsRecyclerView);
        TextView text = staticview.findViewById(R.id.tv_favs_empty);
        if(a == 0){
            list.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);
        }
        else if (a == 1){
            list.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);
        }
    }
}
