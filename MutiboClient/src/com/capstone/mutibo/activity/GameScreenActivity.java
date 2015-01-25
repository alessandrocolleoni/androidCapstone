package com.capstone.mutibo.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.ButterKnife.Action;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

import com.amazon.ags.api.AGResponseCallback;
import com.amazon.ags.api.AGResponseHandle;
import com.amazon.ags.api.AmazonGamesClient;
import com.amazon.ags.api.achievements.UpdateProgressResponse;
import com.capstone.mutibo.gameObjects.GuestsSetList;
import com.capstone.mutibo.gameObjects.Set;
import com.capstone.mutibo.service.MusicService;
import com.capstone.mutibo.service.SetService;
import com.capstone.mutibo.service.SetServiceReceiver;
import com.capstone.mutibo.service.SetServiceReceiver.Listener;

public class GameScreenActivity extends AmazonGameCircleAbstract implements
		SetServiceReceiver.Listener {

	final static String GAME_SCREEN_TAG = GameScreenActivity.class.getName();

	private ArrayList<Set> remainingSets;
	private Set currentSet;
	private int corretInRow = 0;
	private int multiplier = 1;
	private int score = 0;
	private int passLeft = 2;
	private int fiftyFiftyLeft = 2;
	private int saveLifeLeft = 2;
	private int remainingGuess = 3;
	private boolean guest;
	private boolean saveLifeActivated = false;
	private boolean powerUpActive = false;
	private boolean noSetError = true;

	@InjectView(R.id.currentSetRating)
	RatingBar mRatingSet;

	@InjectView(R.id.currentSetExplanation)
	TextView mExplanationText;

	@InjectView(R.id.questionText)
	TextView mQuestionText;

	@InjectView(R.id.scoreLabel)
	TextView mScoreLabel;

	@InjectView(R.id.multiplierLabel)
	TextView mMultiplierLabel;

	@InjectView(R.id.nextQuestion)
	Button mNextFinishButton;

	@InjectView(R.id.passToNextSet)
	ImageButton mPassToNextSetPowerUp;

	@InjectView(R.id.fiftyAndFifty)
	ImageButton mFiftyFiftyPowerUp;

	@InjectView(R.id.saveLife)
	ImageButton mSaveLifePowerUp;
	
	@InjectView(R.id.powerUpActivateDeactivate)
	ImageButton mActivateDeactivatePowerUp;
	
	@InjectViews({ R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4 })
	List<Button> mAnswerButtons;
	
	@InjectViews({R.id.passToNextSet, R.id.fiftyAndFifty, R.id.saveLife})
	List<ImageButton> mPowerUps;
	
	@InjectViews({ R.id.life1, R.id.life2, R.id.life3 })
	List<ImageView> mLives;
	
	@InjectView(R.id.saveLifeLabel)
	ImageView mSaveLifeLabel;
	
	MediaPlayer mp = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_screen_layout);

		guest = getIntent().getBooleanExtra("isGuest", false);

		Log.d(GAME_SCREEN_TAG, "Game Screen initializing");
		ButterKnife.inject(this);
		mNextFinishButton.setEnabled(false);
		
		// if is an authenticated player, retrieve sets from the server
		if (!guest) {
			Intent retrieveSetIntent = SetService
					.makeSetIntent(getApplicationContext());
			SetServiceReceiver ssr = new SetServiceReceiver(new Handler());
			ssr.setListener((Listener) this);
			retrieveSetIntent.putExtra("receiver", ssr);
			retrieveSetIntent.putExtra("command", "getAllSets");
			Log.d(GAME_SCREEN_TAG, "Retrieving sets");
			startService(retrieveSetIntent);
			mRatingSet.setRating(0);
		} else {
			// else load predefined sets
			Log.d(GAME_SCREEN_TAG, "Retrieving sets from GuestsSetList class");
			remainingSets = GuestsSetList.guestSets();
			currentSet = remainingSets.get(0);
			mPassToNextSetPowerUp.setVisibility(View.GONE);
			mFiftyFiftyPowerUp.setVisibility(View.GONE);
			mSaveLifePowerUp.setVisibility(View.GONE);
			setNextSet();
		}
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if (resultCode == SetService.SETS_RETRIEVED) {
			//sets retrieved
			remainingSets = resultData.getParcelableArrayList("sets");
			Log.d(GAME_SCREEN_TAG, "sets retrieved: " + remainingSets.size());
			Collections.shuffle(remainingSets);
			currentSet = remainingSets.get(0);
			setNextSet();
		}
	}

	@OnClick({ R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4 })
	public void checkAnswer(Button answer) {

		if (mp != null) {
			mp.reset();
			mp.release();
		}

		if (answer.getText().equals(currentSet.getCorrectAnswer())) {
			Log.d(GAME_SCREEN_TAG, "answer correct");
			answer.setBackgroundColor(getResources().getColor(R.color.customGreen));
			score += (10 * multiplier);
			mScoreLabel.setText(String.valueOf(score));
			if (noSetError) {
				multiplier += (multiplier >= 5 ? 0 : 1);
				mMultiplierLabel.setText("x" + multiplier);
				corretInRow++;

				if (isOnline() && AmazonGamesClient.getInstance().getPlayerClient().isSignedIn() && corretInRow % 5 == 0) {
					
					final String achi_id = "in_a_row_" + corretInRow;
					AGResponseHandle<UpdateProgressResponse> handle = achiClient
							.updateProgress(achi_id, 100.0f);
					handle.setCallback(new AGResponseCallback<UpdateProgressResponse>() {

						@Override
						public void onComplete(UpdateProgressResponse result) {
							if (result.isError()) {
								Log.d(GAME_SCREEN_TAG,
										"Error updating achivements: "
												+ result.getError());
							}
						}
					});
				}

			}
			mp = MediaPlayer.create(this, R.raw.correct_answer);
			buttonsCommonOperations();

		} else {

			Log.d(GAME_SCREEN_TAG, "answer wrong");
			noSetError = false;
			answer.setBackgroundColor(getResources().getColor(R.color.customRed));
			mp = MediaPlayer.create(this, R.raw.wrong_answer);
			if (!saveLifeActivated) {
				remainingGuess -= 1;
				multiplier = 1;
				mMultiplierLabel.setText("x" + multiplier);
				mLives.get(remainingGuess).setImageResource(
						R.drawable.ic_life_loss);
			} else {
				// re-enable errors
				saveLifeActivated = false;
				mSaveLifeLabel.setVisibility(View.INVISIBLE);
			}
			
			answer.setEnabled(false);
			if (remainingGuess == 0) {
				Log.d(GAME_SCREEN_TAG, "Game over, set Next to Finish label");
				mNextFinishButton.setText("Game Over!");
				buttonsCommonOperations();
			}
		}
		mp.start();
	}

	private void buttonsCommonOperations() {
		mExplanationText.setText(currentSet.getExplanation());
		mExplanationText.setVisibility(View.VISIBLE);

		// only an authenticated player can rate a set
		if (!guest) {
			mRatingSet.setVisibility(View.VISIBLE);
		}
		mNextFinishButton.setEnabled(true);
		ButterKnife.apply(mAnswerButtons, DISABLE);
	}

	@OnClick(R.id.nextQuestion)
	public void next() {

		// last Set or no guess remaining
		if (remainingSets.size() == 1 || remainingGuess == 0) {

			if (!guest) {
				// going to game over
				Intent gameOver = new Intent(this, GameOverActivity.class);
				gameOver.putExtra("SCORE", score);
				startActivity(gameOver);
				finish();
			} else {
				// going to login screen
				Toast.makeText(
						this,
						"You have completed the Mutibo Guest Demo! Login for more challenges!",
						Toast.LENGTH_SHORT).show();
				Intent loginIntent = new Intent(this, LoginActivity.class);
				startActivity(loginIntent);
				finish();
			}

		} else {
			
			mActivateDeactivatePowerUp.setEnabled(true);
			if (saveLifeActivated) {
				// reset savelife. usable only for one set
				saveLifeActivated = false;
				mSaveLifeLabel.setVisibility(View.INVISIBLE);
			}
			
			if (mRatingSet.getRating() != 0 && !guest) {
				Log.d(GAME_SCREEN_TAG,
						"rating set with value: " + mRatingSet.getRating());
				// call SetService for rating
				Intent postSetRatingIntent = SetService.makeSetIntent(this);
				postSetRatingIntent.putExtra("command", "postSetRating");
				postSetRatingIntent.putExtra("rating", mRatingSet.getRating());
				postSetRatingIntent.putExtra("set", currentSet);
				startService(postSetRatingIntent);
			}
			mRatingSet.setVisibility(View.GONE);
			// reset value for the next set
			mRatingSet.setRating(0);
			// remove from sets
			remainingSets.remove(currentSet);
			mNextFinishButton.setEnabled(false);
			// last Set
			if (remainingSets.size() == 1) {
				mNextFinishButton.setText("Finish!");
			}
			currentSet = remainingSets.get(0);
			setNextSet();
			ButterKnife.apply(mAnswerButtons, ENABLED);
			
			// check alpha
			for (Button ib : mAnswerButtons) {
				if (ib.getAlpha() == 0) {
					ib.setAlpha(1.0f);
				}
			}
			
			// reset for next set
			noSetError = true;
		}

	}

	public void setNextSet() {

		mQuestionText.setText(currentSet.getQuestion());

		// save next set answers and mix them
		ArrayList<String> nextSetAnswers = new ArrayList<String>();
		nextSetAnswers.add(currentSet.getAnswer1());
		nextSetAnswers.add(currentSet.getAnswer2());
		nextSetAnswers.add(currentSet.getAnswer3());
		nextSetAnswers.add(currentSet.getCorrectAnswer());

		Collections.shuffle(nextSetAnswers);

		for (Button answerToSet : mAnswerButtons) {
			// set initial color background and text
			answerToSet.setBackgroundColor(getResources().getColor(
					R.color.customBlue));
			answerToSet.setText(nextSetAnswers.get(0));
			nextSetAnswers.remove(0);
		}

		ButterKnife.apply(mAnswerButtons, VISIBLE);
		mExplanationText.setText(currentSet.getExplanation());
		mExplanationText.setVisibility(View.GONE);

	}

	@OnClick(R.id.passToNextSet)
	public void passNextPowerUpActivate() {
		passLeft -= 1;
		mPassToNextSetPowerUp.setEnabled(passLeft > 0);
		if (!mPassToNextSetPowerUp.isEnabled()) {
			mPassToNextSetPowerUp.setColorFilter(Color.GRAY);
		}
		
		next();	
		activateDeactivatePowerUp();
	}

	@OnClick(R.id.fiftyAndFifty)
	public void fiftyAndFiftyPowerUpActivate() {
		fiftyFiftyLeft -= 1;
		mFiftyFiftyPowerUp.setEnabled(fiftyFiftyLeft > 0);
		if (!mFiftyFiftyPowerUp.isEnabled()) {
			mFiftyFiftyPowerUp.setColorFilter(Color.GRAY);
		}
		ArrayList<Button> tmp = new ArrayList<Button>();

		for (Button answer : mAnswerButtons) {
			if (answer.getText().equals(currentSet.getCorrectAnswer())) {
				continue;
			} else {
				tmp.add(answer);
			}
		}
		tmp.remove(new Random().nextInt(tmp.size()));
		mActivateDeactivatePowerUp.setEnabled(false);
		activateDeactivatePowerUp();
		
		final ArrayList<Button> toHide = new ArrayList<Button>();
		toHide.addAll(tmp);
		for (final Button b : toHide) {
			b.animate().alpha(0).setDuration(250).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					ButterKnife.apply(toHide, HIDE);
				}
			});
		}
		
	}

	@OnClick(R.id.saveLife)
	public void saveLifePowerUpActivate() {
		saveLifeLeft -= 1;
		saveLifeActivated = true;
		mSaveLifeLabel.setVisibility(View.VISIBLE);
		mSaveLifePowerUp.setEnabled(saveLifeLeft > 0);
		if (!mSaveLifePowerUp.isEnabled()) {
			mSaveLifePowerUp.setColorFilter(Color.GRAY);
			mSaveLifePowerUp.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
			mSaveLifePowerUp.invalidate();
		}
		activateDeactivatePowerUp();
		mActivateDeactivatePowerUp.setEnabled(false);
	}

	@OnClick(R.id.powerUpActivateDeactivate)
	public void activateDeactivatePowerUp() {

		// clicked on activate/deactivate. first value is false (deactivate)
		powerUpActive = !powerUpActive;
		
		final float scale = getResources().getDisplayMetrics().density;
		
		if (powerUpActive) {
			// power ups menu animation
			int movePassNext = (int) ((-1) * 37 * scale + 0.5f);
			int moveFifty = (int) ((-1) * 74 * scale + 0.5f);
			int moveSaveLife = (int) ((-1) * 111 * scale + 0.5f);
			mPassToNextSetPowerUp.animate().translationYBy(movePassNext)
					.setDuration(250)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationStart(Animator animation) {
							mPassToNextSetPowerUp.setVisibility(View.VISIBLE);
						}

						@Override
						public void onAnimationEnd(Animator animation) {
							mPassToNextSetPowerUp.setEnabled(passLeft > 0);
						}
					});

			mFiftyFiftyPowerUp.animate().translationYBy(moveFifty)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationStart(Animator animation) {
							mFiftyFiftyPowerUp.setVisibility(View.VISIBLE);
						}

						@Override
						public void onAnimationEnd(Animator animation) {
							mFiftyFiftyPowerUp.setEnabled(fiftyFiftyLeft > 0);
						}
					});

			mSaveLifePowerUp.animate().translationYBy(moveSaveLife).setDuration(250)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationStart(Animator animation) {
							mSaveLifePowerUp.setVisibility(View.VISIBLE);
						}

						@Override
						public void onAnimationEnd(Animator animation) {
							mSaveLifePowerUp.setEnabled(saveLifeLeft > 0);
						}
					});

		} else {
			
			int movePassNext = (int) (37 * scale + 0.5f);
			int moveFifty = (int) (74 * scale + 0.5f);
			int moveSaveLife = (int) (111 * scale + 0.5f);
			mPassToNextSetPowerUp.animate().translationYBy(movePassNext).setDuration(250)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mPassToNextSetPowerUp.setEnabled(false);
							mPassToNextSetPowerUp.setVisibility(View.GONE);
						}
					});

			mFiftyFiftyPowerUp.animate().translationYBy(moveFifty).setDuration(250)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mFiftyFiftyPowerUp.setEnabled(false);
							mFiftyFiftyPowerUp.setVisibility(View.GONE);
						}
					});

			mSaveLifePowerUp.animate().translationYBy(moveSaveLife).setDuration(250)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mSaveLifePowerUp.setEnabled(false);
							mSaveLifePowerUp.setVisibility(View.GONE);
						}
					});
		}
	}

	static final Action<View> DISABLE = new Action<View>() {

		@Override
		public void apply(View v, int arg1) {
			v.setEnabled(false);
		}
	};

	static final Action<View> ENABLED = new Action<View>() {

		@Override
		public void apply(View v, int arg1) {
			v.setEnabled(true);
		}
	};

	static final Action<View> VISIBLE = new Action<View>() {

		@Override
		public void apply(View v, int arg1) {
			v.setVisibility(View.VISIBLE);
		}
	};

	static final Action<View> HIDE = new Action<View>() {

		@Override
		public void apply(View v, int arg1) {
			v.setVisibility(View.INVISIBLE);
		}
	};

	static final Action<View> GONE = new Action<View>() {

		@Override
		public void apply(View v, int arg1) {
			v.setVisibility(View.GONE);
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		startService(new Intent(this, MusicService.class));
	}

	@Override
	public void onRestart() {
		super.onRestart();
		startService(new Intent(this, MusicService.class));
	}

	@Override
	public void onPause() {
		super.onPause();
		stopService(new Intent(this, MusicService.class));
	}

	@Override
	public void onStop() {
		super.onStop();
		stopService(new Intent(this, MusicService.class));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopService(new Intent(this, MusicService.class));
	}

}
