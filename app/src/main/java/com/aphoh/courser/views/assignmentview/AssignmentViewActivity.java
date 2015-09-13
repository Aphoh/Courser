package com.aphoh.courser.views.assignmentview;

import java.util.List;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;

/**
 * Created by Will on 9/8/15.
 */
@RequiresPresenter(AssignmentViewPresenter.class)
public class AssignmentViewActivity extends NucleusActivity<AssignmentViewPresenter>
        implements AssignmentViewView {

    @Override
    public long getAssignmentId() {
        return 0;
    }

    @Override
    public void publishItems(List<ResponseModel> responseModels) {

    }
}
