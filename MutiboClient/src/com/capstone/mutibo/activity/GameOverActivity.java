package com.capstone.mutibo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.amazon.ags.api.AGResponseCallback;
import com.amazon.ags.api.AGResponseHandle;
import com.amazon.ags.api.AmazonGamesClient;
import com.amazon.ags.api.leaderboards.SubmitScoreResponse;

public class GameOverActivity extends AmazonGameCircleAbstract {

	final static String GAME_OVER_SCREEN_TAG = GameOverActivity.class.getName();

	int mScore = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over_layout);

		Bundle extras = getIntent().getExtras();
		mScore = extras.getInt("SCORE", 0);

		TextView scoreLabel = (TextView) findViewById(R.id.gameOverScore);
		scoreLabel.setText(String.valueOf(mScore));

		ButterKnife.inject(this);
	}

	@OnClick(R.id.playAgainButton)
	public void playAgain(View view) {

		AmazonGamesClient amazonClient = (AmazonGamesClient) AmazonGamesClient
				.getInstance();
		if (amazonClient.getPlayerClient().isSignedIn()) {

			lbClient = agsClient.getLeaderboardsClient();
			AGResponseHandle<SubmitScoreResponse> handle = lbClient
					.submitScore(LEADERBOARD_ID, mScore);

			handle.setCallback(new AGResponseCallback<SubmitScoreResponse>() {

				@Override
				public void onComplete(SubmitScoreResponse result) {
					if (result.isError()) {
						Log.d(GAME_OVER_SCREEN_TAG,
								"Error while submitting data to Game Circle: "
										+ result.getError());
					} else {
						Log.d(GAME_OVER_SCREEN_TAG, "Score submitted");
					}

					startPlayAgainActivity();
				}
			});
		} else {
			startPlayAgainActivity();
		}
	}

	@OnClick(R.id.mainMenuButton)
	public void backToMenu(View view) {

		AmazonGamesClient amazonClient = (AmazonGamesClient) AmazonGamesClient
				.getInstance();
		if (amazonClient.getPlayerClient().isSignedIn()) {

			lbClient = agsClient.getLeaderboardsClient();
			AGResponseHandle<SubmitScoreResponse> handle = lbClient
					.submitScore(LEADERBOARD_ID, mScore);

			handle.setCallback(new AGResponseCallback<SubmitScoreResponse>() {

				@Override
				public void onComplete(SubmitScoreResponse result) {
					if (result.isError()) {
						Log.d(GAME_OVER_SCREEN_TAG,
								"Error while submitting data to Game Circle: "
										+ result.getError());
					} else {
						Log.d(GAME_OVER_SCREEN_TAG, "Score submitted");
					}

					startMainMenuActivity();

				}
			});
		} else {
			startMainMenuActivity();
		}

	}

	private void startPlayAgainActivity() {
		Intent play = new Intent(this, GameScreenActivity.class);
		startActivity(play);
		finish();
	}

	private void startMainMenuActivity() {
		Intent mainMenu = new Intent(this, MainMenuActivity.class);
		startActivity(mainMenu);
		finish();
	}

}
