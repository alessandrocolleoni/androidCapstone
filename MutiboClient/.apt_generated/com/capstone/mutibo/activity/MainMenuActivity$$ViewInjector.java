// Generated code from Butter Knife. Do not modify!
package com.capstone.mutibo.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainMenuActivity$$ViewInjector {
  public static void inject(Finder finder, final com.capstone.mutibo.activity.MainMenuActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361834, "method 'leaderboards'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.leaderboards();
        }
      });
    view = finder.findRequiredView(source, 2131361832, "method 'createSet'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.createSet(p0);
        }
      });
    view = finder.findRequiredView(source, 2131361831, "method 'play'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.play(p0);
        }
      });
    view = finder.findRequiredView(source, 2131361833, "method 'achievements'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.achievements();
        }
      });
  }

  public static void reset(com.capstone.mutibo.activity.MainMenuActivity target) {
  }
}
