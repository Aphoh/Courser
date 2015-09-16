package com.aphoh.courser.views.assignmentview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aphoh.courser.R;
import com.aphoh.courser.util.ItemClickListener;
import com.aphoh.courser.views.assignmentview.AssignmentViewView.ResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 9/14/15.
 */
public class AssignmentViewAdapter extends RecyclerView.Adapter<AssignmentViewAdapter.ItemViewHolder> {
    List<ResponseModel> data = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    ItemClickListener<ResponseModel> clickListener;

    public AssignmentViewAdapter(Context context, ItemClickListener<ResponseModel> clickListener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_line_status_row, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        view.setOnClickListener(itemViewHolder);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ResponseModel model = data.get(position);
        holder.student.setText(model.getStudent().getName());
        holder.submissionStatus.setText(model.getSubmissionStatus());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ResponseModel> models){
        data = models;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView student;
        TextView submissionStatus;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.onItemClick(data.get(getAdapterPosition()), v, getAdapterPosition());
            }
        }
    }
}
