package com.caimeng.software.network;

import java.io.InputStream;
import java.io.OutputStream;

import com.caimeng.software.binaryprotocol.AppException;


public class QueryServerData {
	public InputStream InStream;
	public OutputStream OutStream;
	public int ReceiveBufferSize;
	public int Timeout;
	public Object Input;
	public Object Output;
	public boolean IsCompleted;
	public AppException Exception;
}
