package com.example.sd_lab2;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemHolder> {
    private final ObjectList objectList;
    private final MainActivity activity;

    public ListAdapter(ObjectList objectList, MainActivity activity) {
        this.objectList = objectList;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.line, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        ObjectList.Data data = objectList.getData(position);

        holder.techName.setText(data.name);

        if(holder.getItemViewType() == 0)
            holder.imageView.setImageBitmap(data.graphicBitmap);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openViewPager(position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        ObjectList.Data data = objectList.getData(position);

        return data.graphicBitmap == null ? 1 : 0;
    }



    public class ItemHolder extends RecyclerView.ViewHolder {
        private final TextView techName;
        private final ImageView imageView;
        private final LinearLayout linearLayout;

        ItemHolder(View itemView) {
            super(itemView);
            techName = itemView.findViewById(R.id.tech);
            imageView = itemView.findViewById(R.id.tech_image);
            linearLayout = itemView.findViewById(R.id.line);
        }
    }
}