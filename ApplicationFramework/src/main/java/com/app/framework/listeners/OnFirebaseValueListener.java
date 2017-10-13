package com.app.framework.listeners;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by LJTat on 6/3/2017.
 */

public interface OnFirebaseValueListener {

    /**
     * Interface for when firebase data has changed
     * @param dataSnapshot Updated data
     */
    void onDataChange(DataSnapshot dataSnapshot);

    /**
     * Interface for when there is an error interfacing with firebase
     * @param databaseError Database error
     */
    void onCancelled(DatabaseError databaseError);
}
