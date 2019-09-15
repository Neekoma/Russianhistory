package com.mnicholas.history.models;

import android.os.Parcelable;

public abstract class MyItem implements Parcelable {
    protected String mTitle;
    protected int isHeader = 0;

    public MyItem(String title){
        mTitle = title;
    }

    public void setTitle(String arg) {
        mTitle = mTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(int isHeader) {
        this.isHeader = isHeader;
    }
}
