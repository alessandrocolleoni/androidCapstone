package com.capstone.mutibo.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

import com.capstone.mutibo.gameObjects.Set;
import com.capstone.mutibo.service.SetService;
import com.capstone.mutibo.service.SetServiceReceiver;
import com.capstone.mutibo.service.SetServiceReceiver.Listener;

public class CreateSetActivity extends Activity implements
		SetServiceReceiver.Listener {

	AlertDialog mAlertDialog;

	@InjectView(R.id.setCreationQuestion)
	EditText mQuestion;

	@InjectView(R.id.setCreationCorrectAnswer)
	EditText mCorrectAnswer;

	@InjectView(R.id.setCreationWrongAnswer1)
	EditText mAnswer1;

	@InjectView(R.id.setCreationWrongAnswer2)
	EditText mAnswer2;

	@InjectView(R.id.setCreationWrongAnswer3)
	EditText mAnswer3;

	@InjectView(R.id.setCreationExplanation)
	EditText mExplanation;

	// Used to control if a field is empty
	@InjectViews({ R.id.setCreationQuestion, R.id.setCreationCorrectAnswer,
			R.id.setCreationWrongAnswer1, R.id.setCreationWrongAnswer2,
			R.id.setCreationWrongAnswer3, R.id.setCreationExplanation })
	List<EditText> mCreationSetFields;

	@InjectView(R.id.setCreationFinishButton)
	Button mFinishSetCreation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_set_layout);
		ButterKnife.inject(this);
	}

	@OnClick(R.id.setCreationFinishButton)
	public void createSet() {

		boolean canSaveSet = true;

		for (EditText et : mCreationSetFields) {
			// control if all fields are filled
			if (et.getText().toString().isEmpty()) {
				et.setHintTextColor(getResources().getColor(R.color.customRed));
				canSaveSet = false;
			}
		}

		if (canSaveSet) {

			// populate set to save
			Set setToSave = new Set();
			setToSave.setQuestion(mQuestion.getText().toString());
			setToSave.setCorrectAnswer(mCorrectAnswer.getText().toString());
			setToSave.setAnswer1(mAnswer1.getText().toString());
			setToSave.setAnswer2(mAnswer2.getText().toString());
			setToSave.setAnswer3(mAnswer3.getText().toString());
			setToSave.setExplanation(mExplanation.getText().toString());

			// call SetService for POST the set
			Intent postSetRatingIntent = SetService.makeSetIntent(this);
			SetServiceReceiver ssr = new SetServiceReceiver(new Handler());
			ssr.setListener((Listener) this);
			postSetRatingIntent.putExtra("receiver", ssr);
			postSetRatingIntent.putExtra("command", "postSetToSave");
			postSetRatingIntent.putExtra("setToSave", setToSave);
			startService(postSetRatingIntent);
		}

	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if (resultCode == SetService.SET_SAVED) {
			Toast.makeText(this, "Set Created", Toast.LENGTH_SHORT).show();
			Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
			startActivity(mainMenuIntent);
			finish();
		} else {
			mAlertDialog = new AlertDialog.Builder(this).setMessage("Error while saving set. Try again.")
					.setCancelable(false)
					.setPositiveButton("OK", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}).create();
			mAlertDialog.show();
		}
	}
}
