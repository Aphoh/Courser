package com.aphoh.courser.utils;

import com.aphoh.courser.db.DataInteractor.Assignment;

import org.assertj.core.api.AbstractAssert;

/**
 * Created by Will on 9/19/15.
 */
public class CustomMatchers {


    public static AssignmentMatcher assertThat(Assignment actual) {
        return new AssignmentMatcher(actual);
    }

    public static class AssignmentMatcher extends AbstractAssert<AssignmentMatcher, Assignment> {
        Assignment actual;

        protected AssignmentMatcher(Assignment actual) {
            super(actual, AssignmentMatcher.class);
            this.actual = actual;
        }

        public AssignmentMatcher hasTitle(String title) {
            isNotNull();

            if (!actual.getTitle().equals(title)) {
                failWithMessage("Expected title <%s> but was actually <%s>", title, actual.getTitle());
            }

            return this;
        }

        public AssignmentMatcher hasId(long id) {
            isNotNull();

            if (!(actual.getId() == id)) {
                failWithMessage("Expected id <%d> but was actually <%d>", id, actual.getId());
            }

            return this;
        }
    }
}
