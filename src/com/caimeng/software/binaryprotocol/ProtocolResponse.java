package com.caimeng.software.binaryprotocol;



import java.util.Hashtable;

public abstract class ProtocolResponse extends ProtocolBase {
	static Hashtable cache;
	static {
		cache = new Hashtable();
	}

	public static void clearCache() {
		if (cache != null && cache.size() > 0) {
			cache.clear();
		}
	}

	public static ProtocolResponse parse(byte[] input) throws AppException {
		if(input == null)
			throw new AppException(-11001,"无效的参数输入，input不能为null");
		if(input.length < 3 )
			throw new AppException(-15001,"无效的协议字节数组，input的长度不能小亄1�7");

		ProtocolStream stream = new ProtocolStream(input);
		short encoding = stream.getInt16();
		byte version = stream.getByte();
		short commandID = stream.getInt16();
		stream.StringEncoding = encoding;

		stream.setPosition(0);
		ProtocolResponse response = null;
		String key = version + "_" + commandID;
		if (cache.containsKey(key)) {
			response = ((ProtocolResponse)cache.get(key)).clone();
		}
		else {
			throw new AppException(-15002,"找不到相应的响应协议，请先注册响应协讄1�7");
		}

		response.read(stream);

		return response;
	}

	/**
	 * @param response
	 *            锟斤拷协锟斤拷注锟结到锟斤拷锟芥，锟皆凤拷锟斤拷锟斤拷锟ￄ1�7
	 * @throws AppException 
	 */

	public static void registerProtocol(ProtocolResponse response) throws AppException {
		if(response == null)
			throw new AppException(-11001,"无效的参数输入，response不能为null");

		String key = response.Version + "_" + response.CommandID;
		if(!cache.containsKey(key))
			cache.put(key, response);
	}

	public int ErrorCode;
	
	public String Message;

	ProtocolResponse() {}

	public ProtocolResponse(byte version, short commandID, short errorCode,String message) {
		super(version, commandID);
		this.ErrorCode = errorCode;
		this.Message = message;
	}

	
	public abstract ProtocolResponse clone();

	
	protected void read(ProtocolStream stream) {
		super.read(stream);

		this.ErrorCode = stream.getInt32();
		this.Message = stream.getString();
	}

	
	protected void write(ProtocolStream stream) {
		super.write(stream);

		stream.write(this.ErrorCode);
		stream.write(this.Message);
	}
}
