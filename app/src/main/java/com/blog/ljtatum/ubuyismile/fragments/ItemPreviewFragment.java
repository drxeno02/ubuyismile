package com.blog.ljtatum.ubuyismile.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.app.amazon.framework.enums.Enum;
import com.app.framework.gui.SwipeGestureUtils;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.model.ChableeData;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.blog.ljtatum.ubuyismile.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by LJTat on 4/8/2018.
 */

public class ItemPreviewFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = ItemPreviewFragment.class.getSimpleName();

    private Context mContext;
    private View mRootView;
    private RelativeLayout rlParent;
    private ImageView ivItemPreview;
    private TextView tvTitle;

    private int mPos;
    private ArrayList<ItemModel> alItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_item_preview, container, false);

        // instantiate views
        initializeViews();
        initializeHandlers();
        // retrieve bundle info
        getBundle();

        return mRootView;
    }

    /**
     * Method is used to initialize views
     */
    private void initializeViews() {
        mContext = getActivity();
        alItems = new ArrayList<>();

        rlParent = mRootView.findViewById(R.id.rl_parent);
        tvTitle = mRootView.findViewById(R.id.tv_title);
        ivItemPreview = mRootView.findViewById(R.id.iv_item_preview);
    }

    /**
     * Method is used to initialize click listeners
     */
    private void initializeHandlers() {
        rlParent.setOnClickListener(this);
    }

    /**
     * Method is used to retrieve bundle information
     */
    private void getBundle() {
        Bundle args = getArguments();
        if (!FrameworkUtils.checkIfNull(args)) {
            // set position
            mPos = args.getInt(Constants.KEY_ITEM_POS);
            // set category
            String category = args.getString(Constants.KEY_CATEGORY, "");
            // set type
            String itemType = args.getString(Constants.KEY_ITEM_TYPE, "");

            // set header
            if (itemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.BROWSE.toString())) {

            } else if (itemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.CHABLEE.toString())) {
                if (category.equalsIgnoreCase(Enum.ItemCategoryChablee.CROWNS.toString())) {
                    alItems = ChableeData.getCrowns();
                } else if (category.equalsIgnoreCase(Enum.ItemCategoryChablee.RINGS.toString())) {
                    alItems = ChableeData.getRings();
                } else if (category.equalsIgnoreCase(Enum.ItemCategoryChablee.NECKLACES.toString())) {
                    alItems = ChableeData.getNecklaces();
                } else if (category.equalsIgnoreCase(Enum.ItemCategoryChablee.ROCKS.toString())) {
                    alItems = ChableeData.getRocks();
                }
            }

            // populate details
            populateItemDetails();
        }
    }

    /**
     * Method is used to populate item details
     */
    private void populateItemDetails() {
        // set values
        tvTitle.setText(alItems.get(mPos).title);
        // set image
        Picasso.with(mContext).load(ItemModel.getFormattedImageUrl(alItems.get(mPos).imageUrl1))
                .placeholder(R.drawable.no_image_available)
                .resize(Constants.DEFAULT_IMAGE_SIZE_500, Constants.DEFAULT_IMAGE_SIZE_500)
                .into(ivItemPreview);
    }

    @Override
    public void onClick(@NonNull View v) {
        if (!FrameworkUtils.isViewClickable()) {
            return;
        }

        switch (v.getId()) {
            case R.id.rl_parent:
                // remove fragment
                remove();
                break;
            default:
                break;
        }
    }

}
