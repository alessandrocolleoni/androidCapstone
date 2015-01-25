package com.capstone.mutibo.activity;

import java.util.EnumSet;

import com.amazon.ags.api.AmazonGamesCallback;
import com.amazon.ags.api.AmazonGamesClient;
import com.amazon.ags.api.AmazonGamesFeature;
import com.amazon.ags.api.AmazonGamesStatus;
import com.amazon.ags.api.achievements.AchievementsClient;
import com.amazon.ags.api.leaderboards.LeaderboardsClient;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public abstract class AmazonGameCircleAbstract extends Activity {
	
	final static String AMAZON_GAME_CIRCLE_TAG = AmazonGameCircleAbstract.class.getName();
	
	final static String LEADERBOARD_ID = "mutibo_leaderboard_id";
	
	AmazonGamesClient agsClient;

	LeaderboardsClient lbClient;
	
	AchievementsClient achiClient;
	
	AmazonGamesCallback callback = new AmazonGamesCallback() {
		@Override
		public void onServiceNotReady(AmazonGamesStatus status) {
			Log.d(AMAZON_GAME_CIRCLE_TAG, status.toString());
		}

		@Override
		public void onServiceReady(AmazonGamesClient amazonGamesClient) {
			agsClient = amazonGamesClient;
			lbClient = agsClient.getLeaderboardsClient();
			achiClient = agsClient.getAchievementsClient();
		}
	};

	EnumSet<AmazonGamesFeature> myGameFeatures = EnumSet
			.of(AmazonGamesFeature.Leaderboards, AmazonGamesFeature.Achievements);
	
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    AmazonGamesClient.initialize(this, callback, myGameFeatures);	    
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	    if (agsClient != null) {
	        AmazonGamesClient.release();
	    }
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}
	
}
