package com.capstone.mutiboserver.client;

import java.util.concurrent.Executor;

import retrofit.Endpoint;
import retrofit.ErrorHandler;
import retrofit.Profiler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Log;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.Client;
import retrofit.client.Client.Provider;
import retrofit.client.OkClient;
import retrofit.converter.Converter;

/**
 * A Builder class for a Retrofit REST Adapter. Extends the default implementation by providing logic to
 * handle an OAuth 2.0 password grant login flow. The RestAdapter that it produces uses an interceptor
 * to automatically obtain a bearer token from the authorization server and insert it into all client
 * requests.
 * */
public class MutiboRestBuilder extends RestAdapter.Builder {

	private String username;
	private String password;
	private String loginUrl;
	private String clientId;
	private String clientSecret = "";
	private Client client;
	
	public MutiboRestBuilder setLoginEndpoint(String endpoint){
		loginUrl = endpoint;
		return this;
	}

	@Override
	public MutiboRestBuilder setEndpoint(String endpoint) {
		return (MutiboRestBuilder) super.setEndpoint(endpoint);
	}

	@Override
	public MutiboRestBuilder setEndpoint(Endpoint endpoint) {
		return (MutiboRestBuilder) super.setEndpoint(endpoint);
	}

	@Override
	public MutiboRestBuilder setClient(Client client) {
		this.client = client;
		return (MutiboRestBuilder) super.setClient(client);
	}

	@Override
	public MutiboRestBuilder setClient(Provider clientProvider) {
		client = clientProvider.get();
		return (MutiboRestBuilder) super.setClient(clientProvider);
	}

	@Override
	public MutiboRestBuilder setErrorHandler(ErrorHandler errorHandler) {

		return (MutiboRestBuilder) super.setErrorHandler(errorHandler);
	}

	@Override
	public MutiboRestBuilder setExecutors(Executor httpExecutor,
			Executor callbackExecutor) {

		return (MutiboRestBuilder) super.setExecutors(httpExecutor,
				callbackExecutor);
	}

	@Override
	public MutiboRestBuilder setRequestInterceptor(
			RequestInterceptor requestInterceptor) {

		return (MutiboRestBuilder) super
				.setRequestInterceptor(requestInterceptor);
	}

	@Override
	public MutiboRestBuilder setConverter(Converter converter) {

		return (MutiboRestBuilder) super.setConverter(converter);
	}

	@Override
	public MutiboRestBuilder setProfiler(@SuppressWarnings("rawtypes") Profiler profiler) {

		return (MutiboRestBuilder) super.setProfiler(profiler);
	}

	@Override
	public MutiboRestBuilder setLog(Log log) {

		return (MutiboRestBuilder) super.setLog(log);
	}

	@Override
	public MutiboRestBuilder setLogLevel(LogLevel logLevel) {

		return (MutiboRestBuilder) super.setLogLevel(logLevel);
	}

	public MutiboRestBuilder setUsername(String username) {
		this.username = username;
		return this;
	}

	public MutiboRestBuilder setPassword(String password) {
		this.password = password;
		return this;
	}

	public MutiboRestBuilder setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}
	
	public MutiboRestBuilder setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}
	
	@Override
	public RestAdapter build() {
		if (username == null || password == null) {
			throw new MutiboRestException(
					"You must specify both a username and password for a "
							+ "SecuredRestBuilder before calling the build() method.");
		}

		if (client == null) {
			client = new OkClient();
		}
		OAuthHandler hdlr = new OAuthHandler(client, loginUrl, username, password, clientId, clientSecret);
		setRequestInterceptor(hdlr);

		return super.build();
	}
}