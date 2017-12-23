package com.app.framework.utilities.firebase;


import android.support.annotation.NonNull;
import android.util.Log;

import com.app.framework.listeners.OnFirebaseValueListener;
import com.app.framework.utilities.FrameworkUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by LJTat on 6/3/2017.
 */

public class FirebaseUtils {
    private static final String TAG = FirebaseUtils.class.getSimpleName();

    private static final String ASIN = "ASIN";
    private static final String CHABLEE = "CHABLEE";
    private static final String ITEM = "ITEM";

    private static DatabaseReference mWriteDbReference;
    private static DatabaseReference mQueryDbReference;

    // listeners
    private static OnFirebaseValueListener mFirebaseValueListener;

    /**
     * Constructor
     */
    public FirebaseUtils() {
        mWriteDbReference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Method is used to set callback to listen to value changes in Firebase DB
     *
     * @param listener Callback to listen to value changes in Firebase DB
     */
    public static void onFirebaseValueListener(OnFirebaseValueListener listener) {
        mFirebaseValueListener = listener;
    }

    /**
     * Method is used to retrieve a list of values from Firebase DB (AMAZON)
     *
     * @param itemCategory Category for item
     */
    public static void retrieveItemAmazon(String itemCategory) {

        // database reference
        mQueryDbReference = FirebaseDatabase.getInstance().getReference(itemCategory).child(ASIN);
        mQueryDbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("TEST", "dataSnapshot= " + dataSnapshot);
                if (!FrameworkUtils.checkIfNull(mFirebaseValueListener)) {
                    mFirebaseValueListener.onRetrieveDataChange(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (!FrameworkUtils.checkIfNull(databaseError) &&
                        !FrameworkUtils.isStringEmpty(databaseError.getMessage())) {
                    if (!FrameworkUtils.checkIfNull(mFirebaseValueListener)) {
                        mFirebaseValueListener.onRetrieveDataError(databaseError);
                    }
                }
            }
        });
    }

    /**
     * Method is used to retrieve a list of values from Firebase DB (Chablee)
     *
     * @param itemCategory Category for item
     */
    public static void retrieveItemChablee(String itemCategory) {

        // database reference
        mQueryDbReference = FirebaseDatabase.getInstance().getReference(CHABLEE).child(itemCategory).child(ITEM);
        mQueryDbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!FrameworkUtils.checkIfNull(mFirebaseValueListener)) {
                    mFirebaseValueListener.onRetrieveDataChange(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (!FrameworkUtils.checkIfNull(databaseError) &&
                        !FrameworkUtils.isStringEmpty(databaseError.getMessage())) {
                    if (!FrameworkUtils.checkIfNull(mFirebaseValueListener)) {
                        mFirebaseValueListener.onRetrieveDataError(databaseError);
                    }
                }
            }
        });
    }

    /**
     * Method is used to add a list of values to Firebase DB
     *
     * @param alData   Data to store to Firebase
     * @param category Category for item
     */
    public static void addValues(@NonNull List<?> alData, String category) {

        // database reference
        mQueryDbReference = FirebaseDatabase.getInstance().getReference(category);
        if (!FrameworkUtils.checkIfNull(mWriteDbReference)) {
            // set value
            mWriteDbReference.child(category).child(ASIN).setValue(alData);
            mWriteDbReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!FrameworkUtils.checkIfNull(mFirebaseValueListener)) {
                        mFirebaseValueListener.onUpdateDataChange(dataSnapshot);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    if (!FrameworkUtils.checkIfNull(databaseError) && !FrameworkUtils.isStringEmpty(databaseError.getMessage())) {
                        if (!FrameworkUtils.checkIfNull(mFirebaseValueListener)) {
                            mFirebaseValueListener.onUpdateDatabaseError(databaseError);
                        }
                    }
                }
            });
        }
    }

    /**
     * Method is used to add a list of values to Firebase DB
     *
     * @param alData   Data to store to Firebase
     * @param category Category for item
     */
    public static void addValuesChab(@NonNull List<?> alData, String category) {

        // database reference
        mQueryDbReference = FirebaseDatabase.getInstance().getReference(CHABLEE);
        if (!FrameworkUtils.checkIfNull(mWriteDbReference)) {
            // set value
            mWriteDbReference.child(CHABLEE).child(category).child(ITEM).setValue(alData);
            mWriteDbReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!FrameworkUtils.checkIfNull(mFirebaseValueListener)) {
                        mFirebaseValueListener.onUpdateDataChange(dataSnapshot);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    if (!FrameworkUtils.checkIfNull(databaseError) && !FrameworkUtils.isStringEmpty(databaseError.getMessage())) {
                        if (!FrameworkUtils.checkIfNull(mFirebaseValueListener)) {
                            mFirebaseValueListener.onUpdateDatabaseError(databaseError);
                        }
                    }
                }
            });
        }
    }

}
