package com.mnicholas.history.models;

import android.os.Parcel;

public class HeaderItem extends MyItem {
    public HeaderItem(String title){
        super(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeInt(isHeader);
    }
}
