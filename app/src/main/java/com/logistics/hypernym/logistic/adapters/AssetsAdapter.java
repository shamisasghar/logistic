package com.logistics.hypernym.logistic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.logistics.hypernym.logistic.R;
import com.logistics.hypernym.logistic.models.AssestData;

import java.util.List;

/**
 * Created by Metis on 17-Dec-17.
 */

public class AssetsAdapter  extends RecyclerView.Adapter<AssetsAdapter.ViewHolder> {


    private List<AssestData> assestData;

    public Context context;

    public AssetsAdapter(List<AssestData> assestData, Context context) {
        this.assestData = assestData;
        this.context = context;
    }


    @Override
    public AssetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asset, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AssetsAdapter.ViewHolder holder, int position) {
        AssestData ad=assestData.get(position);
        holder.itemn.setText(ad.getItemname());

    }


    @Override
    public int getItemCount() {
        return assestData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemn,itemd;
        public ViewHolder(View itemView) {
            super(itemView);

            itemn = (TextView) itemView.findViewById(R.id.itemname);
            itemd = (TextView) itemView.findViewById(R.id.itemdetail);
        }
    }
}
