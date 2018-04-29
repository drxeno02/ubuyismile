package com.blog.ljtatum.ubuyismile.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
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
import com.app.framework.utilities.dialog.DialogUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.asynctask.AsyncTaskUpdateDatabase;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.provider.ItemProvider;
import com.blog.ljtatum.ubuyismile.logger.Logger;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.blog.ljtatum.ubuyismile.utils.AnimationUtils;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by LJTat on 4/3/2018.
 */

public class DetailFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private View mRootView;

    private TextView tvFragmentHeader, tvLabel, tvTitle, tvPrice, tvDesc, tvBuy;
    private ImageView ivBg, ivLabelIcon, ivShare, ivFavorite;
    private LinearLayout llLabelWrapper, llFavoriteIndicatorWrapper;
    private RelativeLayout rlParent;

    private int mItemIndex;
    private String mCategory, mItemType, mItemId;

    // database
    private ItemProvider mItemProvider;
    private List<ItemDatabaseModel> alItemDb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        // instantiate views
        initializeViews();
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

        // instantiate SQLite database
        mItemProvider = new ItemProvider(mContext);
        alItemDb = !FrameworkUtils.checkIfNull(mItemProvider.getAllInfo()) ?
                mItemProvider.getAllInfo() : new ArrayList<ItemDatabaseModel>();

        llLabelWrapper = mRootView.findViewById(R.id.ll_label_wrapper);
        llFavoriteIndicatorWrapper = mRootView.findViewById(R.id.ll_favorite_indicator_wrapper);
        rlParent = mRootView.findViewById(R.id.rl_parent);
        tvFragmentHeader = mRootView.findViewById(R.id.tv_fragment_header);
        tvPrice = mRootView.findViewById(R.id.tv_price);
        tvLabel = mRootView.findViewById(R.id.tv_label);
        tvTitle = mRootView.findViewById(R.id.tv_title);
        tvDesc = mRootView.findViewById(R.id.tv_desc);
        tvBuy = mRootView.findViewById(R.id.tv_buy);
        ivBg = mRootView.findViewById(R.id.iv_bg);
        ivLabelIcon = mRootView.findViewById(R.id.iv_label_icon);
        ivShare = mRootView.findViewById(R.id.iv_share);
        ivFavorite = mRootView.findViewById(R.id.iv_favorite);

        // start animation
        tvBuy.startAnimation(AnimationUtils.retrieveBlinkAnimation());
    }

    /**
     * Method is used to set click listeners
     */
    private void initializeHandlers() {
        tvFragmentHeader.setOnClickListener(this);
        tvBuy.setOnClickListener(this);
        ivBg.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivFavorite.setOnClickListener(this);
    }

    /**
     * Method is used to retrieve bundle information
     */
    private void getBundle() {
        Bundle args = getArguments();
        if (!FrameworkUtils.checkIfNull(args)) {
            // set position
            mItemId = args.getString(Constants.KEY_ITEM_ID, "");
            // set category
            mCategory = args.getString(Constants.KEY_CATEGORY, "");
            // set type
            mItemType = args.getString(Constants.KEY_ITEM_TYPE, "");

            // set background
            if (mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.BROWSE.toString())) {
                rlParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.material_light_blue_400_color_code));
            } else if (mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.CHABLEE.toString())) {
                rlParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.material_pink_100_color_code));
            }

            // set header
            if (mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.BROWSE.toString())) {

            } else if (mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.CHABLEE.toString())) {
                if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.CROWNS.toString())) {
                    // set fragment header
                    tvFragmentHeader.setText(getResources().getString(R.string.menu_crowns));
                } else if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.RINGS.toString())) {
                    // set fragment header
                    tvFragmentHeader.setText(getResources().getString(R.string.menu_rings));
                } else if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.NECKLACES.toString())) {
                    // set fragment header
                    tvFragmentHeader.setText(getResources().getString(R.string.menu_necklaces));
                } else if (mCategory.equalsIgnoreCase(Enum.ItemCategoryChablee.ROCKS.toString())) {
                    // set fragment header
                    tvFragmentHeader.setText(getResources().getString(R.string.menu_rocks));
                }
            }

            // set item details
            setItemDetails();
        }
    }

    /**
     * Method is used to set item details
     */
    private void setItemDetails() {
        for (int i = 0; i < alItemDb.size(); i++) {
            if (alItemDb.get(i).itemId.equalsIgnoreCase(mItemId)) {
                // set index
                mItemIndex = i;
                // favorite item
                if (alItemDb.get(i).isFavorite) {
                    ivFavorite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.favorite_icon_on));
                    FrameworkUtils.setViewVisible(llFavoriteIndicatorWrapper);
                } else {
                    ivFavorite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.favorite_icon_off));
                    FrameworkUtils.setViewGone(llFavoriteIndicatorWrapper);
                }

                // sale price percentage
                if ((!FrameworkUtils.isStringEmpty(alItemDb.get(i).salePrice) &&
                        !FrameworkUtils.isStringEmpty(alItemDb.get(i).price)) &&
                        (Utils.getDollarValue(alItemDb.get(i).salePrice) > 0) &&
                        (Utils.getDollarValue(alItemDb.get(i).salePrice) <
                                Utils.getDollarValue(alItemDb.get(i).price))) {
                    // set price as sale price and price
                    tvPrice.setText(mContext.getResources().getString(
                            R.string.dollar_format, alItemDb.get(i).salePrice));
                } else {
                    tvPrice.setText(mContext.getResources().getString(
                            R.string.dollar_format, alItemDb.get(i).price));
                }

                // label
                if (!alItemDb.get(i).label.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.NONE.toString())) {
                    // set visibility
                    FrameworkUtils.setViewVisible(llLabelWrapper);
                    // set value
                    tvLabel.setText(alItemDb.get(i).label);
                    if (alItemDb.get(i).label.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.SALE.toString())) {
                        // set visibility
                        FrameworkUtils.setViewVisible(ivLabelIcon);
                        // set icon
                        ivLabelIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.label_sale_icon));
                        // set text color
                        tvLabel.setTextColor(ContextCompat.getColor(mContext, R.color.material_green_300_color_code));
                    } else if (alItemDb.get(i).label.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.LEONARD_FAVORITE.toString())) {
                        // set visibility
                        FrameworkUtils.setViewVisible(ivLabelIcon);
                        // set icon
                        ivLabelIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.label_leonard_favorite_icon));
                        // set text color
                        tvLabel.setTextColor(ContextCompat.getColor(mContext, R.color.material_light_blue_300_color_code));
                    } else if (alItemDb.get(i).label.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemLabel.SUPER_HOT.toString())) {
                        // set visibility
                        FrameworkUtils.setViewVisible(ivLabelIcon);
                        // set icon
                        ivLabelIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.label_super_hot_icon));
                        // set text color
                        tvLabel.setTextColor(ContextCompat.getColor(mContext, R.color.material_red_300_color_code));
                    }
                }

                // set values
                tvTitle.setText(alItemDb.get(i).title);
                tvDesc.setText(alItemDb.get(i).description);
                // set image
                Picasso.with(mContext).load(ItemModel.getFormattedImageUrl(alItemDb.get(i).imageUrl1))
                        .placeholder(R.drawable.no_image_available)
                        .resize(Constants.DEFAULT_IMAGE_SIZE_500, Constants.DEFAULT_IMAGE_SIZE_500)
                        .into(ivBg);
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (!FrameworkUtils.isViewClickable()) {
            return;
        }

        // initialize bundle and fragment
        Bundle args = new Bundle();
        Fragment fragment;

        switch (view.getId()) {
            case R.id.tv_fragment_header:
                // remove fragment
                remove();
                break;
            case R.id.tv_buy:
                args.putString(Constants.KEY_ITEM_ID, mItemId);
                args.putString(Constants.KEY_CATEGORY, mCategory);
                args.putString(Constants.KEY_ITEM_TYPE, mItemType);
                // add fragment
                fragment = new WebViewFragment();
                fragment.setArguments(args);
                addFragment(fragment);
                break;
            case R.id.iv_bg:
                args.putString(Constants.KEY_ITEM_ID, mItemId);
                // add fragment
                fragment = new ItemPreviewFragment();
                fragment.setArguments(args);
                addFragmentNoAnim(fragment);
                break;
            case R.id.iv_share:
                // share
                share();
                break;
            case R.id.iv_favorite:
                // favorite
                favorite();
                break;
            default:
                break;
        }
    }

    /**
     * Method is used to share item details
     */
    private void share() {
        String msg;
        Random rand = new Random();
        int msgValue = (rand.nextInt(3));
        if (msgValue == 0) {
            msg = getActivity().getResources().getString(R.string.share_option_a).concat(" ").concat(alItemDb.get(mItemIndex).purchaseUrl);
        } else if (msgValue == 1) {
            msg = getActivity().getResources().getString(R.string.share_option_b).concat(" ").concat(alItemDb.get(mItemIndex).purchaseUrl);
        } else {
            msg = getActivity().getResources().getString(R.string.share_option_c).concat(" ").concat(alItemDb.get(mItemIndex).purchaseUrl);
        }

        ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setChooserTitle(getActivity().getResources().getString(R.string.menu_share))
                .setText(msg)
                .startChooser();
    }

    /**
     * Method is used to favorite an item
     */
    private void favorite() {
        // update flag
        alItemDb.get(mItemIndex).isFavorite = !alItemDb.get(mItemIndex).isFavorite;
        // favorite item
        if (alItemDb.get(mItemIndex).isFavorite) {
            ivFavorite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.favorite_icon_on));
            FrameworkUtils.setViewVisible(llFavoriteIndicatorWrapper);
        } else {
            ivFavorite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.favorite_icon_off));
            FrameworkUtils.setViewGone(llFavoriteIndicatorWrapper);
        }
        // update database
        new AsyncTaskUpdateDatabase(mContext, mItemProvider, alItemDb).execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // clear animation
        tvBuy.clearAnimation();
        tvBuy = null;
        System.gc();
    }
}
