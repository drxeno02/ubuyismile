package com.blog.ljtatum.ubuyismile.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;

/**
 * Created by leonard on 9/22/2017.
 */

public class BaseActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
    }

    /**
     * Method is used to add fragment to the current stack
     *
     * @param fragment The new Fragment that is going to replace the container
     */
    public void addFragment(@NonNull Fragment fragment) {
        // check if the fragment has been added already
        Fragment temp = mFragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());
        if (!FrameworkUtils.checkIfNull(temp) && temp.isAdded()) {
            return;
        }

        // add fragment and transition with animation
        mFragmentManager.beginTransaction().setCustomAnimations(R.anim.ui_slide_in_from_bottom,
                R.anim.ui_slide_out_to_bottom, R.anim.ui_slide_in_from_bottom,
                R.anim.ui_slide_out_to_bottom).add(R.id.frag_container, fragment,
                fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

    /**
     * Method is used to pop the top state off the back stack.
     * Returns true if there was one to pop, else false.
     */
    private void popBackStack() {
        mFragmentManager.popBackStack();
    }

    /**
     * Method is used to pop the top state off the back stack.
     * Returns true if there was one to pop, else false.
     */
    public void popBackStack(String name) {
        mFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * Method is used to remove a fragment
     *
     * @param fragment The fragment to be removed
     */
    public void removeFragment(Fragment fragment) {
        try {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.remove(fragment).commitAllowingStateLoss();
            popBackStack();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is used to remove all fragments
     */
    public void removeAllFragments() {
        try {
            for (Fragment fragment : mFragmentManager.getFragments()) {
                if (!FrameworkUtils.checkIfNull(fragment)) {
                    FragmentTransaction ft = mFragmentManager.beginTransaction();
                    ft.remove(fragment).commit();
                    popBackStack(fragment.getTag());
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is used to retrieve the current fragment the user is on
     *
     * @return Returns the TopFragment if there is one, otherwise returns null
     */
    @Nullable
    public Fragment getTopFragment() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            int i = mFragmentManager.getBackStackEntryCount();
            while (i >= 0) {
                i--;
                Fragment topFragment = mFragmentManager.getFragments().get(i);
                if (!FrameworkUtils.checkIfNull(topFragment)) {
                    return topFragment;
                }
            }
        }
        return null;
    }

    /**
     * Method is used to re-direct to a different Activity with no transition
     *
     * @param context          Interface to global information about an application environment
     * @param activity         The in-memory representation of a Java class
     * @param intent           An intent is an abstract description of an operation to be performed
     * @param isClearBackStack True to clear Activity backstack, otherwise false
     * @param isFinished       True to finish Activity otherwise false
     */
    public void goToActivity(@NonNull Context context, @NonNull Class<?> activity, Intent intent,
                             boolean isClearBackStack, boolean isFinished) {
        Intent i;
        if (FrameworkUtils.checkIfNull(intent)) {
            i = new Intent(context, activity);
        } else {
            i = intent;
        }
        if (isClearBackStack) {
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }

        if (isFinished && !isFinishing()) {
            finish();
        }
        startActivity(i);
    }

    /**
     * Method is used to re-direct to different Activity from a fragment with a
     * transition animation slide in from bottom of screen
     *
     * @param context          Interface to global information about an application environment
     * @param activity         The in-memory representation of a Java class
     * @param isClearBackStack True to clear Activity backstack, otherwise false
     * @param isFinished       True to finish Activity otherwise false
     */
    public void goToActivityAnimInFromBottom(@NonNull Context context, @NonNull Class<?> activity,
                                             Intent intent, boolean isClearBackStack, boolean isFinished) {
        Intent i;
        if (FrameworkUtils.checkIfNull(intent)) {
            i = new Intent(context, activity);
        } else {
            i = intent;
        }
        if (isClearBackStack) {
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }

        if (isFinished && !isFinishing()) {
            finish();
        }
        startActivity(i);
        // transition animation
        overridePendingTransition(R.anim.ui_slide_in_from_bottom, R.anim.ui_slide_out_to_bottom);
    }
}