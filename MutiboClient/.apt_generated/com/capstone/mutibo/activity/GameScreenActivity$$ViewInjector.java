// Generated code from Butter Knife. Do not modify!
package com.capstone.mutibo.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class GameScreenActivity$$ViewInjector {
  public static void inject(Finder finder, final com.capstone.mutibo.activity.GameScreenActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361819, "field 'mFiftyFiftyPowerUp' and method 'fiftyAndFiftyPowerUpActivate'");
    target.mFiftyFiftyPowerUp = (android.widget.ImageButton) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.fiftyAndFiftyPowerUpActivate();
        }
      });
    view = finder.findRequiredView(source, 2131361811, "field 'mQuestionText'");
    target.mQuestionText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361810, "field 'mActivateDeactivatePowerUp' and method 'activateDeactivatePowerUp'");
    target.mActivateDeactivatePowerUp = (android.widget.ImageButton) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.activateDeactivatePowerUp();
        }
      });
    view = finder.findRequiredView(source, 2131361807, "field 'mSaveLifeLabel'");
    target.mSaveLifeLabel = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131361816, "field 'mExplanationText'");
    target.mExplanationText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361808, "field 'mScoreLabel'");
    target.mScoreLabel = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361821, "field 'mSaveLifePowerUp' and method 'saveLifePowerUpActivate'");
    target.mSaveLifePowerUp = (android.widget.ImageButton) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.saveLifePowerUpActivate();
        }
      });
    view = finder.findRequiredView(source, 2131361820, "field 'mPassToNextSetPowerUp' and method 'passNextPowerUpActivate'");
    target.mPassToNextSetPowerUp = (android.widget.ImageButton) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.passNextPowerUpActivate();
        }
      });
    view = finder.findRequiredView(source, 2131361818, "field 'mNextFinishButton' and method 'next'");
    target.mNextFinishButton = (android.widget.Button) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.next();
        }
      });
    view = finder.findRequiredView(source, 2131361817, "field 'mRatingSet'");
    target.mRatingSet = (android.widget.RatingBar) view;
    view = finder.findRequiredView(source, 2131361809, "field 'mMultiplierLabel'");
    target.mMultiplierLabel = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361812, "method 'checkAnswer'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.checkAnswer((android.widget.Button) p0);
        }
      });
    view = finder.findRequiredView(source, 2131361813, "method 'checkAnswer'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.checkAnswer((android.widget.Button) p0);
        }
      });
    view = finder.findRequiredView(source, 2131361814, "method 'checkAnswer'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.checkAnswer((android.widget.Button) p0);
        }
      });
    view = finder.findRequiredView(source, 2131361815, "method 'checkAnswer'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.checkAnswer((android.widget.Button) p0);
        }
      });
    target.mAnswerButtons = Finder.listOf(
        (android.widget.Button) finder.findRequiredView(source, 2131361812, "mAnswerButtons"),
        (android.widget.Button) finder.findRequiredView(source, 2131361813, "mAnswerButtons"),
        (android.widget.Button) finder.findRequiredView(source, 2131361814, "mAnswerButtons"),
        (android.widget.Button) finder.findRequiredView(source, 2131361815, "mAnswerButtons")
    );    target.mLives = Finder.listOf(
        (android.widget.ImageView) finder.findRequiredView(source, 2131361804, "mLives"),
        (android.widget.ImageView) finder.findRequiredView(source, 2131361805, "mLives"),
        (android.widget.ImageView) finder.findRequiredView(source, 2131361806, "mLives")
    );    target.mPowerUps = Finder.listOf(
        (android.widget.ImageButton) finder.findRequiredView(source, 2131361820, "mPowerUps"),
        (android.widget.ImageButton) finder.findRequiredView(source, 2131361819, "mPowerUps"),
        (android.widget.ImageButton) finder.findRequiredView(source, 2131361821, "mPowerUps")
    );  }

  public static void reset(com.capstone.mutibo.activity.GameScreenActivity target) {
    target.mFiftyFiftyPowerUp = null;
    target.mQuestionText = null;
    target.mActivateDeactivatePowerUp = null;
    target.mSaveLifeLabel = null;
    target.mExplanationText = null;
    target.mScoreLabel = null;
    target.mSaveLifePowerUp = null;
    target.mPassToNextSetPowerUp = null;
    target.mNextFinishButton = null;
    target.mRatingSet = null;
    target.mMultiplierLabel = null;
    target.mAnswerButtons = null;
    target.mLives = null;
    target.mPowerUps = null;
  }
}
