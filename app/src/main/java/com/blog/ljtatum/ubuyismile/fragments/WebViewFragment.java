package com.blog.ljtatum.ubuyismile.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.amazon.framework.enums.Enum;
import com.app.framework.utilities.FrameworkUtils;
import com.app.framework.utilities.dialog.DialogUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.constants.Constants;
import com.blog.ljtatum.ubuyismile.model.ChableeData;
import com.blog.ljtatum.ubuyismile.model.ItemModel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by LJTat on 4/10/2018.
 */

public class WebViewFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private View mRootView;
    private WebView wv;

    private TextView tvFragmentHeader;
    private ImageView ivIcon;
    private LinearLayout llNoPurchaseUrl;
    private RelativeLayout rlParent;
    private ArrayList<ItemModel> alItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_web_view, container, false);

        // instantiate views
        initializeViews();
        initializeHandlers();
        initializeListeners();
        // retrieve bundle info
        getBundle();

        return mRootView;
    }

    /**
     * Method is used to initialize views
     */
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initializeViews() {
        mContext = getActivity();
        alItems = new ArrayList<>();

        // web view
        wv = mRootView.findViewById(R.id.wv);
        wv.getSettings().setJavaScriptEnabled(true);

        llNoPurchaseUrl = mRootView.findViewById(R.id.ll_no_purchase_url);
        rlParent = mRootView.findViewById(R.id.rl_parent);
        tvFragmentHeader = mRootView.findViewById(R.id.tv_fragment_header);
        ivIcon = mRootView.findViewById(R.id.iv_icon);
    }

    /**
     * Method is used to initialize click listeners
     */
    private void initializeHandlers() {
        tvFragmentHeader.setOnClickListener(this);
    }

    /**
     * Method is used to initialize listeners and callbacks
     */
    private void initializeListeners() {
        // track back button
        wv.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, @NonNull KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    // if there is web page history, go back one web page
                    if (wv.canGoBack()) {
                        wv.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        // set web view client listener
        wv.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                // dismiss loading dialog
                DialogUtils.dismissProgressDialog();
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // do nothing
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, @NonNull WebResourceRequest req, @NonNull WebResourceError err) {
                // dismiss loading dialog
                DialogUtils.dismissProgressDialog();
                // redirect to deprecated method, so it can be used in all SDK versions
                onReceivedError(view, err.getErrorCode(), err.getDescription().toString(), req.getUrl().toString());
            }
        });
    }

    /**
     * Method is used to retrieve bundle information
     */
    private void getBundle() {
        Bundle args = getArguments();
        if (!FrameworkUtils.checkIfNull(args)) {
            // set category
            String category = args.getString(Constants.KEY_CATEGORY, "");
            // set type
            String itemType = args.getString(Constants.KEY_ITEM_TYPE, "");
            // set purchase url
            String purchaseUrl = args.getString(Constants.KEY_ITEM_PURCHASE_URL, "");

            // set background
            if (itemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.BROWSE.toString())) {
                rlParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.material_light_blue_400_color_code));
            } else if (itemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.CHABLEE.toString())) {
                rlParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.material_pink_100_color_code));
            }

            // set header
            if (itemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.BROWSE.toString())) {

            } else if (itemType.equalsIgnoreCase(com.blog.ljtatum.ubuyismile.enums.Enum.ItemType.CHABLEE.toString())) {
                if (category.equalsIgnoreCase(Enum.ItemCategoryChablee.CROWNS.toString())) {
                    alItems = ChableeData.getCrowns();
                    // set fragment header
                    tvFragmentHeader.setText(getResources().getString(R.string.menu_crowns));
                } else if (category.equalsIgnoreCase(Enum.ItemCategoryChablee.RINGS.toString())) {
                    alItems = ChableeData.getRings();
                    // set fragment header
                    tvFragmentHeader.setText(getResources().getString(R.string.menu_rings));
                } else if (category.equalsIgnoreCase(Enum.ItemCategoryChablee.NECKLACES.toString())) {
                    alItems = ChableeData.getNecklaces();
                    // set fragment header
                    tvFragmentHeader.setText(getResources().getString(R.string.menu_necklaces));
                } else if (category.equalsIgnoreCase(Enum.ItemCategoryChablee.ROCKS.toString())) {
                    alItems = ChableeData.getRocks();
                    // set fragment header
                    tvFragmentHeader.setText(getResources().getString(R.string.menu_rocks));
                }
            }

            // set web view
            if (!FrameworkUtils.isStringEmpty(purchaseUrl)) {
                // set visibility
                FrameworkUtils.setViewGone(llNoPurchaseUrl);

                // load website
                wv.loadUrl(purchaseUrl);
                // display progress dialog
                DialogUtils.showProgressDialog(mContext);
            } else {
                // set visibility
                FrameworkUtils.setViewVisible(llNoPurchaseUrl);

                // no purchase url
                TypedArray typedArryDrawable60 = mContext.getResources().obtainTypedArray(R.array.drawable_60);
                Random rand = new Random();
                ivIcon.setImageDrawable(ContextCompat.getDrawable(mContext, typedArryDrawable60.getResourceId(
                        rand.nextInt(typedArryDrawable60.length() - 1), 0)));
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (!FrameworkUtils.isViewClickable()) {
            return;
        }

        switch (view.getId()) {
            case R.id.tv_fragment_header:
                // remove fragment
                remove();
                popBackStack();
                break;
            default:
                break;
        }
    }

}
