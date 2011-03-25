package com.caimeng.software.binaryprotocol;

public abstract class ProtocolBase {
	
	/**
	 * 通锟斤拷协锟斤拷姹撅拷锟�
	 */
	public short CommandID;
	/**
	 * 锟斤拷锟斤拷谋锟绞讹拷锟�
	 */
	public byte Version;

	/**
	 * 协锟斤拷幕锟斤拷锟�,锟斤拷锟斤拷协锟斤拷墓锟斤拷锟斤拷锟斤拷统锟皆�
	 */
	public ProtocolBase() {

	}

	/**
	 * ProtocolBase锟侥癸拷锟届函锟斤拷锟绞硷拷锟叫拷锟芥本锟脚硷拷锟斤拷锟斤拷ID
	 * @param version 通锟斤拷协锟斤拷姹撅拷锟�
	 * @param commandID 锟斤拷锟斤拷谋锟绞讹拷锟�
	 */
	public ProtocolBase(byte version, short commandID) {
		this.Version = version;
		this.CommandID = commandID;
	}

	/**
	 * 锟斤拷锟斤拷协锟斤拷锟斤拷锟叫伙拷锟斤拷锟街斤拷锟斤拷锟斤拷
	 * @return 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷谢锟叫拷锟斤拷锟街斤拷锟斤拷锟斤拷
	 */
	public byte[] getBytes() {
		ProtocolStream stream = new ProtocolStream();
		this.write(stream);
		byte[] output = stream.toArray();

		return output;
	}	

	/**
	 * 锟斤拷协锟斤拷协锟斤拷陌姹撅拷偶锟斤拷锟斤拷锟絀D写锟诫到协锟斤拷锟斤拷锟斤拷
	 * @param stream 协锟斤拷锟斤拷实锟斤拷
	 */
	protected void write(ProtocolStream stream) {
		stream.writeByte(this.Version);
		stream.write(this.CommandID);
	}
	
	/**
	 * 锟斤拷协锟斤拷锟斤拷锟叫讹拷取协锟斤拷陌姹撅拷偶锟斤拷锟斤拷锟絀D
	 * @param stream 协锟斤拷锟斤拷实锟斤拷
	 */
	protected void read(ProtocolStream stream) {
		this.Version = stream.getByte();
		this.CommandID = stream.getInt16();
	}

}
