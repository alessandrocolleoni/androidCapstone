// Generated code from Butter Knife. Do not modify!
package com.capstone.mutibo.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class GameOverActivity$$ViewInjector {
  public static void inject(Finder finder, final com.capstone.mutibo.activity.GameOverActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361802, "method 'playAgain'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.playAgain(p0);
        }
      });
    view = finder.findRequiredView(source, 2131361803, "method 'backToMenu'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.backToMenu(p0);
        }
      });
  }

  public static void reset(com.capstone.mutibo.activity.GameOverActivity target) {
  }
}
