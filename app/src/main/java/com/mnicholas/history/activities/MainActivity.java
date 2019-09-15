package com.mnicholas.history.activities;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mnicholas.history.R;
import com.mnicholas.history.adapters.MainListAdapter;
import com.mnicholas.history.fragments.BattlesFragment;
import com.mnicholas.history.fragments.BuildsFragment;
import com.mnicholas.history.fragments.EventsFragment;
import com.mnicholas.history.fragments.FavoritesFragment;
import com.mnicholas.history.fragments.RulersFragment;
import com.mnicholas.history.helpers.AdHelper;
import com.mnicholas.history.helpers.BottomNavigationHelper;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener
{

    private static final String TAG = "MainActivity";

    private BottomNavigationViewEx bottomNavigationView;
    private Toolbar toolbar;

    private static final int FRAGMENT_RULERS = 0;
    private static final int FRAGMENT_EVENTS = 1;
    private static final int FRAGMENT_BATTLES = 2;
    private static final int FRAGMENT_BUILDS = 3;
    private static final int FRAGMENT_FAVORITES = 4;

    private static int FRAGMENT_CURRENT = 0;
    private Fragment fragment;
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationHelper.setupBottomNavigationView(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        try {
            loadFragment(); // init start fragment
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorGrey));
        if(AdHelper.getAd() == null)
            AdHelper.initAd(this);
    }

    private void loadFragment() throws IOException, JSONException {
        switch (FRAGMENT_CURRENT) {
            case FRAGMENT_RULERS:
                fragment = RulersFragment.newInstance();
                getSupportActionBar().setTitle(getResources().getString(R.string.Rulers));
                break;
            case FRAGMENT_EVENTS:
                fragment = EventsFragment.newInstance();
                getSupportActionBar().setTitle(getResources().getString(R.string.Events));
                break;
            case FRAGMENT_BATTLES:
                fragment = BattlesFragment.newInstance();
                getSupportActionBar().setTitle(getResources().getString(R.string.Battles));
                break;
            case FRAGMENT_BUILDS:
                fragment = BuildsFragment.newInstance();
                getSupportActionBar().setTitle(getResources().getString(R.string.Builds));
                break;
            case FRAGMENT_FAVORITES:
                fragment = FavoritesFragment.newInstance();
                getSupportActionBar().setTitle(getResources().getString(R.string.Favorites));
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(FRAGMENT_CURRENT);
        item.setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        try {
            switch (menuItem.getItemId()) {
                case R.id.bottom_nav_rulers:
                    FRAGMENT_CURRENT = FRAGMENT_RULERS;
                    loadFragment();
                    return true;
                case R.id.bottom_nav_events:
                    FRAGMENT_CURRENT = FRAGMENT_EVENTS;
                    loadFragment();
                    return true;
                case R.id.bottom_nav_battles:
                    FRAGMENT_CURRENT = FRAGMENT_BATTLES;
                    loadFragment();
                    return true;
                case R.id.bottom_nav_builds:
                    FRAGMENT_CURRENT = FRAGMENT_BUILDS;
                    loadFragment();
                    return true;
                case R.id.bottom_nav_favorites:
                    FRAGMENT_CURRENT = FRAGMENT_FAVORITES;
                    loadFragment();
                    return true;
            }
        }
        catch (IOException e){e.printStackTrace();}
        catch (JSONException e){e.printStackTrace();}
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        search = (SearchView) searchItem.getActionView();
        search.setImeOptions(EditorInfo.IME_ACTION_DONE);
        search.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Fragment currentFragment;
        RecyclerView list;
        MainListAdapter adapter;
        switch (FRAGMENT_CURRENT){
            case FRAGMENT_RULERS:
                currentFragment= (RulersFragment) fragment;
                list = ((RulersFragment) currentFragment).getRulersList();
                adapter = (MainListAdapter) list.getAdapter();
                adapter.getFilter().filter(s);
                return true;
            case FRAGMENT_EVENTS:
                currentFragment = (EventsFragment) fragment;
                list = ((EventsFragment) currentFragment).getEventsList();
                adapter = (MainListAdapter) list.getAdapter();
                adapter.getFilter().filter(s);
                return true;
            case FRAGMENT_BATTLES:
                currentFragment = (BattlesFragment) fragment;
                list = ((BattlesFragment) currentFragment).getBattlesList();
                adapter = (MainListAdapter) list.getAdapter();
                adapter.getFilter().filter(s);
                return true;
            case FRAGMENT_BUILDS:
                currentFragment = (BuildsFragment) fragment;
                list = ((BuildsFragment) currentFragment).getBuildsList();
                adapter = (MainListAdapter) list.getAdapter();
                adapter.getFilter().filter(s);
                return true;
            case FRAGMENT_FAVORITES:
                currentFragment = (FavoritesFragment) fragment;
                list = ((FavoritesFragment) currentFragment).getFavsList();
                adapter = (MainListAdapter) list.getAdapter();
                adapter.getFilter().filter(s);
                return true;
        }
        return false;
    }
}
