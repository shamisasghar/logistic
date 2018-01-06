package com.logistics.hypernym.logistic.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.logistics.hypernym.logistic.R;
import com.logistics.hypernym.logistic.models.Drawer;
import com.logistics.hypernym.logistic.toolbox.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Metis on 08-Dec-17.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {
    private OnItemClickListener mItemClickListener;
    private List<Drawer> mItems = new ArrayList<>();

    public DrawerAdapter(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drawer, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Drawer item = mItems.get(position);
        holder.iconImage.setImageResource(item.icon);
        holder.titleText.setText(item.title);
        if (item.isSelected) {
            holder.view.setVisibility(View.VISIBLE);
        } else {
            holder.view.setVisibility(View.GONE);
        }
        holder.iconImage.setColorFilter(ContextCompat.getColor(holder.iconImage.getContext(), R.color.colorDrawerIcon));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v, item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mItems != null ? mItems.size() : 0);
    }

    public List<Drawer> getItems() {
        return mItems;
    }

    public void addAll(List<Drawer> collection) {
        mItems.clear();
        if (collection != null)
            mItems.addAll(collection);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView titleText;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            iconImage = (ImageView) itemView.findViewById(R.id.image_icon);
            titleText = (TextView) itemView.findViewById(R.id.text_title);
            view = itemView.findViewById(R.id.view_line);
        }
    }
}
