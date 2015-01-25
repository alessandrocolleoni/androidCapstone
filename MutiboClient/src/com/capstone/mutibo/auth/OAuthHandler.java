package com.capstone.mutibo.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import retrofit.RequestInterceptor;
import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.FormUrlEncodedTypedOutput;

import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class OAuthHandler implements RequestInterceptor {

	private boolean loggedIn;
	private Client client;
	private String tokenIssuingEndpoint;
	private String username;
	private String password;
	private String clientId;
	private String clientSecret;
	private String accessToken;

	public OAuthHandler(Client client, String tokenIssuingEndpoint,
			String username, String password, String clientId,
			String clientSecret) {
		super();
		this.client = client;
		this.tokenIssuingEndpoint = tokenIssuingEndpoint;
		this.username = username;
		this.password = password;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	/**
	 * Every time a method on the client interface is invoked, this method is
	 * going to get called. The method checks if the client has previously
	 * obtained an OAuth 2.0 bearer token. If not, the method obtains the bearer
	 * token by sending a password grant request to the server.
	 * 
	 * Once this method has obtained a bearer token, all future invocations will
	 * automatically insert the bearer token as the "Authorization" header in
	 * outgoing HTTP requests.
	 * 
	 */
	@Override
	public void intercept(RequestFacade request) {
		// If we're not logged in, login and store the authentication token.
		if (!loggedIn) {
			try {
				// This code below programmatically builds an OAuth 2.0 password
				// grant request and sends it to the server.

				// Encode the username and password into the body of the
				// request.
				FormUrlEncodedTypedOutput to = new FormUrlEncodedTypedOutput();
				to.addField("username", username);
				to.addField("password", password);

				// Add the client ID and client secret to the body of the
				// request.
				to.addField("client_id", clientId);
				to.addField("client_secret", clientSecret);

				// Indicate that we're using the OAuth Password Grant Flow
				// by adding grant_type=password to the body
				to.addField("grant_type", "password");

				String base64Auth = BaseEncoding.base64().encode(
						new String(clientId + ":" + clientSecret).getBytes());
				// Add the basic authorization header
				List<Header> headers = new ArrayList<Header>();
				headers.add(new Header("Authorization", "Basic " + base64Auth));

				// Create the actual password grant request using the data above
				Request req = new Request("POST", tokenIssuingEndpoint,
						headers, to);

				// Request the password grant.
				Response resp = client.execute(req);

				// Make sure the server responded with 200 OK
				if (resp.getStatus() < 200 || resp.getStatus() > 299) {
					// If not, we probably have bad credentials
					switch (resp.getStatus()) {
					case 400:
						throw new MutiboRestException("Login failure: "
								+ resp.getStatus() + " - " + resp.getReason(),
								resp.getStatus(), resp.getReason(),
								"Wrong Password");

					case 401: // not authorized
						throw new MutiboRestException("Login failure: "
								+ resp.getStatus() + " - " + resp.getReason(),
								resp.getStatus(), resp.getReason(),
								"User Unauthorized. Login with valid credentials.");
					default:
						throw new MutiboRestException("Failure on /oaut/token "
								+ resp.getStatus() + " - " + resp.getReason());
					}

				} else {
					// Extract the string body from the response
					String body = IOUtils.toString(resp.getBody().in());

					// Extract the access_token (bearer token) from the response
					// so that we
					// can add it to future requests.
					accessToken = new Gson().fromJson(body, JsonObject.class)
							.get("access_token").getAsString();

					// Add the access_token to this request as the
					// "Authorization"
					// header.
					request.addHeader("Authorization", "Bearer " + accessToken);

					// Let future calls know we've already fetched the access
					// token
					loggedIn = true;
				}
			} catch (Exception e) {
				throw new MutiboRestException(e);
			}
		} else {
			// Add the access_token that we previously obtained to this request
			// as
			// the "Authorization" header.
			request.addHeader("Authorization", "Bearer " + accessToken);
		}
	}

}
