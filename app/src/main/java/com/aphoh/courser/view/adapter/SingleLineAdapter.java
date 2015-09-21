package com.aphoh.courser.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aphoh.courser.R;
import com.aphoh.courser.util.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Func1;

/**
 * Created by Will on 9/20/15.
 */
public class SingleLineAdapter<T> extends RecyclerView.Adapter<SingleLineAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<T> data = new ArrayList<>();
    Func1<T, String> titleGetter, statusGetter;
    ItemClickListener<T> itemClickListener, longClickListener;

    public SingleLineAdapter(Context context, ItemClickListener<T> itemClickListener, ItemClickListener<T> longClickListener, Func1<T, String> titleGetter, Func1<T, String> statusGetter) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.itemClickListener = itemClickListener;
        this.longClickListener = longClickListener;
        this.titleGetter = titleGetter;
        this.statusGetter = statusGetter;
    }

    public SingleLineAdapter(Context context, Func1<T, String> titleGetter, Func1<T, String> statusGetter) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.titleGetter = titleGetter;
        this.statusGetter = statusGetter;
    }

    @Override
    public ViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.single_line_status_row, parent, false);
        ViewHolder<T> vh = new ViewHolder<T>(v);
        v.setOnClickListener(vh);
        v.setOnLongClickListener(vh);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = data.get(position);
        holder.title.setText(titleGetter.call(item));
        holder.status.setText(statusGetter.call(item));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setLongClickListener(ItemClickListener<T> longClickListener) {
        this.longClickListener = longClickListener;
    }

    class ViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @Bind(R.id.single_line_status_title) TextView title;
        @Bind(R.id.single_line_status_status) TextView status;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null)
                itemClickListener.onItemClick(data.get(getAdapterPosition()), v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if(longClickListener != null) {
                longClickListener.onItemClick(data.get(getAdapterPosition()), v, getAdapterPosition());
                return true;
            }
            return false;
        }
    }
}
