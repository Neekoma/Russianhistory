package com.mnicholas.history.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnicholas.history.R;
import com.mnicholas.history.models.ListItem;

import java.util.ArrayList;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.FavoriteViewHolder> {

    private Context mContext;
    private ArrayList<ListItem> items;

    public FavoritesListAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.main_list_item, viewGroup, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder favoriteViewHolder, int position) {
        favoriteViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class FavoriteViewHolder extends MainListAdapter.ViewHolder{
        private TextView title;
        private TextView info;
        private ImageView itemImage;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            info = (TextView) itemView.findViewById(R.id.item_info);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
        }

        @Override
        public void bind(int elementPosition) {

        }
    }
}
