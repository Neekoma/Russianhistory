package com.mnicholas.history.models;

import android.content.Context;
import android.os.Parcel;

public class ListItem extends MyItem{
    private String mInfo;
    private int mType;
    private int mId;
    private String mHtmlDoc;

    public ListItem(String title){
        super(title);
    }

    public void setHtmlDoc(String filename){mHtmlDoc = filename;}
    public String getHtmlDoc(){return mHtmlDoc;}
    public void setType(int type){mType = type;}
    public int getType(){return mType;}
    public void setId(int id){mId = id;}
    public int getId(){return mId;}
    public void setInfo(String info){ mInfo = info; }
    public String getInfo(){return mInfo;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mInfo);
        dest.writeInt(mId);
        dest.writeInt(isHeader);
        dest.writeString(mHtmlDoc);
        dest.writeInt(mType);
    }
}
