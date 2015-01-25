package com.capstone.mutibo.service;

import java.util.ArrayList;

import retrofit.RetrofitError;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.capstone.mutibo.gameObjects.Rating;
import com.capstone.mutibo.gameObjects.Set;
import com.capstone.mutibo.rest.api.MutiboApi;
import com.capstone.mutibo.rest.api.MutiboApiManager;

public class SetService extends IntentService {

	final static String SET_SERVICE_TAG = SetService.class.getName();

	public final static int SETS_RETRIEVED = 0;
	public final static int SET_SAVED = 1000;
	public final static int SET_NOT_SAVED = 1999;

	public SetService() {
		super("SetService");
	}

	public SetService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		final MutiboApi mutiboApi = MutiboApiManager.getOrShowLogin(this);

		String command = intent.getStringExtra("command");

		switch (command) {

		case "getAllSets":
			ResultReceiver receiverSetRetrieve = (ResultReceiver) intent
					.getParcelableExtra("receiver");
			try {
				ArrayList<Set> gameSetList = mutiboApi.getListSet();
				Bundle bundleRetrieveSet = new Bundle();
				bundleRetrieveSet.putParcelableArrayList("sets", gameSetList);
				receiverSetRetrieve.send(SETS_RETRIEVED, bundleRetrieveSet);
			} catch (RetrofitError re) {
				Log.d(SET_SERVICE_TAG, re.getMessage());
			}
			break;

		case "postSetRating":
			
			Set currentSet = intent.getParcelableExtra("set");
			
			Log.d(SET_SERVICE_TAG, "Post set's (" + currentSet.getId() + ") rating");
			
			float rating = intent.getFloatExtra("rating", 0);

			Rating currentSetRating = null;

			try {
				currentSetRating = mutiboApi.rateSet(currentSet, rating);
			} catch (RetrofitError re) {
				Log.d(SET_SERVICE_TAG, re.getMessage());
				break;
			}

			if (currentSetRating != null) {
				// user > 5 because here we have 10 users stored on the db (so is 50% of the total users)
				if (currentSetRating.getUsersNameRatedSet().size() > 5
						&& currentSetRating.getAvgRatingValue() < 1.5f) {
					try {
						Log.d(SET_SERVICE_TAG, "Deleting rating: " + currentSetRating.getId());
						// first we delete the related rating for integrity
						mutiboApi.deleteRating(currentSetRating.getId());
						// then our set
						Log.d(SET_SERVICE_TAG, "Deleting set: " + currentSet.getId());
						mutiboApi.deleteSet(currentSet.getId());
					} catch (RetrofitError re) {
						Log.d(SET_SERVICE_TAG, re.getMessage());
					}
				}
			}

			break;

		case "postSetToSave":
			Set setToSave = intent.getParcelableExtra("setToSave");
			Log.d(SET_SERVICE_TAG, "Saving set");
			ResultReceiver receiverCreateSet = (ResultReceiver) intent
					.getParcelableExtra("receiver");
			Bundle bundleSaveSet = new Bundle();
			try {
				mutiboApi.addSet(setToSave);
			} catch (RetrofitError re) {
				Log.d(SET_SERVICE_TAG, re.getMessage());
				receiverCreateSet.send(SET_NOT_SAVED, bundleSaveSet);
				break;
			}
			
			Log.d(SET_SERVICE_TAG, "Set created" + setToSave.getId());
			receiverCreateSet.send(SET_SAVED, bundleSaveSet);
			break;

		default:
			break;
		}
	}

	public static Intent makeSetIntent(Context context) {
		return new Intent(context, SetService.class);
	}
}
