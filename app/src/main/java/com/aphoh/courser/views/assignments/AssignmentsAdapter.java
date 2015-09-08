package com.aphoh.courser.views.assignments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aphoh.courser.R;
import com.aphoh.courser.model.Assignment;
import com.aphoh.courser.util.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Will on 9/5/15.
 */
public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.AssignmentViewHolder> {
    Context context;
    List<Assignment> assignments = new ArrayList<>();
    LayoutInflater inflater;

    ItemClickListener<Assignment> clickListener;
    ItemClickListener<Assignment> longClickListener;

    public AssignmentsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_line_status_row, parent, false);
        AssignmentViewHolder assignmentViewHolder = new AssignmentViewHolder(view);
        view.setOnClickListener(assignmentViewHolder);
        view.setOnLongClickListener(assignmentViewHolder);
        return assignmentViewHolder;
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int position) {
        Assignment assignment = assignments.get(position);
        holder.title.setText(assignment.getTitle());
        holder.status.setText(assignment.getCourse().getName());
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener<Assignment> clickListener) {
        this.clickListener = clickListener;
    }

    public void setLongClickListener(ItemClickListener<Assignment> longClickListener) {
        this.longClickListener = longClickListener;
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        @Bind(R.id.single_line_status_title) TextView title;
        @Bind(R.id.single_line_status_status) TextView status;

        public AssignmentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClick(assignments.get(getAdapterPosition()), itemView, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (longClickListener != null)
                longClickListener.onItemClick(assignments.get(getAdapterPosition()), itemView, getAdapterPosition());
            return true;
        }
    }
}
