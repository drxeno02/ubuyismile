package com.blog.ljtatum.ubuyismile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.framework.utilities.FrameworkUtils;
import com.blog.ljtatum.ubuyismile.R;
import com.blog.ljtatum.ubuyismile.model.ChableeData;
import com.blog.ljtatum.ubuyismile.model.ChableeModel;
import com.blog.ljtatum.ubuyismile.utils.Utils;

import java.util.ArrayList;

/**
 * Created by LJTat on 12/31/2017.
 */

public class ChableeItemAdapter extends RecyclerView.Adapter<ChableeItemAdapter.ViewHolder> {

    private final Context mContext;
    private ArrayList<ChableeModel> alItems;

    /**
     * Constructor
     * @param context Interface to global information about an application environment
     * @param alItems List of items to display
     */
    public ChableeItemAdapter(Context context, ArrayList<ChableeModel> alItems) {
        mContext = context;
        this.alItems = alItems;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_a, parent, false);
        return new ChableeItemAdapter.ViewHolder(v);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();

        // set visibility
        if ((!FrameworkUtils.isStringEmpty(alItems.get(position).salePrice) &&
                !FrameworkUtils.isStringEmpty(alItems.get(position).price)) &&
                (Utils.getDollarValue(alItems.get(position).salePrice) > 0) &&
                (Utils.getDollarValue(alItems.get(position).salePrice) <
                Utils.getDollarValue(alItems.get(position).price))) {
            FrameworkUtils.setViewVisible(holder.tvSalePerc);
            // set percent sale value
            holder.tvSalePerc.setText(mContext.getResources().getString(R.string.percent_sale,
                    String.valueOf(Utils.calculatePercSale(Utils.getDollarValue(alItems.get(position).price),
                    Utils.getDollarValue(alItems.get(position).salePrice)))));
        }

        // set values
        holder.tvTitle.setText(alItems.get(position).title);
        holder.tvLabel.setText(alItems.get(position).label);
        holder.tvPrice.setText(alItems.get(position).price);

    }

    @Override
    public int getItemCount() {
        return alItems.size();
    }

    /**
     * Method is used to update trip history data
     *
     * @param alItems List of completed status trips. Populated from model class
     *              {@link com.blog.ljtatum.ubuyismile.model.ChableeModel}
     */
    public void updateData(@NonNull ArrayList<ChableeModel> alItems) {
        if (alItems.size() > 0 && !alItems.isEmpty()) {
            this.alItems = alItems;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSalePerc, tvLabel, tvTitle, tvPrice;
        private final ImageView ivBg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSalePerc = itemView.findViewById(R.id.tv_sale_perc);
            tvLabel = itemView.findViewById(R.id.tv_label);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivBg = itemView.findViewById(R.id.iv_bg);
        }


    }
}
