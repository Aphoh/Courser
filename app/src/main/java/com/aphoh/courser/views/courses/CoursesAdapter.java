package com.aphoh.courser.views.courses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aphoh.courser.R;
import com.aphoh.courser.db.DataInteractor.Course;
import com.aphoh.courser.util.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Will on 9/5/15.
 */
public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {
    Context context;
    List<Course> courses = new ArrayList<>();
    LayoutInflater inflater;

    ItemClickListener<Course> clickListener;
    ItemClickListener<Course> longClickListener;

    public CoursesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_line_status_row, parent, false);
        CourseViewHolder courseViewHolder = new CourseViewHolder(view);
        view.setOnClickListener(courseViewHolder);
        view.setOnLongClickListener(courseViewHolder);
        return courseViewHolder;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.title.setText(course.getName());
        String status = course.getTerm().substring(0, 1).toUpperCase() + course.getTerm().substring(1) + ", " + course.getYear();
        holder.status.setText(status);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener<Course> clickListener) {
        this.clickListener = clickListener;
    }

    public void setLongClickListener(ItemClickListener<Course> longClickListener) {
        this.longClickListener = longClickListener;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        @Bind(R.id.single_line_status_title) TextView title;
        @Bind(R.id.single_line_status_status) TextView status;

        public CourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClick(courses.get(getAdapterPosition()), itemView, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (longClickListener != null)
                longClickListener.onItemClick(courses.get(getAdapterPosition()), itemView, getAdapterPosition());
            return true;
        }
    }
}
