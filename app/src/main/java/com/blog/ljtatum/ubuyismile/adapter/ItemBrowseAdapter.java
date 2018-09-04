package com.blog.ljtatum.ubuyismile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.device.DeviceUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.constants.Durations;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.enums.Enum;
import com.blog.ljtatum.ubuyismile.interfaces.OnClickAdapterListener;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LJTat on 12/31/2017.
 */

public class ItemBrowseAdapter extends RecyclerView.Adapter<ItemBrowseAdapter.ViewHolder> {

    // custom callback
    private static OnClickAdapterListener mOnClickAdapterListener;
    private Context mContext;
    private List<ItemDatabaseModel> alItems;

    private boolean lockedAnimations;
    private int lastAnimatedItem = -1;

    /**
     * Constructor
     *
     * @param context Interface to global information about an application environment
     * @param items   List of items to display
     */
    public ItemBrowseAdapter(@NonNull Context context, @NonNull List<ItemDatabaseModel> items) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_browse, parent, false);
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) v.getLayoutParams();
        params.height = DeviceUtils.getDeviceWidthPx() / 2;
        params.width = DeviceUtils.getDeviceWidthPx() / 2;
        v.setLayoutParams(params);
        return new ItemBrowseAdapter.ViewHolder(v);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();

        // sale price percentage
        if (Utils.isItemOnSale(alItems.get(position))) {
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
            holder.tvPrice.setText(!FrameworkUtils.isStringEmpty(alItems.get(position).price) ?
                    mContext.getResources().getString(R.string.dollar_format, alItems.get(position).price) :
                    mContext.getResources().getString(R.string.free));
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
            // set text color default
            holder.tvLabel.setTextColor(ContextCompat.getColor(mContext, R.color.white));
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
                .into(holder.ivBg, new Callback() {
                    @Override
                    public void onSuccess() {
                        // animate item
                        animateItem(holder);
                        // do nothing
                    }

                    @Override
                    public void onError() {

                    }
                });
        if (lastAnimatedItem < position) {
            lastAnimatedItem = position;
        }

        // click listener
        holder.ivBg.setOnClickListener(new View.OnClickListener() {
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
     * Method is used to animate items
     *
     * @param holder A ViewHolder describes an item view and metadata about its place within the RecyclerView
     */
    private void animateItem(@NonNull final ViewHolder holder) {
        if (!lockedAnimations) {
            if (lastAnimatedItem == holder.getAdapterPosition()) {
                lockedAnimations = true;
            }

            // interpolator defines the rate of change of an animation
            Interpolator INTERPOLATOR = new DecelerateInterpolator();

            // animation delay
            long delay = Durations.ANIM_DURATION_SHORT_200 + holder.getAdapterPosition() * 30;
            holder.rlParent.setScaleX(0);
            holder.rlParent.setScaleY(0);
            holder.rlParent.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setDuration(Durations.ANIM_DURATION_SHORT_200)
                    .setInterpolator(INTERPOLATOR)
                    .setStartDelay(delay)
                    .start();
        }
    }

    /**
     * Method is used to update trip history data
     *
     * @param items List of completed status trips. Populated from model class
     *              {@link com.blog.ljtatum.ubuyismile.model.ItemModel}
     */
    public void updateData(@NonNull List<ItemDatabaseModel> items) {
        if (!items.isEmpty()) {
            alItems = items;
            notifyDataSetChanged();
        }
    }

    /**
     * View holder class
     * <p>A ViewHolder describes an item view and metadata about its place within the RecyclerView</p>
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout rlParent;
        private final LinearLayout llLabelWrapper;
        private final TextView tvSalePerc, tvLabel, tvTitle, tvPrice, tvScratchPrice;
        private final ImageView ivBg, ivLabelIcon;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            rlParent = itemView.findViewById(R.id.rl_parent);
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
