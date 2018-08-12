package com.blog.ljtatum.ubuyismile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.interfaces.OnScreenshotSelectedListener;
import com.blog.ljtatum.ubuyismile.model.ItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ScreenshotAdapter extends PagerAdapter {

    // custom callback
    private static OnScreenshotSelectedListener mOnScreenshotSelectedListener;
    private Context mContext;
    private List<String> alImgUrls;

    /**
     * Constructor
     *
     * @param context Interface to global information about an application environment
     * @param imgUrls List of image urls to load
     */
    public ScreenshotAdapter(@NonNull Context context, @NonNull List<String> imgUrls) {
        mContext = context;
        alImgUrls = imgUrls;
    }

    /**
     * Method is used to set callback for when item is clicked
     *
     * @param listener Callback for when item is clicked
     */
    public static void onScreenshotSelectedListener(OnScreenshotSelectedListener listener) {
        mOnScreenshotSelectedListener = listener;
    }

    @Override
    public int getCount() {
        return alImgUrls.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // create the page for the given position
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup itemView = (ViewGroup) inflater.inflate(R.layout.item_screenshot, container, false);

        // instantiate views
        ImageView ivScreenshot = itemView.findViewById(R.id.iv_screenshot);

        // set image
        Picasso.with(mContext).load(ItemModel.getFormattedImageUrl(alImgUrls.get(position)))
                .placeholder(R.drawable.no_image_available)
                .resize(Constants.DEFAULT_IMAGE_SIZE_250, Constants.DEFAULT_IMAGE_SIZE_250)
                .into(ivScreenshot);

        // set listener
        ivScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FrameworkUtils.checkIfNull(mOnScreenshotSelectedListener)) {
                    // set listener
                    mOnScreenshotSelectedListener.onClick(position);
                }
            }
        });

        // add view to container
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        // remove view from container
        container.removeView((FrameLayout) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // determines whether a page View is associated with a specific key object
        // as returned by instantiateItem(ViewGroup, int)
        return view == object;
    }
}
