package com.aphoh.courser.views.assignmentview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aphoh.courser.R;
import com.aphoh.courser.util.DividerItemDecoration;
import com.aphoh.courser.util.ItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;

/**
 * Created by Will on 9/8/15.
 */
@RequiresPresenter(AssignmentViewPresenter.class)
public class AssignmentViewActivity extends NucleusActivity<AssignmentViewPresenter>
        implements AssignmentViewView {
    private static final String ASSIGNMENT_ID = "com.aphoh.courser.assignmentid";

    @Bind(R.id.activity_courses_recyclerview) RecyclerView recyclerView;

    private AssignmentViewAdapter adapter;
    private long assignmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        adapter = new AssignmentViewAdapter(this, new ItemClickListener<ResponseModel>() {
            @Override
            public void onItemClick(ResponseModel obj, View view, int position) {
                //TODO: Next thing
            }
        });
        recyclerView.setAdapter(adapter);

        if(getIntent().getExtras() != null) {
            assignmentId = getIntent().getLongExtra(ASSIGNMENT_ID, -1);
            getPresenter().request(assignmentId);
        } else {

        }
    }

    @Override
    public void publishItems(List<ResponseModel> responseModels) {
        adapter.setData(responseModels);
    }

    @Override
    public void publishError(Throwable error) {

    }

    public static class IntentFactor{
        public static Intent withCourse(Context context, long assignmentId){
            Intent intent = new Intent(context, AssignmentViewActivity.class);
            intent.putExtra(ASSIGNMENT_ID, assignmentId);
            return intent;
        }
    }
}
