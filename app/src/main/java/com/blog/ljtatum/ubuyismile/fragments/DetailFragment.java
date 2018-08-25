package com.blog.ljtatum.ubuyismile.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.amazon.framework.enums.Enum;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.activity.MainActivity;
import com.blog.ljtatum.ubuyismile.adapter.FavoriteAdapter;
import com.blog.ljtatum.ubuyismile.adapter.ScreenshotAdapter;
import com.blog.ljtatum.ubuyismile.asynctask.AsyncTaskUpdateItemDatabase;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.constants.Durations;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.provider.ItemProvider;
import com.blog.ljtatum.ubuyismile.interfaces.OnDatabaseChangeListener;
import com.blog.ljtatum.ubuyismile.interfaces.OnFavoriteRemoveListener;
import com.blog.ljtatum.ubuyismile.interfaces.OnScreenshotSelectedListener;
import com.blog.ljtatum.ubuyismile.utils.HappinessUtils;
import com.blog.ljtatum.ubuyismile.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by LJTat on 4/3/2018.
 */

public class DetailFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private Activity mActivity;
    private View mRootView;

    private TextView tvFragmentHeader, tvLabel, tvTitle, tvPrice, tvDesc, tvBuy, tvNoFavoriteItems,
            tvPageIndicator;
    private ImageView ivLabelIcon, ivShare, ivFavorite, ivFavoriteAnim;
    private LinearLayout llLabelWrapper, llFavoriteIndicatorWrapper;
    private RelativeLayout rlParent;
    private View vFavoriteAnimBg;

    private int mItemIndex;
    private String mCategory, mItemType, mItemId;
    private ViewPager vpScreenshot;

    // database
    private ItemProvider mItemProvider;
    private List<ItemDatabaseModel> alItemDb;

    // adapter (favorite)
    private FavoriteAdapter mFavoriteAdapter;
    private RecyclerView rvFavorite;

    // adapter (screenshot)
    private List<String> alImgUrls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        // instantiate views
        initializeViews();
        initializeHandlers();
        initializeListeners();
        // retrieve bundle info
        getBundle();

        return mRootView;
    }

    /**
     * Method is used to instantiate views
     */
    private void initializeViews() {
        mContext = getActivity();
        mActivity = getActivity();
        alImgUrls = new ArrayList<>();

        // instantiate SQLite database
        mItemProvider = new ItemProvider(mContext);
        alItemDb = mItemProvider.getAllInfo();

        // track Happiness
        HappinessUtils.trackHappiness(HappinessUtils.EVENT_CONTENT_ITEM_DETAIL_COUNTER);

        // initialize views
        vpScreenshot = mRootView.findViewById(R.id.vp_screenshot);
        rvFavorite = mRootView.findViewById(R.id.rv_favorite);
        llLabelWrapper = mRootView.findViewById(R.id.ll_label_wrapper);
        llFavoriteIndicatorWrapper = mRootView.findViewById(R.id.ll_favorite_indicator_wrapper);
        rlParent = mRootView.findViewById(R.id.rl_parent);
        tvFragmentHeader = mRootView.findViewById(R.id.tv_fragment_header);
        tvPrice = mRootView.findViewById(R.id.tv_price);
        tvLabel = mRootView.findViewById(R.id.tv_label);
        tvTitle = mRootView.findViewById(R.id.tv_title);
        tvDesc = mRootView.findViewById(R.id.tv_desc);
        tvBuy = mRootView.findViewById(R.id.tv_buy);
        tvNoFavoriteItems = mRootView.findViewById(R.id.tv_no_favorite_items);
        tvPageIndicator = mRootView.findViewById(R.id.tv_page_indicator);
        ivLabelIcon = mRootView.findViewById(R.id.iv_label_icon);
        ivShare = mRootView.findViewById(R.id.iv_share);
        ivFavorite = mRootView.findViewById(R.id.iv_favorite);
        ivFavoriteAnim = mRootView.findViewById(R.id.iv_favorite_anim);
        vFavoriteAnimBg = mRootView.findViewById(R.id.v_favorite_anim_bg);

        if (!isFavoriteItem()) {
            // set visibility
            FrameworkUtils.setViewGone(rvFavorite);
            FrameworkUtils.setViewVisible(tvNoFavoriteItems);
        } else {
            // set visibility
            FrameworkUtils.setViewGone(tvNoFavoriteItems);
            FrameworkUtils.setViewVisible(rvFavorite);
        }

        // initialize adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFavorite.setLayoutManager(layoutManager);
        rvFavorite.addItemDecoration(new DividerItemDecoration(rvFavorite.getContext(), DividerItemDecoration.VERTICAL));
        mFavoriteAdapter = new FavoriteAdapter(mContext, filterFavoriteItemList(mItemProvider.getAllInfo()));
        rvFavorite.setAdapter(mFavoriteAdapter);
    }

    /**
     * Method is used to set click listeners
     */
    private void initializeHandlers() {
        tvFragmentHeader.setOnClickListener(this);
        tvBuy.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivFavorite.setOnClickListener(this);
    }

    /**
     * Method is used to initialize listeners and callbacks
     */
    private void initializeListeners() {
        // onDatabseChange listener
        AsyncTaskUpdateItemDatabase.onDatabaseChangeListener(new OnDatabaseChangeListener() {
            @Override
            public void onDatabaseUpdate() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alItemDb = mItemProvider.getAllInfo();

                        if (!isFavoriteItem()) {
                            // set visibility
                            FrameworkUtils.setViewGone(rvFavorite);
                            FrameworkUtils.setViewVisible(tvNoFavoriteItems);
                        } else {
                            // set visibility
                            FrameworkUtils.setViewGone(tvNoFavoriteItems);
                            FrameworkUtils.setViewVisible(rvFavorite);

                            // update adapter
                            mFavoriteAdapter.updateData(filterFavoriteItemList(mItemProvider.getAllInfo()));
                        }

                        // set item details
                        setItemDetails();
                    }
                });
            }
        });

        // onFavoriteRemove listener
        FavoriteAdapter.onFavoriteRemoveListener(new OnFavoriteRemoveListener() {
            @Override
            public void onFavoriteRemove(ItemDatabaseModel item) {
                // item to un-favorite
                for (int i = 0; i < alItemDb.size(); i++) {
                    if (alItemDb.get(i).itemId.equalsIgnoreCase(item.itemId)) {
                        alItemDb.get(i).isFavorite = false;
                        break;
                    }
                }
                // update database
                new AsyncTaskUpdateItemDatabase(mContext, mItemProvider, null, item).execute();
            }
        });

        // onScreenshotSelected listener
        ScreenshotAdapter.onScreenshotSelectedListener(new OnScreenshotSelectedListener() {
            @Override
            public void onClick(int pos) {
                Bundle args = new Bundle();
                args.putString(Constants.KEY_ITEM_ID, mItemId);
                args.putInt(Constants.KEY_SCREENSHOT_POS, pos);
                // add fragment
                BaseFragment fragment = new ItemPreviewFragment();
                fragment.setArguments(args);
                addFragmentNoAnim(fragment);
            }
        });

        // onPageChange listener
        vpScreenshot.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // do nothing
            }

            @Override
            public void onPageSelected(int position) {
                // update page indicator
                tvPageIndicator.setText(mContext.getResources().getString(R.string.page_indicator, position + 1, alImgUrls.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // do nothing
            }
        });
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
            if (mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.SEARCH.toString()) ||
                    mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.BROWSE.toString())) {
                rlParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.material_light_blue_400_color_code));
            } else if (mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.AMAZON.toString())) {
                rlParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.material_orange_400_color_code));
            } else if (mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.CHABLEE.toString())) {
                rlParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.material_pink_100_color_code));
            }

            // set header
            if (mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.SEARCH.toString()) ||
                    mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.BROWSE.toString()) ||
                    mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.AMAZON.toString())) {
                // set fragment header
                if (mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.SEARCH.toString())) {
                    tvFragmentHeader.setText(getResources().getString(R.string.menu_search));
                } else if (mItemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.BROWSE.toString())) {
                    tvFragmentHeader.setText(getResources().getString(R.string.menu_browse));
                } else {
                    tvFragmentHeader.setText(getResources().getString(R.string.menu_amazon));
                }
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
                if (Utils.isItemOnSale(alItemDb.get(i))) {
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

                // populate image list
                populateImageUrls(alItemDb.get(i));

                // update page indicator
                tvPageIndicator.setText(mContext.getResources().getString(R.string.page_indicator, 1, alImgUrls.size()));

                // set adapter
                vpScreenshot.setAdapter(new ScreenshotAdapter(mContext, alImgUrls));
                break;
            }
        }
    }

    /**
     * Method is used to populate a list of image urls
     *
     * @param item Selected item
     */
    private void populateImageUrls(@NonNull ItemDatabaseModel item) {
        if (!FrameworkUtils.isStringEmpty(item.imageUrl1)) {
            // add screenshot 1
            alImgUrls.add(item.imageUrl1);
        }
        if (!FrameworkUtils.isStringEmpty(item.imageUrl2)) {
            // add screenshot 2
            alImgUrls.add(item.imageUrl2);
        }
        if (!FrameworkUtils.isStringEmpty(item.imageUrl3)) {
            // add screenshot 3
            alImgUrls.add(item.imageUrl3);
        }
        if (!FrameworkUtils.isStringEmpty(item.imageUrl4)) {
            // add screenshot 4
            alImgUrls.add(item.imageUrl4);
        }
        if (!FrameworkUtils.isStringEmpty(item.imageUrl5)) {
            // add screenshot 5
            alImgUrls.add(item.imageUrl5);
        }
    }

    @Override
    public void onClick(View view) {
        if (!FrameworkUtils.isViewClickable()) {
            return;
        }

        // initialize bundle and fragment
        Bundle args = new Bundle();
        BaseFragment fragment;

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
            case R.id.iv_share:
                // share
                share();
                break;
            case R.id.iv_favorite:
                // favorite
                addFavoriteItem();
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
            msg = getResources().getString(R.string.share_option_a).concat(" ").concat(alItemDb.get(mItemIndex).purchaseUrl);
        } else if (msgValue == 1) {
            msg = getResources().getString(R.string.share_option_b).concat(" ").concat(alItemDb.get(mItemIndex).purchaseUrl);
        } else {
            msg = getResources().getString(R.string.share_option_c).concat(" ").concat(alItemDb.get(mItemIndex).purchaseUrl);
        }

        ShareCompat.IntentBuilder.from(mActivity)
                .setType("text/plain")
                .setChooserTitle(getResources().getString(R.string.menu_share))
                .setText(msg)
                .startChooser();
    }

    /**
     * Method is used to favorite an item
     */
    private void addFavoriteItem() {
        // update flag
        alItemDb.get(mItemIndex).isFavorite = !alItemDb.get(mItemIndex).isFavorite;

        // favorite item
        if (alItemDb.get(mItemIndex).isFavorite) {
            FrameworkUtils.setViewVisible(llFavoriteIndicatorWrapper);
            ivFavorite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.favorite_icon_on));
            // animate favorite
            animateFavorite();
        } else {
            FrameworkUtils.setViewGone(llFavoriteIndicatorWrapper);
            ivFavorite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.favorite_icon_off));
        }
        // update database
        new AsyncTaskUpdateItemDatabase(mContext, mItemProvider, null, alItemDb.get(mItemIndex)).execute();
    }

    /**
     * Method is used to show animation when item is favorite
     */
    private void animateFavorite() {
        // interpolator where the rate of change starts out quickly and and then decelerates
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
        // interpolator where the rate of change starts out slowly and and then accelerates
        AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();

        // set visibility
        FrameworkUtils.setViewVisible(ivFavoriteAnim, vFavoriteAnimBg);

        // alpha and scaling
        vFavoriteAnimBg.setAlpha(1f);
        vFavoriteAnimBg.setScaleX(0.1f);
        vFavoriteAnimBg.setScaleY(0.1f);
        ivFavoriteAnim.setScaleX(0.1f);
        ivFavoriteAnim.setScaleY(0.1f);

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator bgScaleYAnim = ObjectAnimator.ofFloat(vFavoriteAnimBg, "scaleY", 0.1f, 1f);
        bgScaleYAnim.setDuration(Durations.ANIM_DURATION_SHORT_200);
        bgScaleYAnim.setInterpolator(decelerateInterpolator);
        ObjectAnimator bgScaleXAnim = ObjectAnimator.ofFloat(vFavoriteAnimBg, "scaleX", 0.1f, 1f);
        bgScaleXAnim.setDuration(Durations.ANIM_DURATION_SHORT_200);
        bgScaleXAnim.setInterpolator(decelerateInterpolator);
        ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(vFavoriteAnimBg, "alpha", 1f, 0f);
        bgAlphaAnim.setDuration(Durations.ANIM_DURATION_SHORT_200);
        bgAlphaAnim.setStartDelay(Durations.ANIM_DURATION_SHORT_200);
        bgAlphaAnim.setInterpolator(decelerateInterpolator);

        ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(ivFavoriteAnim, "scaleY", 0.1f, 1f);
        imgScaleUpYAnim.setDuration(Durations.ANIM_DURATION_MEDIUM_400);
        imgScaleUpYAnim.setInterpolator(decelerateInterpolator);
        ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(ivFavoriteAnim, "scaleX", 0.1f, 1f);
        imgScaleUpXAnim.setDuration(Durations.ANIM_DURATION_MEDIUM_400);
        imgScaleUpXAnim.setInterpolator(decelerateInterpolator);

        ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(ivFavoriteAnim, "scaleY", 1f, 0f);
        imgScaleDownYAnim.setDuration(Durations.ANIM_DURATION_MEDIUM_400);
        imgScaleDownYAnim.setInterpolator(accelerateInterpolator);
        ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(ivFavoriteAnim, "scaleX", 1f, 0f);
        imgScaleDownXAnim.setDuration(Durations.ANIM_DURATION_MEDIUM_400);
        imgScaleDownXAnim.setInterpolator(accelerateInterpolator);

        // sets up this AnimatorSet to play all of the supplied animations at the same time
        animatorSet.playTogether(bgScaleYAnim, bgScaleXAnim, bgAlphaAnim, imgScaleUpYAnim, imgScaleUpXAnim);
        animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim);
        animatorSet.start();
    }

    /**
     * Method is used to filter out items that are not favorited
     *
     * @return List of favorite items
     */
    private List<ItemDatabaseModel> filterFavoriteItemList(@NonNull List<ItemDatabaseModel> alItems) {
        for (int i = alItems.size() - 1; i >= 0; i--) {
            if (!alItems.get(i).isFavorite) {
                alItems.remove(i);
            }
        }
        return alItems;
    }

    /**
     * Method is used to check if there are any favorite items that exist
     *
     * @return True if there are any favorite items that exists, otherwise false
     */
    private boolean isFavoriteItem() {
        boolean isFavoriteItem = false;
        for (int i = 0; i < alItemDb.size(); i++) {
            if (alItemDb.get(i).isFavorite) {
                isFavoriteItem = true;
                break;
            }
        }
        return isFavoriteItem;
    }

    @Override
    public void onResume() {
        super.onResume();
        // disable drawer
        ((MainActivity) mContext).setDrawerState(false);
    }

    @Override
    public void onDetach() {
        // enable drawer
        ((MainActivity) mContext).setDrawerState(true);
        // clear animation
        tvBuy.clearAnimation();
        tvBuy = null;
        System.gc();
        super.onDetach();
    }
}
