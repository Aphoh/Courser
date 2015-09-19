package com.aphoh.courser.views.assignments;

import android.content.Context;
import android.content.Intent;
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
import com.aphoh.courser.db.DataInteractor.Assignment;
import com.aphoh.courser.util.DividerItemDecoration;
import com.aphoh.courser.util.ItemClickListener;
import com.aphoh.courser.util.LogUtil;
import com.aphoh.courser.views.assignmentview.AssignmentViewActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;

/**
 * Created by Will on 9/5/15.
 */
@RequiresPresenter(AssignmentsPresenter.class)
public class AssignmentsActivity extends NucleusActivity<AssignmentsPresenter> implements AssignmentsView {
    private static final String COURSE_ID = "com.aphoh.courser.courseId";

    LogUtil log = new LogUtil(AssignmentsActivity.class.getSimpleName());

    @Bind(R.id.activity_courses_recyclerview) RecyclerView recyclerView;
    AssignmentsAdapter adapter;
    long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        ButterKnife.bind(this);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(savedInstanceState != null && savedInstanceState.containsKey(COURSE_ID)){
            courseId = savedInstanceState.getLong(COURSE_ID, -1);
        } else if(getIntent() != null && getIntent().hasExtra(COURSE_ID)){
            courseId = getIntent().getLongExtra(COURSE_ID, -1);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setAdapter(adapter = new AssignmentsAdapter(this));
        adapter.setClickListener(new ItemClickListener<Assignment>() {
            @Override
            public void onItemClick(Assignment obj, View view, int position) {
                Intent intent = AssignmentViewActivity.IntentFactor.withCourse(AssignmentsActivity.this, obj.getId());
                startActivity(intent);
            }
        });
        adapter.setLongClickListener(new ItemClickListener<Assignment>() {
            @Override
            public void onItemClick(Assignment obj, View view, int position) {

            }
        });

        getPresenter().requestAssignments(courseId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assignments, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_assignments_add:
                new MaterialDialog.Builder(this)
                        .title(R.string.new_assignment)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("Name", null, false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                                getPresenter().requestCreateAssignment(charSequence.toString());
                                log.toast(AssignmentsActivity.this, "Creating course...");
                            }
                        })
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(COURSE_ID, courseId);
    }

    @Override
    public void publishItems(List<Assignment> assignments) {
        log.d("publish items: " + Arrays.toString(assignments.toArray()));
        adapter.setAssignments(assignments);
    }

    @Override
    public void onError(Throwable throwable) {
        log.e(Log.getStackTraceString(throwable));
        log.toast(this, "An error has occurred");
    }

    public static class IntentFactory {
        public static Intent withCourseId(Context context, long courseId) {
            Intent intent = new Intent(context, AssignmentsActivity.class);
            intent.putExtra(COURSE_ID, courseId);
            return intent;
        }
    }
}
