package com.blog.ljtatum.ubuyismile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.amazon.framework.enums.Enum;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.logger.Logger;
import com.blog.ljtatum.ubuyismile.model.ChableeData;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by LJTat on 4/3/2018.
 */

public class DetailFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private View mRootView;

    private TextView tvFragmentHeader, tvSalePerc, tvLabel, tvTitle, tvPrice, tvScratchPrice, tvDesc;
    private ImageView ivBg, ivLabelIcon;
    private LinearLayout llLabelWrapper;
    private RelativeLayout rlParent;

    private int mPos;
    private String mCategory;
    private ArrayList<ItemModel> alItems;
    private com.blog.ljtatum.ubuyismile.enums.Enum.ItemType mItemType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        // instantiate views
        initializeViews();
        initializeListeners();
        initializeHandlers();
        // retrieve bundle info
        getBundle();

        return mRootView;
    }

    /**
     * Method is used to instantiate views
     */
    private void initializeViews() {
        mContext = getActivity();
        alItems = new ArrayList<>();

        llLabelWrapper = mRootView.findViewById(R.id.ll_label_wrapper);
        rlParent = mRootView.findViewById(R.id.rl_parent);
        tvFragmentHeader = mRootView.findViewById(R.id.tv_fragment_header);
//        tvSalePerc = mRootView.findViewById(R.id.tv_sale_perc);
        tvLabel = mRootView.findViewById(R.id.tv_label);
        tvTitle = mRootView.findViewById(R.id.tv_title);
//        tvScratchPrice = mRootView.findViewById(R.id.tv_scratch_price);
        tvDesc = mRootView.findViewById(R.id.tv_desc);
        ivBg = mRootView.findViewById(R.id.iv_bg);
        ivLabelIcon = mRootView.findViewById(R.id.iv_label_icon);
    }

    /**
     * Method is used to initialize listeners and callbacks
     */
    private void initializeListeners() {

    }

    /**
     * Method is used to set click listeners
     */
    private void initializeHandlers() {
        tvFragmentHeader.setOnClickListener(this);


    }

    /**
     * Method is used to retrieve bundle information
     */
    private void getBundle() {
        Bundle args = getArguments();
        if (!FrameworkUtils.checkIfNull(args)) {
            // set position
            mPos = args.getInt(Constants.KEY_ITEM_POS);
            Logger.e("TEST", "<DetailFragment> pos= " + mPos);
            // set background
            if (args.getString(Constants.KEY_ITEM_TYPE, "").equalsIgnoreCase(
                    com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.CHABLEE.toString())) {
                rlParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.material_pink_100_color_code));
            } else {
                rlParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.material_light_blue_400_color_code));
            }

            // set category
            mCategory = args.getString(Constants.KEY_CATEGORY, "");
            if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.CROWNS.toString())) {
                alItems = ChableeData.getCrowns();
                // set fragment header
                tvFragmentHeader.setText(getResources().getString(R.string.menu_crowns));
            } else if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.RINGS.toString())) {
                alItems = ChableeData.getRings();
                // set fragment header
                tvFragmentHeader.setText(getResources().getString(R.string.menu_rings));
            } else if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.NECKLACES.toString())) {
                alItems = ChableeData.getNecklaces();
                // set fragment header
                tvFragmentHeader.setText(getResources().getString(R.string.menu_necklaces));
            } else if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.ROCKS.toString())) {
                alItems = ChableeData.getRocks();
                // set fragment header
                tvFragmentHeader.setText(getResources().getString(R.string.menu_rocks));
            }

            // populate details
            populateItemDetails();
        }
    }

    private void populateItemDetails() {
        // sale price percentage
//        if ((!FrameworkUtils.isStringEmpty(alItems.get(mPos).salePrice) &&
//                !FrameworkUtils.isStringEmpty(alItems.get(mPos).price)) &&
//                (Utils.getDollarValue(alItems.get(mPos).salePrice) > 0) &&
//                (Utils.getDollarValue(alItems.get(mPos).salePrice) <
//                        Utils.getDollarValue(alItems.get(mPos).price))) {
//            // set visibility
//            FrameworkUtils.setViewVisible(holder.tvSalePerc, holder.tvScratchPrice);
//            // set percent sale value
//            holder.tvSalePerc.setText(mContext.getResources().getString(R.string.percent_sale,
//                    String.valueOf(Utils.calculatePercSale(Utils.getDollarValue(alItems.get(position).price),
//                            Utils.getDollarValue(alItems.get(position).salePrice)))));
//            // set price as sale price and price
//            holder.tvPrice.setText(mContext.getResources().getString(
//                    R.string.dollar_format, alItems.get(position).salePrice));
//            holder.tvScratchPrice.setText(mContext.getResources().getString(
//                    R.string.dollar_format, alItems.get(position).price));
//            holder.tvScratchPrice.setPaintFlags(holder.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        } else {
//            // set visibility
//            FrameworkUtils.setViewGone(holder.tvSalePerc);
//            FrameworkUtils.setViewInvisible(holder.tvScratchPrice);
//            holder.tvPrice.setText(mContext.getResources().getString(
//                    R.string.dollar_format, alItems.get(position).price));
//        }

        // label
        if (!alItems.get(mPos).label.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.NONE.toString())) {
            // set visibility
            FrameworkUtils.setViewVisible(llLabelWrapper);
            // set value
            tvLabel.setText(alItems.get(mPos).label);
            if (alItems.get(mPos).label.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.SALE.toString())) {
                // set visibility
                FrameworkUtils.setViewVisible(ivLabelIcon);
                // set icon
                ivLabelIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.label_sale_icon));
                // set text color
                tvLabel.setTextColor(ContextCompat.getColor(mContext, R.color.material_green_300_color_code));
            } else if (alItems.get(mPos).label.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.LEONARD_FAVORITE.toString())) {
                // set visibility
                FrameworkUtils.setViewVisible(ivLabelIcon);
                // set icon
                ivLabelIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.label_leonard_favorite_icon));
                // set text color
                tvLabel.setTextColor(ContextCompat.getColor(mContext, R.color.material_light_blue_300_color_code));
            } else if (alItems.get(mPos).label.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.SUPER_HOT.toString())) {
                // set visibility
                FrameworkUtils.setViewVisible(ivLabelIcon);
                // set icon
                ivLabelIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.label_super_hot_icon));
                // set text color
                tvLabel.setTextColor(ContextCompat.getColor(mContext, R.color.material_red_300_color_code));
            }
        }

        // set values
        tvTitle.setText(alItems.get(mPos).title);
//        tvDesc.setText(alItems.get(mPos).description);
        tvDesc.setText("wfuhwiuefw hwuefhwf hwefuhw euuuue ufhhweu faeaw ufewfwf house fhwfhuwehfw aa cat ahfeuahwfnewuf wwwe what wfhwuaefhwfhe can I afwefwefwaef see uaafuhauefhw up in hefahuhaefw how far in three lines of text");
        // set image
        Picasso.with(mContext).load(ItemModel.getFormattedImageUrl(alItems.get(mPos).imageUrl1))
                .placeholder(R.drawable.no_image_available)
                .resize(Constants.DEFAULT_IMAGE_SIZE_500, Constants.DEFAULT_IMAGE_SIZE_500)
                .into(ivBg);
    }

    @Override
    public void onClick(View view) {
        if (!FrameworkUtils.isViewClickable()) {
            return;
        }

        switch (view.getId()) {
            case R.id.tv_fragment_header:
                remove();
//                popBackStack();
                break;
            default:
                break;
        }
    }
}
