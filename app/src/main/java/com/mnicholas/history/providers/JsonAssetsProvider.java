package com.mnicholas.history.providers;

import android.content.Context;

import com.mnicholas.history.models.HeaderItem;
import com.mnicholas.history.models.ListItem;
import com.mnicholas.history.models.MyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JsonAssetsProvider {

    private static final String JSON_FILENAME = "items.json";
    private static final String ID_PARAM = "id";
    private static final String TYPE_PARAM = "type";
    private static final String IS_HEADER_PARAM = "is_header";
    private static final String TITLE_PARAM = "title";
    private static final String INFO_PARAM = "info";
    private static final String HTML_DOC_PARAM = "html_doc";

    public static int getItemCount(Context context) throws JSONException, IOException {
        JSONObject json = new JSONObject(getJsonString(context));
        JSONArray array = json.getJSONArray("items");
        return array.length();
    }

    /**
     *
     * @param context
     * @param type 0 = правители 1 = события 2 = сражения 3 = строения
     * @return возвращает заполненный список данных MyItem из items.json
     * @throws JSONException
     * @throws IOException
     */
    public static ArrayList<MyItem> fillItemsList(Context context, int type) throws JSONException, IOException{
        ArrayList<MyItem> items = new ArrayList<>();
        JSONObject json = new JSONObject(getJsonString(context));
        JSONArray array = json.getJSONArray("items");
        for(int i = 0; i < getItemCount(context); i++){
            JSONObject object = array.getJSONObject(i);
            if(object.getInt(TYPE_PARAM) == type){
                MyItem item = null;
                switch (object.getInt(IS_HEADER_PARAM)){
                    case 0:
                        item = new ListItem(object.getString(TITLE_PARAM));
                        ((ListItem) item).setType(object.getInt(TYPE_PARAM));
                        item.setIsHeader(0);
                        ((ListItem) item).setInfo(object.getString(INFO_PARAM));
                        ((ListItem) item).setId(object.getInt(ID_PARAM));
                        break;
                    case 1:
                        item = new HeaderItem(object.getString(TITLE_PARAM));
                        item.setIsHeader(1);
                        break;
                }
                items.add(item);
            }
        }
        return items;
    }

    public static MyItem getItemById(Context context, int id) throws IOException, JSONException{
        JSONObject json = new JSONObject(getJsonString(context));
        JSONArray array = json.getJSONArray("items");
        for(int i = 0; i < getItemCount(context); i++){
            JSONObject object = array.getJSONObject(i);
            if(object.getInt(IS_HEADER_PARAM) == 0 && object.getInt(ID_PARAM) == id){
                ListItem item = new ListItem(object.getString(TITLE_PARAM));
                item.setType(object.getInt(TYPE_PARAM));
                item.setInfo(object.getString(INFO_PARAM));
                item.setId(object.getInt(ID_PARAM));
                return item;
            }
        }
        return null;
    }

    private static String getJsonString(Context context) throws JSONException, IOException {
        InputStream input = context.getAssets().open(JSON_FILENAME);
        int size = input.available();
        byte[] buffer = new byte[size];
        input.read(buffer);
        String result = new String(buffer, "UTF-8");
        return result;
    }

}
