package com.caimeng.software.binaryprotocol;

public abstract class ProtocolBase {
	
	/**
	 * ͨ��Э��汾�ￄ1�7
	 */
	public short CommandID;
	/**
	 * ����ı�ʶ�ￄ1�7
	 */
	public byte Version;

	/**
	 * Э��Ļ��ￄ1�7,����Э��Ĺ������ͳ�Ԅ1�7
	 */
	public ProtocolBase() {

	}

	/**
	 * ProtocolBase�Ĺ��캯���ʼ��Э��汾�ż�����ID
	 * @param version ͨ��Э��汾�ￄ1�7
	 * @param commandID ����ı�ʶ�ￄ1�7
	 */
	public ProtocolBase(byte version, short commandID) {
		this.Version = version;
		this.CommandID = commandID;
	}

	/**
	 * ����Э�����л����ֽ�����
	 * @return ����������л�Э����ֽ�����
	 */
	public byte[] getBytes() {
		ProtocolStream stream = new ProtocolStream();
		this.write(stream);
		byte[] output = stream.toArray();

		return output;
	}	

	/**
	 * ��Э��Э��İ汾�ż�����IDд�뵽Э������
	 * @param stream Э����ʵ��
	 */
	protected void write(ProtocolStream stream) {
		stream.writeByte(this.Version);
		stream.write(this.CommandID);
	}
	
	/**
	 * ��Э�����ж�ȡЭ��İ汾�ż�����ID
	 * @param stream Э����ʵ��
	 */
	protected void read(ProtocolStream stream) {
		this.Version = stream.getByte();
		this.CommandID = stream.getInt16();
	}

}
