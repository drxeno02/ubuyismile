package com.blog.ljtatum.ubuyismile.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;

/**
 * Created by leonard on 10/23/2017.
 */

public class BaseFragment extends Fragment {

    /**
     * Method is used to pop the top state off the back stack. Returns true if there
     * was one to pop, else false. This function is asynchronous -- it enqueues the
     * request to pop, but the action will not be performed until the application
     * returns to its event loop.
     */
    public void popBackStack() {
        if (!FrameworkUtils.checkIfNull(getActivity())) {
            try {
                getActivity().getSupportFragmentManager().popBackStack();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method for removing the Fragment view
     */
    void remove() {
        if (!FrameworkUtils.checkIfNull(getActivity())) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.ui_slide_in_from_bottom, R.anim.ui_slide_out_to_bottom);
            ft.remove(this).commitAllowingStateLoss();
        }
    }
}
