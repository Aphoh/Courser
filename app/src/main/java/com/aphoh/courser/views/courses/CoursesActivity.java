package com.aphoh.courser.views.courses;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.aphoh.courser.R;
import com.aphoh.courser.model.Course;
import com.aphoh.courser.util.DividerItemDecoration;
import com.aphoh.courser.util.ItemClickListener;
import com.aphoh.courser.util.LogUtil;
import com.aphoh.courser.views.assignments.AssignmentsActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;

@RequiresPresenter(CoursesPresenter.class)
public class CoursesActivity extends NucleusActivity<CoursesPresenter> implements CoursesView {
    LogUtil log = new LogUtil(CoursesActivity.class.getSimpleName());
    @Bind(R.id.activity_courses_recyclerview) RecyclerView recyclerView;
    CoursesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setAdapter(adapter = new CoursesAdapter(this));
        adapter.setClickListener(new ItemClickListener<Course>() {
            @Override
            public void onItemClick(Course obj, View view, int position) {
                startActivity(AssignmentsActivity.IntentFactory.withCourseId(CoursesActivity.this, obj.getId()));
            }
        });
        adapter.setLongClickListener(new ItemClickListener<Course>() {
            @Override
            public void onItemClick(final Course obj, View view, int position) {
                new MaterialDialog.Builder(CoursesActivity.this)
                        .title("Delete Course?")
                        .content("You cannot undo this")
                        .positiveText("Delete")
                        .negativeText("Cancel")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                getPresenter().requestCourseDeletion(obj.getId());
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void publishItems(List<Course> courseList) {
        log.d("publishItems: " + Arrays.toString(courseList.toArray()));
        adapter.setCourses(courseList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_courses, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_courses_add:
                new MaterialDialog.Builder(this)
                        .title(R.string.new_course)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("Name", null, false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                                getPresenter().requestCourseCreation(charSequence.toString(), "fall", 2015);
                                log.toast(CoursesActivity.this, "Creating course...");
                            }
                        })
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onError(Throwable error) {
        log.e(Log.getStackTraceString(error));
        log.toast(this, "An error has occurred");
    }
}