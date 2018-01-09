package com.blog.ljtatum.ubuyismile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.enums.Enum;
import com.blog.ljtatum.ubuyismile.model.ChableeModel;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by LJTat on 12/31/2017.
 */

public class ChableeItemAdapter extends RecyclerView.Adapter<ChableeItemAdapter.ViewHolder> {

    private final Context mContext;
    private final int DEFAULT_IMAGE_SIZE = 500;
    private ArrayList<ChableeModel> alItems;

    /**
     * Constructor
     *
     * @param context Interface to global information about an application environment
     * @param alItems List of items to display
     */
    public ChableeItemAdapter(Context context, ArrayList<ChableeModel> alItems) {
        mContext = context;
        this.alItems = alItems;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_a, parent, false);
        return new ChableeItemAdapter.ViewHolder(v);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();

        // sale price percentage
        if ((!FrameworkUtils.isStringEmpty(alItems.get(position).salePrice) &&
                !FrameworkUtils.isStringEmpty(alItems.get(position).price)) &&
                (Utils.getDollarValue(alItems.get(position).salePrice) > 0) &&
                (Utils.getDollarValue(alItems.get(position).salePrice) <
                        Utils.getDollarValue(alItems.get(position).price))) {
            // set visibility
            FrameworkUtils.setViewVisible(holder.tvSalePerc);
            // set percent sale value
            holder.tvSalePerc.setText(mContext.getResources().getString(R.string.percent_sale,
                    String.valueOf(Utils.calculatePercSale(Utils.getDollarValue(alItems.get(position).price),
                            Utils.getDollarValue(alItems.get(position).salePrice)))));
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
        holder.tvPrice.setText(mContext.getResources().getString(R.string.dollar_format, alItems.get(position).price));
        // set image
        Picasso.with(mContext).load(alItems.get(position).imageUrl1)
                .placeholder(R.drawable.no_image_available)
                .resize(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE)
                .into(holder.ivBg);

    }

    @Override
    public int getItemCount() {
        return alItems.size();
    }

    /**
     * Method is used to update trip history data
     *
     * @param alItems List of completed status trips. Populated from model class
     *                {@link com.blog.ljtatum.ubuyismile.model.ChableeModel}
     */
    public void updateData(@NonNull ArrayList<ChableeModel> alItems) {
        if (alItems.size() > 0 && !alItems.isEmpty()) {
            this.alItems = alItems;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout llLabelWrapper;
        private final TextView tvSalePerc, tvLabel, tvTitle, tvPrice;
        private final ImageView ivBg, ivLabelIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llLabelWrapper = itemView.findViewById(R.id.ll_label_wrapper);
            tvSalePerc = itemView.findViewById(R.id.tv_sale_perc);
            tvLabel = itemView.findViewById(R.id.tv_label);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivBg = itemView.findViewById(R.id.iv_bg);
            ivLabelIcon = itemView.findViewById(R.id.iv_label_icon);
        }


    }
}
