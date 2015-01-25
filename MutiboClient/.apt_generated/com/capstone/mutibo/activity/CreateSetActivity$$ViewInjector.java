// Generated code from Butter Knife. Do not modify!
package com.capstone.mutibo.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class CreateSetActivity$$ViewInjector {
  public static void inject(Finder finder, final com.capstone.mutibo.activity.CreateSetActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361794, "field 'mCorrectAnswer'");
    target.mCorrectAnswer = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361796, "field 'mAnswer2'");
    target.mAnswer2 = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361793, "field 'mQuestion'");
    target.mQuestion = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361797, "field 'mAnswer3'");
    target.mAnswer3 = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361798, "field 'mExplanation'");
    target.mExplanation = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361795, "field 'mAnswer1'");
    target.mAnswer1 = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361799, "field 'mFinishSetCreation' and method 'createSet'");
    target.mFinishSetCreation = (android.widget.Button) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.createSet();
        }
      });
    target.mCreationSetFields = Finder.listOf(
        (android.widget.EditText) finder.findRequiredView(source, 2131361793, "mCreationSetFields"),
        (android.widget.EditText) finder.findRequiredView(source, 2131361794, "mCreationSetFields"),
        (android.widget.EditText) finder.findRequiredView(source, 2131361795, "mCreationSetFields"),
        (android.widget.EditText) finder.findRequiredView(source, 2131361796, "mCreationSetFields"),
        (android.widget.EditText) finder.findRequiredView(source, 2131361797, "mCreationSetFields"),
        (android.widget.EditText) finder.findRequiredView(source, 2131361798, "mCreationSetFields")
    );  }

  public static void reset(com.capstone.mutibo.activity.CreateSetActivity target) {
    target.mCorrectAnswer = null;
    target.mAnswer2 = null;
    target.mQuestion = null;
    target.mAnswer3 = null;
    target.mExplanation = null;
    target.mAnswer1 = null;
    target.mFinishSetCreation = null;
    target.mCreationSetFields = null;
  }
}
