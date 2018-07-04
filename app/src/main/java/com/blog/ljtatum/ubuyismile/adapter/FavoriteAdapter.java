package com.blog.ljtatum.ubuyismile.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.framework.gui.CircleImageView;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.enums.Enum;
import com.blog.ljtatum.ubuyismile.interfaces.OnFavoriteRemoveListener;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LJTat on 12/31/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context mContext;
    private List<ItemDatabaseModel> alItems;

    // custom callback
    private static OnFavoriteRemoveListener mOnFavoriteRemoveListener;

    /**
     * Method is used to set callback for when to remove item from favorite list
     *
     * @param listener Callback for when to remove item from favorite list
     */
    public static void onFavoriteRemoveListener(OnFavoriteRemoveListener listener) {
        mOnFavoriteRemoveListener = listener;
    }

    /**
     * Constructor
     *
     * @param context Interface to global information about an application environment
     * @param items   List of items to display
     */
    public FavoriteAdapter(@NonNull Context context, @NonNull List<ItemDatabaseModel> items) {
        mContext = context;
        alItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();

        // sale price percentage
        if (Utils.isItemOnSale(alItems.get(position))) {
            // set visibility
            FrameworkUtils.setViewVisible(holder.tvScratchPrice);
            // set price as sale price and price
            holder.tvPrice.setText(mContext.getResources().getString(
                    R.string.dollar_format, alItems.get(position).salePrice));
            holder.tvScratchPrice.setText(mContext.getResources().getString(
                    R.string.dollar_format, alItems.get(position).price));
            holder.tvScratchPrice.setPaintFlags(holder.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            // set visibility
            FrameworkUtils.setViewInvisible(holder.tvScratchPrice);
            holder.tvPrice.setText(mContext.getResources().getString(
                    R.string.dollar_format, alItems.get(position).price));
        }

        // label
        if (alItems.get(position).label.equalsIgnoreCase(Enum.ItemLabel.NONE.toString())) {
            FrameworkUtils.setViewGone(holder.llLabelWrapper);
        } else {
            // set visibility
            FrameworkUtils.setViewVisible(holder.llLabelWrapper);
            FrameworkUtils.setViewGone(holder.ivLabelIcon);
            // set value
            holder.tvLabel.setText(alItems.get(position).label);
            if (alItems.get(position).label.equalsIgnoreCase(Enum.ItemLabel.SALE.toString())) {
                // set visibility
                FrameworkUtils.setViewVisible(holder.ivLabelIcon);
                // set icon
                holder.ivLabelIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.label_sale_icon));
                // set text color
                holder.tvLabel.setTextColor(ContextCompat.getColor(mContext, R.color.material_green_300_color_code));
            } else if (alItems.get(position).label.equalsIgnoreCase(Enum.ItemLabel.LEONARD_FAVORITE.toString())) {
                // set visibility
                FrameworkUtils.setViewVisible(holder.ivLabelIcon);
                // set icon
                holder.ivLabelIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.label_leonard_favorite_icon));
                // set text color
                holder.tvLabel.setTextColor(ContextCompat.getColor(mContext, R.color.material_light_blue_300_color_code));
            } else if (alItems.get(position).label.equalsIgnoreCase(Enum.ItemLabel.SUPER_HOT.toString())) {
                // set visibility
                FrameworkUtils.setViewVisible(holder.ivLabelIcon);
                // set icon
                holder.ivLabelIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.label_super_hot_icon));
                // set text color
                holder.tvLabel.setTextColor(ContextCompat.getColor(mContext, R.color.material_red_300_color_code));
            }
        }

        // set values
        holder.tvTitle.setText(alItems.get(position).title);
        // set image
        Picasso.with(mContext).load(ItemModel.getFormattedImageUrl(alItems.get(position).imageUrl1))
                .placeholder(R.drawable.no_image_available)
                .resize(Constants.DEFAULT_IMAGE_SIZE_500, Constants.DEFAULT_IMAGE_SIZE_500)
                .into(holder.civItemIcon);

        // click listener
        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FrameworkUtils.checkIfNull(mOnFavoriteRemoveListener)) {
                    // set listener
                    mOnFavoriteRemoveListener.onFavoriteRemove(alItems.get(index));
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
     *              {@link ItemModel}
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
        private final LinearLayout llLabelWrapper;
        private final TextView tvLabel, tvTitle, tvRemove, tvPrice, tvScratchPrice;
        private final ImageView ivLabelIcon;
        private final CircleImageView civItemIcon;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            llLabelWrapper = itemView.findViewById(R.id.ll_label_wrapper);
            tvLabel = itemView.findViewById(R.id.tv_label);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRemove = itemView.findViewById(R.id.tv_remove);
            tvScratchPrice = itemView.findViewById(R.id.tv_scratch_price);
            tvPrice = itemView.findViewById(R.id.tv_price);
            civItemIcon = itemView.findViewById(R.id.civ_item_icon);
            ivLabelIcon = itemView.findViewById(R.id.iv_label_icon);
        }
    }
}
