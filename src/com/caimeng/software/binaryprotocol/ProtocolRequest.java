package com.caimeng.software.binaryprotocol;



import java.util.Hashtable;

public abstract class ProtocolRequest extends ProtocolBase{

	static Hashtable cache;

	static {
		cache = new Hashtable();
	}
	
	

	public static void clearCache() {
		if (cache != null && cache.size() > 0) {
			cache.clear();
		}
	}

	public static ProtocolRequest parse(byte[] input) throws AppException {
		if(input == null)
			throw new AppException(-11001,"鏃犳晥鐨勫弬鏁拌緭鍏ワ紝input涓嶈兘涓簄ull");
		if(input.length < 3 )
			throw new AppException(-15001,"鏃犳晥鐨勫崗璁瓧鑺傛暟缁勶紝input鐨勯暱搴︿笉鑳藉皬浜�3");

		ProtocolStream stream = new ProtocolStream(input);
		short encoding = stream.getInt16();
		byte version = stream.getByte();
		short commandID = stream.getInt16();
		stream.StringEncoding = encoding;
		stream.setPosition(0);
		ProtocolRequest request = null;

		String key = version + "_" + commandID;

		if (cache.containsKey(key)) {
			
			request = ((ProtocolRequest)cache.get(key)).clone();	
			
		}
		else {
			throw new AppException(-15002,"鎵句笉鍒扮浉搴旂殑璇锋眰鍗忚锛岃鍏堟敞鍐岃姹傚崗璁�");
		}

		request.read(stream);

		return request;
	}

	/**
	 * 闁跨喐鏋婚幏宄板礂闁跨喐鏋婚幏閿嬫暈闁跨喓绮ㄩ崚浼存晸閺傘倖瀚归柨鐔诲Г閿涘矂鏁撻惃鍡楀殩閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏鐑芥晸閿燂拷	 
	 * @throws AppException */
	public static void registerProtocol(ProtocolRequest request) throws AppException {
		if(request == null)
			throw new AppException(-11001,"鏃犳晥鐨勫弬鏁拌緭鍏ワ紝request涓嶈兘涓簄ull");

		String key = request.Version + "_" + request.CommandID;
		if(!cache.containsKey(key))
			cache.put(key, request);
	}

	public ProtocolRequest() {}

	public ProtocolRequest(byte version, short commandID) {
		super(version, commandID);
	}

	
	public abstract ProtocolRequest clone();
}
