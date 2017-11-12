package com.app.amazon.framework.interfaces;

/**
 * Created by leonard on 10/20/2017.
 */

public interface OnAWSRequestListener {

    /**
     * Interface for when request to AWS is successful
     */
    void onAWSSuccess(String response);
}
