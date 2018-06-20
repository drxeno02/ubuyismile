package com.blog.ljtatum.ubuyismile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.databases.ItemDatabaseModel;
import com.blog.ljtatum.ubuyismile.enums.Enum;
import com.blog.ljtatum.ubuyismile.interfaces.OnClickAdapterListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LJTat on 12/31/2017.
 */

public class ItemAutoCompletedAdapter extends ArrayAdapter {

    private static final String COLOR_CODE_AMAZON = "[A]";
    private static final String COLOR_CODE_CHABLEE = "[C}";

    private Context mContext;
    private List<ItemDatabaseModel> alItems;
    private List<ItemDatabaseModel> alItemsTemp;
    private ListFilter listFilter = new ListFilter();


    // custom callback
    private static OnClickAdapterListener mOnClickAdapterListener;

    /**
     * Method is used to set callback for when item is clicked
     *
     * @param listener Callback for when item is clicked
     */
    public static void onClickAdapterListener(OnClickAdapterListener listener) {
        mOnClickAdapterListener = listener;
    }

    /**
     * Constructor
     *
     * @param context  Interface to global information about an application environment
     * @param resource The auto complete view
     * @param items    List of items to filter for auto complete
     */
    public ItemAutoCompletedAdapter(@NonNull Context context, int resource, @NonNull List<ItemDatabaseModel> items) {
        super(context, resource, items);
        mContext = context;
        alItems = items;
    }

    @Override
    public int getCount() {
        return alItems.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return alItems.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (FrameworkUtils.checkIfNull(convertView)) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_auto_complete, parent, false);
        }

        // instantiate views
        TextView tvColorCode = convertView.findViewById(R.id.tv_color_code);
        TextView tvTitle = convertView.findViewById(R.id.tv_title);
        ImageView ivItemCategoryIcon = convertView.findViewById(R.id.iv_item_category_icon);

        // set color code
        if (alItems.get(position).itemType.equalsIgnoreCase(Enum.ItemType.AMAZON.toString())) {
            tvColorCode.setText(COLOR_CODE_AMAZON);
            tvColorCode.setTextColor(ContextCompat.getColor(mContext, R.color.material_orange_100_color_code));
        } else if (alItems.get(position).itemType.equalsIgnoreCase(Enum.ItemType.CHABLEE.toString())) {
            tvColorCode.setText(COLOR_CODE_CHABLEE);
            tvColorCode.setTextColor(ContextCompat.getColor(mContext, R.color.material_pink_100_color_code));
        }

        // set category icon
        if (alItems.get(position).itemType.equalsIgnoreCase(Enum.ItemType.AMAZON.toString())) {

        } else if (alItems.get(position).itemType.equalsIgnoreCase(Enum.ItemType.CHABLEE.toString())) {
            if (alItems.get(position).category.equalsIgnoreCase(
                    com.app.amazon.framework.enums.Enum.ItemCategoryChablee.CROWNS.toString())) {
                // set icon
                ivItemCategoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.crowns));
            } else if (alItems.get(position).category.equalsIgnoreCase(
                    com.app.amazon.framework.enums.Enum.ItemCategoryChablee.RINGS.toString())) {
                // set icon
                ivItemCategoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.rings));
            } else if (alItems.get(position).category.equalsIgnoreCase(
                    com.app.amazon.framework.enums.Enum.ItemCategoryChablee.NECKLACES.toString())) {
                // set icon
                ivItemCategoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.necklaces));
            } else if (alItems.get(position).category.equalsIgnoreCase(
                    com.app.amazon.framework.enums.Enum.ItemCategoryChablee.ROCKS.toString())) {
                // set icon
                ivItemCategoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.rocks));
            }
        }

        // set values
        tvTitle.setText(alItems.get(position).title);

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    /**
     * Embedded class that uses filter constrains data with a filtering pattern
     */
    public class ListFilter extends Filter {
        private final Object lock = new Object();

        @SuppressWarnings("SuspiciousMethodCalls")
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (FrameworkUtils.checkIfNull(alItemsTemp)) {
                synchronized (lock) {
                    alItemsTemp = new ArrayList<>(alItems);
                }
            }

            if (FrameworkUtils.checkIfNull(prefix) || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = alItemsTemp;
                    results.count = alItemsTemp.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();
                ArrayList<ItemDatabaseModel> matchValues = new ArrayList<>();

                for (ItemDatabaseModel dataItem : alItemsTemp) {
                    if (!matchValues.contains(dataItem.itemId) &&
                            dataItem.title.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }
                results.values = matchValues;
                results.count = matchValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (!FrameworkUtils.checkIfNull(results.values)) {
                alItems = (ArrayList<ItemDatabaseModel>) results.values;
            } else {
                alItems = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
