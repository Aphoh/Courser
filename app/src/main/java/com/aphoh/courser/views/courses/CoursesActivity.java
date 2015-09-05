package com.aphoh.courser.views.courses;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aphoh.courser.R;
import com.aphoh.courser.model.Course;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;

@RequiresPresenter(CoursesPresenter.class)
public class CoursesActivity extends NucleusActivity<CoursesPresenter> implements CoursesView {

    @Bind(R.id.activity_courses_recyclerview) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void publishItems(List<Course> courseList) {

    }

    @Override
    public void onError(Throwable error) {

    }
}
