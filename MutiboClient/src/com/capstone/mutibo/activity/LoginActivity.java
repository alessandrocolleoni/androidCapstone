package com.capstone.mutibo.activity;

import org.apache.http.conn.HttpHostConnectException;

import retrofit.RetrofitError;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.capstone.mutibo.auth.MutiboRestException;
import com.capstone.mutibo.auth.MutiboUser;
import com.capstone.mutibo.rest.api.MutiboApi;
import com.capstone.mutibo.rest.api.MutiboApiManager;

public class LoginActivity extends AmazonGameCircleAbstract {

	final static String LOGIN_TAG = LoginActivity.class.getName();

	@InjectView(R.id.login_button)
	Button mLoginButton;

	@InjectView(R.id.login_user)
	TextView mUser;

	@InjectView(R.id.login_password)
	TextView mPassword;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		ButterKnife.inject(this);
	}

	@OnClick(R.id.guest_button)
	public void playDemo() {
		Intent gameScreenIntent = new Intent(this, GameScreenActivity.class);
		gameScreenIntent.putExtra("isGuest", true);
		startActivity(gameScreenIntent);

	}

	@OnClick(R.id.login_button)
	public void login() {
		LoginTask t = new LoginTask(LoginActivity.this);
		t.execute();
	}

	private class LoginTask extends AsyncTask<String, Void, Boolean> {

		ProgressDialog mDialog;
		AlertDialog mAlertDialog;
		Context mContext;
		String mError;

		// constructor
		LoginTask(Context c) {
			Log.d(LOGIN_TAG, "LoginTask() constructed");
			mLoginButton.setEnabled(false);
			mContext = c;
			// display a dialog with a spinner showing progress being worked on
			mDialog = ProgressDialog.show(c, "", "Loading", true, false);
			mDialog.setCancelable(false);
		}

		@Override
		public Boolean doInBackground(String... params) {
			Log.d(LOGIN_TAG, "LoginTask.doInBackground() called");

			String user = mUser.getText().toString();
			String password = mPassword.getText().toString();

			if (user.isEmpty() || password.isEmpty()) {
				mError = "Insert Username and Password";
				return false;
			}

			final MutiboApi mutiboRestApi = MutiboApiManager.init(mUser.getText().toString(), mPassword.getText().toString());
			MutiboUser mu = null;
			try {
				// called for first Login
				mu = mutiboRestApi.getUser(user);
			} catch (RetrofitError re) {
				Throwable t = re.getCause();
				if (t.getCause() instanceof MutiboRestException) {
					MutiboRestException mre = (MutiboRestException) t
							.getCause();
					mError = mre.getErrorDescription();
				}

				if (t.getCause() instanceof HttpHostConnectException) {
					mError = "Cannot connect to the Server.";
				}
				Log.d(LOGIN_TAG, re.getMessage());
				return false;
			} catch (Exception e) {
				Log.d(LOGIN_TAG, e.getMessage());
				return false;
			}
			
			if (mu == null) {
				mError = "User not found.";
				return false;
			}
			
			return true;
		}

		// post processing of login verification
		@Override
		public void onPostExecute(Boolean result) {
			Log.d(LOGIN_TAG, "LoginTask.onPostExecute() called");
			mLoginButton.setEnabled(true);
			mDialog.dismiss();
			if (result) {
				Intent mainMenuIntent = new Intent(LoginActivity.this,
						MainMenuActivity.class);
				startActivity(mainMenuIntent);
				finish();
			} else {
				mAlertDialog = new AlertDialog.Builder(mContext)
						.setMessage(mError).setCancelable(false)
						.setPositiveButton("OK", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).create();
				mAlertDialog.show();
				Log.d(LOGIN_TAG, "Wrong login");
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_circle_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.gameCircle:
			if (isOnline()) {
				if (agsClient.getPlayerClient().isSignedIn()) {
					agsClient.showGameCircle();
				} else {
					agsClient.showSignInPage();
				}
			}
		default:
			return false;
		}
	}
}
