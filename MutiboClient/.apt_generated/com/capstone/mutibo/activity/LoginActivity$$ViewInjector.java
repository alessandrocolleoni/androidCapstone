// Generated code from Butter Knife. Do not modify!
package com.capstone.mutibo.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LoginActivity$$ViewInjector {
  public static void inject(Finder finder, final com.capstone.mutibo.activity.LoginActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361829, "field 'mLoginButton' and method 'login'");
    target.mLoginButton = (android.widget.Button) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.login();
        }
      });
    view = finder.findRequiredView(source, 2131361828, "field 'mPassword'");
    target.mPassword = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361827, "field 'mUser'");
    target.mUser = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361830, "method 'playDemo'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.playDemo();
        }
      });
  }

  public static void reset(com.capstone.mutibo.activity.LoginActivity target) {
    target.mLoginButton = null;
    target.mPassword = null;
    target.mUser = null;
  }
}
