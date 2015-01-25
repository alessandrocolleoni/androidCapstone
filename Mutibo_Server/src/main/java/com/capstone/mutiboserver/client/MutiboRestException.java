package com.capstone.mutiboserver.client;

public class MutiboRestException extends RuntimeException {
	
	private static final long serialVersionUID = 4734250060191343059L;
	
	private int status;
	
	private String reason;
	
	private String errorDescription;

	public MutiboRestException() {
		super();
	}

	public MutiboRestException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MutiboRestException(String message, int status, String reason) {
		super(message);
		this.status = status;
		this.reason = reason;
	}
	
	public MutiboRestException(String message, int status, String reason, String errorDescription) {
		super(message);
		this.status = status;
		this.reason = reason;
		this.errorDescription = errorDescription;
	}
	
	public MutiboRestException(String message) {
		super(message);
	}

	public MutiboRestException(Throwable cause) {
		super(cause);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
}
