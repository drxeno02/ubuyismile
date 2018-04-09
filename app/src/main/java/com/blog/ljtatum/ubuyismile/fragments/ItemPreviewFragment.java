package com.blog.ljtatum.ubuyismile.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.app.framework.gui.SwipeGestureUtils;
import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.utils.Utils;

/**
 * Created by LJTat on 4/8/2018.
 */

public class ItemPreviewFragment extends BaseFragment implements View.OnClickListener, GestureDetector.OnGestureListener {
    private static final String TAG = ItemPreviewFragment.class.getSimpleName();

    private View mRootView;
    private ImageView ivClose, ivBuildingPreview;
    private TextView tvBuildingName, tvBuildingAddress;
    // The listener that is used to notify when gestures occur
    private GestureDetector mGestureDetector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_item_preview, container, false);

        // instantiate views
        initializeViews();
        initializeHandlers();
        initializeListeners();
        return mRootView;
    }

    /**
     * Method is used to initialize views
     */
    private void initializeViews() {
        // instantiate gesture
        mGestureDetector = new GestureDetector(this);
    }

    /**
     * Method is used to initialize click listeners
     */
    private void initializeHandlers() {
        ivClose.setOnClickListener(this);
    }

    /**
     * Method is used to initialize listeners and callbacks
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initializeListeners() {
        // onTouchListener listener
        ivBuildingPreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    public void onClick(@NonNull View v) {
        if (!FrameworkUtils.isViewClickable()) {
            return;
        }

        switch (v.getId()) {
//            case R.id.iv_close:
//                removeNoAnim();
//                popBackStack();
//                break;
            default:
                break;
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // do nothing
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // do nothing
    }

    @Override
    public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        // motion event A
        float x1 = e1.getX();
        float y1 = e1.getY();
        // motion event B
        float x2 = e2.getX();
        float y2 = e2.getY();

        if (SwipeGestureUtils.getDirection(x1, y1, x2, y2).equals(SwipeGestureUtils.Direction.DOWN)) {
            remove();
            popBackStack();
        }
        return true;
    }

}
