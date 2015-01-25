package com.capstone.mutibo.rest.api;

import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Context;
import android.content.Intent;

import com.capstone.mutibo.activity.LoginActivity;
import com.capstone.mutibo.auth.MutiboRestBuilder;

public class MutiboApiManager {

	public static final String CLIENT_ID = "mobile";

	private static final String server = "https://10.0.3.2:8443";// "https://10.0.2.2:8443";

	private static MutiboApi mutiboApi_;

	public static synchronized MutiboApi getOrShowLogin(Context ctx) {
		if (mutiboApi_ != null) {
			return mutiboApi_;
		} else {
			Intent i = new Intent(ctx, LoginActivity.class);
			ctx.startActivity(i);
			return null;
		}
	}

	public static synchronized MutiboApi init(String user, String pass) {

		mutiboApi_ = new MutiboRestBuilder()
				.setLoginEndpoint(server + MutiboApi.TOKEN_PATH)
				.setUsername(user).setPassword(pass).setClientId(CLIENT_ID)
				.setClient(new ApacheClient(new EasyHttpClient()))
				.setEndpoint(server).setLogLevel(LogLevel.FULL).build()
				.create(MutiboApi.class);
		
		return mutiboApi_;
	}

}
