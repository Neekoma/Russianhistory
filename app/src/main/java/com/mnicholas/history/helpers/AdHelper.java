package com.mnicholas.history.helpers;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.mnicholas.history.R;

public final class AdHelper {
  private static AdRequest mAdRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
  private static InterstitialAd mAd = null;
  public static int impressions = 0;
  public static int nextAd = 1;

  public static void initAd(Context context){
      mAd = new InterstitialAd(context);
      mAd.setAdUnitId(context.getString(R.string.adUnitId));
      mAd.loadAd(mAdRequest);
  }
  public static InterstitialAd getAd(){return mAd;}



}

