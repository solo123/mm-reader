package com.caimeng.software.binaryprotocol;

public class AppException extends Exception {
	
	private int errorCode;
	
	public AppException(int errorCode, String message)	{
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}	
}
