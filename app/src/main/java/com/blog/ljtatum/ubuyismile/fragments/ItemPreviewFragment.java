package com.blog.ljtatum.ubuyismile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.databases.provider.ItemProvider;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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

    private String mItemId;
    private int mScreenshotPos;

    // database
    private List<ItemDatabaseModel> alItemDb;

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

        // instantiate SQLite database
        ItemProvider itemProvider = new ItemProvider(mContext);
        alItemDb = !FrameworkUtils.checkIfNull(itemProvider.getAllInfo()) ?
                itemProvider.getAllInfo() : new ArrayList<ItemDatabaseModel>();

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
            mItemId = args.getString(Constants.KEY_ITEM_ID, "");
            mScreenshotPos = args.getInt(Constants.KEY_SCREENSHOT_POS, 0);

            // set item details
            setItemDetails();
        }
    }

    /**
     * Method is used to populate item details
     */
    private void setItemDetails() {
        for (int i = 0; i < alItemDb.size(); i++) {
            if (alItemDb.get(i).itemId.equalsIgnoreCase(mItemId)) {
                // set values
                tvTitle.setText(alItemDb.get(i).title);

                // set image
                Picasso.with(mContext).load(ItemModel.getFormattedImageUrl(getImgUrl(alItemDb.get(i))))
                        .placeholder(R.drawable.no_image_available)
                        .resize(Constants.DEFAULT_IMAGE_SIZE_250, Constants.DEFAULT_IMAGE_SIZE_250)
                        .into(ivItemPreview);
                break;
            }
        }
    }

    /**
     * Method is used to retrieve selected image url
     *
     * @param item Database object {@link com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel}
     *             that represents item properties
     * @return Image url
     */
    private String getImgUrl(@NonNull ItemDatabaseModel item) {
        if (mScreenshotPos == 1) {
            return item.imageUrl2;
        } else if (mScreenshotPos == 2) {
            return item.imageUrl3;
        } else if (mScreenshotPos == 3) {
            return item.imageUrl4;
        } else if (mScreenshotPos == 4) {
            return item.imageUrl5;
        } else if (mScreenshotPos == 5) {
            return item.imageUrl6;
        }
        return item.imageUrl1;
    }

    @Override
    public void onClick(@NonNull View v) {
        if (!FrameworkUtils.isViewClickable()) {
            return;
        }

        switch (v.getId()) {
            case R.id.rl_parent:
                // remove fragment
                removeNoAnim();
                break;
            default:
                break;
        }
    }

}
