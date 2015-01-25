package com.capstone.mutibo.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.capstone.mutibo.activity.R;

public class MusicService extends Service {
	
	final static String MUSIC_SERVICE_TAG = MusicService.class.getName(); 
	
	private MediaPlayer mMediaPlayer = new MediaPlayer();
	
	public MusicService() {}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mMediaPlayer = MediaPlayer.create(this, R.raw.simple_music);
		mMediaPlayer.setLooping(true); // Set looping
		Log.d(MUSIC_SERVICE_TAG, "musicService");

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mMediaPlayer.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		mMediaPlayer.stop();
		mMediaPlayer.release();
	}
}
