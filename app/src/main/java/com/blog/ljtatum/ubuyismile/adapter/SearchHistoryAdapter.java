package com.blog.ljtatum.ubuyismile.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {



    @Override
    public SearchHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SearchHistoryAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * View holder class
     * <p>A ViewHolder describes an item view and metadata about its place within the RecyclerView</p>
     */
    class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
