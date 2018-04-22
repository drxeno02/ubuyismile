package com.blog.ljtatum.ubuyismile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.enums.Enum;
import com.blog.ljtatum.ubuyismile.interfaces.OnClickAdapterListener;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by LJTat on 12/31/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final Context mContext;
    private ArrayList<ItemModel> alItems;
    private Enum.ItemType mItemType;

    // custom callback
    private static OnClickAdapterListener mOnClickAdapterListener;

    /**
     * Method is used to set callback for when item is clicked
     * @param listener Callback for when item is clicked
     */
    public static void onClickAdapterListener(OnClickAdapterListener listener) {
        mOnClickAdapterListener = listener;
    }

    /**
     * Constructor
     *
     * @param context Interface to global information about an application environment
     * @param alItems List of items to display
     */
    public ItemAdapter(Context context, ArrayList<ItemModel> alItems, Enum.ItemType itemType) {
        mItemType = itemType;
        mContext = context;
        this.alItems = alItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (mItemType.equals(Enum.ItemType.CHABLEE)) {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_a, parent, false);
        } else {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_b, parent, false);
        }
        return new ItemAdapter.ViewHolder(v);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();

        // set background
        if (mItemType.equals(Enum.ItemType.CHABLEE)) {
            if (position % 2 == 0) {
                holder.llBgWrapper.setBackgroundColor(ContextCompat.getColor(mContext, R.color.material_pink_100_color_code));
            } else {
                holder.llBgWrapper.setBackgroundColor(ContextCompat.getColor(mContext, R.color.material_pink_a100_color_code));
            }
        }

        // sale price percentage
        if ((!FrameworkUtils.isStringEmpty(alItems.get(position).salePrice) &&
                !FrameworkUtils.isStringEmpty(alItems.get(position).price)) &&
                (Utils.getDollarValue(alItems.get(position).salePrice) > 0) &&
                (Utils.getDollarValue(alItems.get(position).salePrice) <
                        Utils.getDollarValue(alItems.get(position).price))) {
            // set visibility
            FrameworkUtils.setViewVisible(holder.tvSalePerc, holder.tvScratchPrice);
            // set percent sale value
            holder.tvSalePerc.setText(mContext.getResources().getString(R.string.percent_sale,
                    String.valueOf(Utils.calculatePercSale(Utils.getDollarValue(alItems.get(position).price),
                            Utils.getDollarValue(alItems.get(position).salePrice)))));
            // set price as sale price and price
            holder.tvPrice.setText(mContext.getResources().getString(
                    R.string.dollar_format, alItems.get(position).salePrice));
            holder.tvScratchPrice.setText(mContext.getResources().getString(
                    R.string.dollar_format, alItems.get(position).price));
            holder.tvScratchPrice.setPaintFlags(holder.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            // set visibility
            FrameworkUtils.setViewGone(holder.tvSalePerc);
            FrameworkUtils.setViewInvisible(holder.tvScratchPrice);
            holder.tvPrice.setText(mContext.getResources().getString(
                    R.string.dollar_format, alItems.get(position).price));
        }

        // label
        if (!alItems.get(position).label.equalsIgnoreCase(Enum.ItemLabel.NONE.toString())) {
            // set visibility
            FrameworkUtils.setViewVisible(holder.llLabelWrapper);
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
                .into(holder.ivBg);
        // click listener
        holder.ivBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FrameworkUtils.checkIfNull(mOnClickAdapterListener)) {
                    // set listener
                    mOnClickAdapterListener.onClick(index);
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
     * @param alItems List of completed status trips. Populated from model class
     *                {@link com.blog.ljtatum.ubuyismile.model.ItemModel}
     */
    public void updateData(@NonNull ArrayList<ItemModel> alItems) {
        if (alItems.size() > 0 && !alItems.isEmpty()) {
            this.alItems = alItems;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout llLabelWrapper, llBgWrapper;
        private final TextView tvSalePerc, tvLabel, tvTitle, tvPrice, tvScratchPrice;
        private final ImageView ivBg, ivLabelIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llBgWrapper = itemView.findViewById(R.id.ll_bg_wrapper);
            llLabelWrapper = itemView.findViewById(R.id.ll_label_wrapper);
            tvSalePerc = itemView.findViewById(R.id.tv_sale_perc);
            tvLabel = itemView.findViewById(R.id.tv_label);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvScratchPrice = itemView.findViewById(R.id.tv_scratch_price);
            ivBg = itemView.findViewById(R.id.iv_bg);
            ivLabelIcon = itemView.findViewById(R.id.iv_label_icon);
        }


    }
}
