package com.blog.ljtatum.ubuyismile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blog.ljtatum.ubuyismile.R;

import java.util.ArrayList;

/**
 * Created by LJTat on 12/31/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final Context mContext;
    private ArrayList<?> alItems;


    /**
     * Constructor
     * @param context Interface to global information about an application environment
     * @param alItems List of items to display
     */
    public ItemAdapter(Context context, ArrayList<?> alItems) {
        mContext = context;
        this.alItems = alItems;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_a, parent, false);
        return new ItemAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();

    }

    @Override
    public int getItemCount() {
        return alItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        }


    }
}
