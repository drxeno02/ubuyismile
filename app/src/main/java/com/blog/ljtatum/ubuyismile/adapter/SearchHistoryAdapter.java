package com.blog.ljtatum.ubuyismile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.enums.Enum;
import com.blog.ljtatum.ubuyismile.interfaces.OnClickAdapterListener;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {

    private static final String COLOR_CODE_AMAZON = "[A]";
    private static final String COLOR_CODE_CHABLEE = "[C]";
    // custom callback
    private static OnClickAdapterListener mOnClickAdapterListener;
    private Context mContext;
    private List<ItemDatabaseModel> alItems;

    /**
     * Constructor
     *
     * @param context Interface to global information about an application environment
     * @param items   List of items to display
     */
    public SearchHistoryAdapter(@NonNull Context context, @NonNull List<ItemDatabaseModel> items) {
        mContext = context;
        alItems = items;
    }

    /**
     * Method is used to set callback for when item is clicked
     *
     * @param listener Callback for when item is clicked
     */
    public static void onClickAdapterListener(OnClickAdapterListener listener) {
        mOnClickAdapterListener = listener;
    }

    @Override
    public SearchHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false);
        return new SearchHistoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchHistoryAdapter.ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();

        // set favorite
        if (alItems.get(position).isFavorite) {
            holder.ivItemFavoriteIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.favorite_icon_on));
        } else {
            holder.ivItemFavoriteIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.favorite_icon_off));
        }

        // set color code
        if (alItems.get(position).itemType.equalsIgnoreCase(Enum.ItemType.AMAZON.toString())) {
            holder.tvColorCode.setText(COLOR_CODE_AMAZON);
            holder.tvColorCode.setTextColor(ContextCompat.getColor(mContext, R.color.material_orange_100_color_code));
        } else if (alItems.get(position).itemType.equalsIgnoreCase(Enum.ItemType.CHABLEE.toString())) {
            holder.tvColorCode.setText(COLOR_CODE_CHABLEE);
            holder.tvColorCode.setTextColor(ContextCompat.getColor(mContext, R.color.material_pink_100_color_code));
        }

        // set category icon
        if (alItems.get(position).itemType.equalsIgnoreCase(Enum.ItemType.AMAZON.toString())) {
            // TODO use Amazon official icon
        } else if (alItems.get(position).itemType.equalsIgnoreCase(Enum.ItemType.CHABLEE.toString())) {
            if (alItems.get(position).category.equalsIgnoreCase(
                    com.app.amazon.framework.enums.Enum.ItemCategoryChablee.CROWNS.toString())) {
                // set icon
                holder.ivItemCategoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.crowns));
            } else if (alItems.get(position).category.equalsIgnoreCase(
                    com.app.amazon.framework.enums.Enum.ItemCategoryChablee.RINGS.toString())) {
                // set icon
                holder.ivItemCategoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.rings));
            } else if (alItems.get(position).category.equalsIgnoreCase(
                    com.app.amazon.framework.enums.Enum.ItemCategoryChablee.NECKLACES.toString())) {
                // set icon
                holder.ivItemCategoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.necklaces));
            } else if (alItems.get(position).category.equalsIgnoreCase(
                    com.app.amazon.framework.enums.Enum.ItemCategoryChablee.ROCKS.toString())) {
                // set icon
                holder.ivItemCategoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.rocks));
            }
        }

        // set values
        holder.tvTitle.setText(alItems.get(position).title);
        holder.tvTimestampSearch.setText(FrameworkUtils.parseDate(alItems.get(position).timestampSearch));

        // click listener
        holder.rlParentWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FrameworkUtils.checkIfNull(mOnClickAdapterListener)) {
                    // set listener
                    mOnClickAdapterListener.onClick(alItems.get(index));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return alItems.size();
    }

    /**
     * Method is used to update trip history data
     *
     * @param items List of completed status trips. Populated from model class
     *              {@link com.blog.ljtatum.ubuyismile.model.ItemModel}
     */
    public void updateData(@NonNull List<ItemDatabaseModel> items) {
        if (items.size() > 0) {
            alItems = items;
            notifyDataSetChanged();
        }
    }

    /**
     * View holder class
     * <p>A ViewHolder describes an item view and metadata about its place within the RecyclerView</p>
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout rlParentWrapper;
        private final ImageView ivItemCategoryIcon, ivItemFavoriteIcon;
        private final TextView tvColorCode, tvTitle, tvTimestampSearch;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            rlParentWrapper = itemView.findViewById(R.id.rl_parent_wrapper);
            ivItemCategoryIcon = itemView.findViewById(R.id.iv_item_category_icon);
            ivItemFavoriteIcon = itemView.findViewById(R.id.iv_item_favorite_icon);
            tvColorCode = itemView.findViewById(R.id.tv_color_code);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTimestampSearch = itemView.findViewById(R.id.tv_timestamp_search);
        }
    }
}
