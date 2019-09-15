package com.mnicholas.history.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.mnicholas.history.R;
import com.mnicholas.history.activities.ContentActivity;
import com.mnicholas.history.fragments.FavoritesFragment;
import com.mnicholas.history.helpers.AdHelper;
import com.mnicholas.history.models.HeaderItem;
import com.mnicholas.history.models.ListItem;
import com.mnicholas.history.models.MyItem;
import com.mnicholas.history.providers.JsonAssetsProvider;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "Debug";

    private Context mContext;

    private ArrayList<MyItem> items; // Текущее состояние
    private ArrayList<MyItem> itemsFull; // Полный список
    //Реклама
    // Избранное
    private DBHelper databaseHelper;
    private boolean isFav = false;

    public MainListAdapter(Context context, int listType) {
        mContext = context;
        databaseHelper = new DBHelper(mContext);
        try {
            items = JsonAssetsProvider.fillItemsList(mContext, listType);
            itemsFull = new ArrayList<MyItem>();
            itemsFull.addAll(items);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Конструктор для работы с избранным
    public MainListAdapter(Context context){
        mContext = context;
        isFav = true;
        databaseHelper = new DBHelper(mContext);
        items = setFavoritesItems();
        itemsFull = new ArrayList<MyItem>();
        itemsFull.addAll(items);
    }

    public ArrayList<MyItem> getItems(){return items;}
    public void setItems(ArrayList<MyItem> i){
        if(items != null)
            items.clear();
        else
            items = new ArrayList<>();
        items.addAll(i);

    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof ListItem)
            return 0;
        else
            return 1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater
                        .from(mContext)
                        .inflate(R.layout.main_list_item, viewGroup, false);
                return new ContentViewHolder(view);
            case 1:
                view = LayoutInflater
                        .from(mContext)
                        .inflate(R.layout.main_list_header, viewGroup, false);
                return new HeaderViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ViewHolder holder = null;
        switch (getItemViewType(i)) {
            case 0:
                holder = (ContentViewHolder) viewHolder;
                break;
            case 1:
                holder = (HeaderViewHolder) viewHolder;
                break;
        }
        holder.bind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }
    private Filter myFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<MyItem> filteredItems = new ArrayList<MyItem>();
            if(constraint == null || constraint.length() == 0) {
                filteredItems.addAll(itemsFull);
            }
            else{
                String format = constraint.toString().toLowerCase();
                for(MyItem item : itemsFull){
                    if(item.getIsHeader() == 0 && item.getTitle().toLowerCase().startsWith(format))
                        filteredItems.add(item);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredItems;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    public static abstract class ViewHolder extends RecyclerView.ViewHolder implements ViewHolderBehaviour {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class HeaderViewHolder extends ViewHolder {

        private TextView text;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.header_text);
        }

        @Override
        public void bind(int elementPosition) {
            HeaderItem item = (HeaderItem) items.get(elementPosition);
            text.setText(item.getTitle());
        }

    }

    class ContentViewHolder extends ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView itemImage;
        private ImageView favImage;
        private TextView itemTitle, itemInfo;
        private int position;
        private int id;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            favImage = (ImageView) itemView.findViewById(R.id.image_fav);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            itemInfo = (TextView) itemView.findViewById(R.id.item_info);
        }

        @Override
        public void bind(int elementPosition) {
            ListItem item = (ListItem) items.get(elementPosition);
            itemTitle.setText(item.getTitle());
            itemInfo.setText(item.getInfo());
            position = elementPosition;
            id = item.getId();
            switch (item.getType()) {
                case 0:
                    itemImage.setBackgroundColor(mContext.getResources().getColor(R.color.colorRulers));
                    break;
                case 1:
                    itemImage.setBackgroundColor(mContext.getResources().getColor(R.color.colorEvents));
                    break;
                case 2:
                    itemImage.setBackgroundColor(mContext.getResources().getColor(R.color.colorBattles));
                    break;
                case 3:
                    itemImage.setBackgroundColor(mContext.getResources().getColor(R.color.colorBuilds));
                    break;
            }
            if(!isFav) {
                if (checkFav(id))
                    favImage.setVisibility(View.VISIBLE);
                else
                    favImage.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClick(View v) {
            final Intent intent = new Intent(mContext, ContentActivity.class);
            intent.putExtra("URL", "file:///android_asset/index.html");
            intent.putExtra("TITLE", itemTitle.getText());
           if(AdHelper.getAd().isLoaded()) {
               AdHelper.getAd().setAdListener(new AdListener(){
                   @Override
                   public void onAdClosed() {
                       mContext.startActivity(intent);
                   }
               });
               AdHelper.getAd().show();
           }
           else
               mContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            if(!isFav) {
                if (addToFavs(id)) {
                    Toast.makeText(mContext, "Элемент под id " + id + " был добавлен в избранное", Toast.LENGTH_SHORT).show();
                    favImage.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(mContext, "Элемент под id " + id + " был удален из избранного", Toast.LENGTH_SHORT).show();

                    favImage.setVisibility(View.GONE);
                }
                return true;
            }
            else{
                addToFavs(id);
                for(MyItem i : items){
                    ListItem j = (ListItem) i;
                    if(j.getId() == id) {
                        notifyItemRemoved(items.indexOf(i));
                        items.remove(i);
                        if(!FavoritesFragment.haveFavs(mContext))
                            FavoritesFragment.switchViews(1);
                        break;
                    }
                }
                return true;
            }
        }

        /**
         * @param fav id ячейки (json)
         * @return false, если элемент удален, true - элемент добавлен
         */
        private boolean addToFavs(int fav) {
            SQLiteDatabase database = databaseHelper.getWritableDatabase();
            Cursor cursor = database.query(DBHelper.TABLE_FAVORITES_NAME, null, null, null, null, null, null);
            int favIdCollumIndex = cursor.getColumnIndex(DBHelper.KEY_FAVORITE);
            ContentValues values;
            if (cursor.moveToFirst()) {
                do {
                    int favId = cursor.getInt(favIdCollumIndex);
                    if (favId == fav) {
                        String format = String.format("%s = %d", DBHelper.KEY_FAVORITE, favId);
                        database.delete(DBHelper.TABLE_FAVORITES_NAME, format, null);
                        return false;
                    }
                }
                while (cursor.moveToNext());
            }
            values = new ContentValues();
            values.put(DBHelper.KEY_FAVORITE, fav);
            database.insert(DBHelper.TABLE_FAVORITES_NAME, null, values);
            return true;
        }

    }

    /**
     * @param fav id ячейки (json)
     * @return true, если существует, false - если нет
     */
    private boolean checkFav(int fav) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_FAVORITES_NAME, null, null, null, null, null, null);
        int favIdCollumIndex = cursor.getColumnIndex(DBHelper.KEY_FAVORITE);
        if (cursor.moveToFirst()) {
            do {
                int favId = cursor.getInt(favIdCollumIndex);
                if (favId == fav)
                    return true;
            }
            while (cursor.moveToNext());
        }
        return false;
    }

    private ArrayList<MyItem> setFavoritesItems(){
        ArrayList<MyItem> favsItems = new ArrayList<>();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_FAVORITES_NAME, null, null, null, null, null, null);
        int favIdCollumIndex = cursor.getColumnIndex(DBHelper.KEY_FAVORITE);
        if(cursor.moveToFirst()){
            do{
                try {
                    favsItems.add(JsonAssetsProvider.getItemById(mContext, cursor.getInt(favIdCollumIndex)));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            while (cursor.moveToNext());
        }
        return favsItems;
    }

    interface ViewHolderBehaviour {
        void bind(int elementPosition);
    }

}
