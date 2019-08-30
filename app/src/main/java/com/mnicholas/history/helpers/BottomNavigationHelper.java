package com.mnicholas.history.helpers;

import android.util.Log;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationHelper {
    private static final String TAG = "BottomNavigationHelper";
    public static void setupBottomNavigationView(BottomNavigationViewEx view){
        view.enableShiftingMode(false);
        view.enableAnimation(false);
        view.enableItemShiftingMode(false);
        view.setTextSize(9);
    }
}
