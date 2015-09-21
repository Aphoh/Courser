package com.aphoh.courser.views.courses;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.aphoh.courser.R;
import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.db.DataInteractor.Course;
import com.aphoh.courser.db.DataInteractor.Student;
import com.aphoh.courser.util.DividerItemDecoration;
import com.aphoh.courser.util.ItemClickListener;
import com.aphoh.courser.util.LogUtil;
import com.aphoh.courser.view.MultiInputMaterialDialogBuilder;
import com.aphoh.courser.view.adapter.SingleLineAdapter;
import com.aphoh.courser.views.assignments.AssignmentsActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;
import rx.functions.Func1;

@RequiresPresenter(CoursesPresenter.class)
public class CoursesActivity extends NucleusActivity<CoursesPresenter> implements CoursesView {
    LogUtil log = new LogUtil(CoursesActivity.class.getSimpleName());
    @Bind(R.id.activity_courses_recyclerview) RecyclerView recyclerView;
    @Bind(R.id.placeholder) TextView placeholder;
    SingleLineAdapter<Course> courseAdapter;
    SingleLineAdapter<Student> studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        courseAdapter = new SingleLineAdapter<>(this,
                new Func1<Course, String>() {
                    @Override
                    public String call(Course course) {
                        return course.getName();
                    }
                },
                new Func1<Course, String>() {
                    @Override
                    public String call(Course course) {
                        return course.getTerm();
                    }
                });

        studentAdapter = new SingleLineAdapter<>(this,
                new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return student.getName();
                    }
                },
                new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return String.valueOf(student.getAge());
                    }
                });

        courseAdapter.setItemClickListener(new ItemClickListener<Course>() {
            @Override
            public void onItemClick(Course obj, View view, int position) {
                startActivity(AssignmentsActivity.IntentFactory.withCourseId(CoursesActivity.this, obj.getId()));
            }
        });

        courseAdapter.setLongClickListener(new ItemClickListener<Course>() {
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

        studentAdapter.setItemClickListener(new ItemClickListener<Student>() {
            @Override
            public void onItemClick(Student obj, View view, int position) {

            }
        });

        studentAdapter.setLongClickListener(new ItemClickListener<Student>() {
            @Override
            public void onItemClick(Student obj, View view, int position) {

            }
        });

        getPresenter().requestCourses();
    }

    @Override
    public void publishCourses(List<Course> courseList) {
        courseAdapter.setData(courseList);
        recyclerView.setAdapter(courseAdapter);
        if (courseList.size() > 0) {
            placeholder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            courseAdapter.setData(courseList);
        } else {
            recyclerView.setVisibility(View.GONE);
            placeholder.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void publishStudents(List<Student> students) {
        recyclerView.setAdapter(studentAdapter);
        if (students.size() > 0) {
            placeholder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            studentAdapter.setData(students);
        } else {
            recyclerView.setVisibility(View.GONE);
            placeholder.setVisibility(View.VISIBLE);
        }
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
            case R.id.menu_courses_new_student:
                new MultiInputMaterialDialogBuilder(this)
                        .addInput(InputType.TYPE_TEXT_VARIATION_PERSON_NAME, null, "Name", new MultiInputMaterialDialogBuilder.InputValidator() {
                            @Override
                            public CharSequence validate(CharSequence input) {
                                if (input.length() < 0) {
                                    return "Name cannot be empty";
                                }
                                return null;
                            }
                        })
                        .addInput(InputType.TYPE_CLASS_NUMBER, null, "Age", new MultiInputMaterialDialogBuilder.InputValidator() {
                            @Override
                            public CharSequence validate(CharSequence input) {
                                if (input.toString().matches("[0-9]+") && input.length() > 0) {
                                    return null;
                                } else {
                                    return "Age must be a number";
                                }
                            }
                        })
                        .inputs(new MultiInputMaterialDialogBuilder.InputsCallback() {
                            @Override
                            public void onInputs(MaterialDialog dialog, List<CharSequence> inputs, boolean allInputsValidated) {
                                String name = inputs.get(0).toString();
                                int age = Integer.valueOf(inputs.get(1).toString());
                                getPresenter().requestStudentCreation(name, age);
                            }
                        })
                        .title(R.string.add_student)
                        .positiveText("Confirm")
                        .negativeText("Cancel")
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
