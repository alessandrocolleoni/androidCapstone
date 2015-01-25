package com.capstone.mutibo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.amazon.ags.api.AmazonGamesClient;
import com.amazon.identity.auth.device.AuthError;
import com.amazon.identity.auth.device.authorization.api.AmazonAuthorizationManager;
import com.amazon.identity.auth.device.shared.APIListener;

public class MainMenuActivity extends AmazonGameCircleAbstract {
	
	final static String MENU_SCREEN_TAG = MainMenuActivity.class.getName();
	
	private AmazonAuthorizationManager mAuthManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_layout);
		mAuthManager = new AmazonAuthorizationManager(this, Bundle.EMPTY);
		ButterKnife.inject(this);
	}
	
	@OnClick(R.id.playButton)
	public void play(View view) {
		Intent play = new Intent(this, GameScreenActivity.class);
		startActivity(play);
	}
	
	@OnClick(R.id.createSetButton)
	public void createSet(View view) {
		Intent createSet = new Intent(this, CreateSetActivity.class);
		startActivity(createSet);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu_activity_contextmenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.logout:
			// logout from amazon
			if (isOnline()) {
				mAuthManager.clearAuthorizationState(new APIListener() {
	                @Override
	                public void onSuccess(Bundle results) {
	                	AmazonGamesClient.shutdown();
	                	Intent loginIntent = new Intent(MainMenuActivity.this.getApplicationContext(), LoginActivity.class);
	        			startActivity(loginIntent);
	        			finish();
	                }
	                @Override
	                public void onError(AuthError authError) {
	                    Log.d(MENU_SCREEN_TAG, authError.getMessage());
	                }
	            });
			} else {
				Intent loginIntent = new Intent(MainMenuActivity.this.getApplicationContext(), LoginActivity.class);
    			startActivity(loginIntent);
    			finish();
			}
			return true;
		case R.id.gameCircle:
			if (isOnline() && agsClient.getPlayerClient().isSignedIn()) {
				agsClient.showGameCircle();
			} else {
				agsClient.showSignInPage();
			}
			return true;
		case R.id.help:
			Intent helpIntent = new Intent(this, HelpScreenActivity.class);
			startActivity(helpIntent);
			return true;
		default:
			return false;
		}
	}
	
	@OnClick(R.id.achievementsImageButton)
	public void achievements() {
		if (isOnline() && agsClient.getPlayerClient().isSignedIn()) {
			achiClient.showAchievementsOverlay();
		} else {
			Toast.makeText(this, "Login with Amazon Game Circle for achievements!", Toast.LENGTH_SHORT).show();
		}
	}
	
	@OnClick(R.id.leaderboardImageButton)
	public void leaderboards() {
		if (isOnline() && agsClient.getPlayerClient().isSignedIn()) { 
			lbClient.showLeaderboardsOverlay();
		} else {
			Toast.makeText(this, "Login with Amazon Game Circle for leaderboards!", Toast.LENGTH_SHORT).show();
		}
	}
}
