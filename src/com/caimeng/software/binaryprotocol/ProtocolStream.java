package com.caimeng.software.binaryprotocol;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.caimeng.software.io.BitConverter;
import com.caimeng.software.io.MemoryStream;
import com.caimeng.software.io.UUID;

public class ProtocolStream extends MemoryStream {

	public short StringEncoding;

	public ProtocolStream() {
		super();
	}

	public ProtocolStream(byte[] buffer) {
		super(buffer);
		// stream = new Stream(buffer);
	}

	public ProtocolStream(int capacity) {
		super();
	}

	public boolean getBoolean() {

		byte[] bufer = new byte[1];

		this.read(bufer, 0, bufer.length);

		return bufer[0] != 0;
	}

	public boolean[] getBooleanArray() {
		int count = this.getInt32();//
		boolean[] output = new boolean[count];
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				output[i] = this.getBoolean();
			}
			return output;
		} else {
			byte v = -1;

			v = this.getByte();

			if (v == 1) {
				return new boolean[0];
			} else {
				return null;
			}
		}
	}

	public byte getByte() {
		return (byte) this.readByte();
	}

	public byte[] getByteArray() {
		int count = this.getInt32();//
		byte[] output = new byte[count];
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				output[i] = this.getByte();
			}
			return output;
		} else {
			byte v = -1;

			v = this.getByte();

			if (v == 1) {
				return new byte[0];
			} else {
				return null;
			}
		}
	}

	public char getChar() {
		byte[] bufer = new byte[2];
		this.read(bufer, 0, bufer.length);

		return BitConverter.toChar(bufer);
	}

	public char[] getCharArray() {
		int count = this.getInt32();//
		char[] output = new char[count];
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				output[i] = this.getChar();
			}
			return output;
		} else {
			byte v = -1;

			v = this.getByte();

			if (v == 1) {
				return new char[0];
			} else {
				return null;
			}
		}
	}

	public Date getDateTime() {
		long v = this.getInt64();
		//System.out.println(v);
		return new Date(v);
	}

	public Date[] getDateTimeArray() {
		int count = this.getInt32();

		Date[] output = new Date[count];

		if (count > 0) {
			for (int i = 0; i < count; i++) {
				output[i] = this.getDateTime();

			}
			return output;
		} else {
			byte v = -1;

			v = this.getByte();

			if (v == 1) {
				return new Date[] {};
			} else {
				return null;
			}
		}
	}

	public double getDouble() {

		byte[] bufer = new byte[8];
		this.read(bufer, 0, bufer.length);
		return BitConverter.toDouble(bufer);
	}

	public double[] getDoubleArray() {
		int count = this.getInt32();//
		double[] output = new double[count];
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				output[i] = this.getDouble();
			}
			return output;
		} else {
			byte v = -1;

			v = this.getByte();

			if (v == 1) {
				return new double[0];
			} else {
				return null;
			}
		}
	}

	public float getFloat() {

		byte[] bufer = new byte[4];

		this.read(bufer, 0, bufer.length);

		return BitConverter.toFloat(bufer);
	}

	public float[] getFloatArray() {
		int count = this.getInt32();//
		float[] output = new float[count];
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				output[i] = this.getFloat();
			}
			return output;
		} else {
			byte v = -1;

			v = this.getByte();

			if (v == 1) {
				return new float[0];
			} else {
				return null;
			}
		}
	}

	/**
	 * ��ȡ�����sessionid
	 */

	public UUID getGuid() {
		//
		byte[] bufer = new byte[16];
		this.read(bufer, 0, bufer.length);

		byte[] bytes1 = new byte[8];
		byte[] bytes2 = new byte[8];
		bytes1[4] = bufer[0];
		bytes1[5] = bufer[1];
		bytes1[6] = bufer[2];
		bytes1[7] = bufer[3];
		bytes1[2] = bufer[4];
		bytes1[3] = bufer[5];
		bytes1[0] = bufer[6];
		bytes1[1] = bufer[7];

		bytes2[7] = bufer[8];
		bytes2[6] = bufer[9];
		bytes2[5] = bufer[10];
		bytes2[4] = bufer[11];
		bytes2[3] = bufer[12];
		bytes2[2] = bufer[13];
		bytes2[1] = bufer[14];
		bytes2[0] = bufer[15];

		long lest = BitConverter.toInt64(bytes1);
		long most = BitConverter.toInt64(bytes2);

		UUID uuid = new UUID(lest, most);
		return uuid;
	}

	public UUID[] getGuidArray() {
		int count = this.getInt32();

		UUID[] output = new UUID[count];

		if (count > 0) {
			for (int i = 0; i < count; i++) {
				output[i] = this.getGuid();

			}
			return output;
		} else {
			byte v = -1;

			v = this.getByte();

			if (v == 1) {
				return new UUID[] {};
			} else {
				return null;
			}
		}
	}

	public short getInt16() {

		byte[] bufer = new byte[2];
		this.read(bufer, 0, bufer.length);
		return BitConverter.toInt16(bufer);
	}

	public short[] getInt16Array() {
		int count = this.getInt32();//
		short[] output = new short[count];
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				output[i] = this.getInt16();
			}
			return output;
		} else {
			byte v = -1;

			v = this.getByte();

			if (v == 1) {
				return new short[0];
			} else {
				return null;
			}
		}
	}

	public int getInt32() {

		byte[] bufer = new byte[4];

		this.read(bufer, 0, bufer.length);
		return BitConverter.toInt32(bufer);
	}

	public int[] getInt32Array() {
		int count = this.getInt32();//
		int[] output = new int[count];
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				output[i] = this.getInt32();
			}
			return output;
		} else {
			byte v = -1;

			v = this.getByte();

			if (v == 1) {
				return new int[0];
			} else {
				return null;
			}
		}
	}

	public long getInt64() {

		byte[] bufer = new byte[8];

		this.read(bufer, 0, bufer.length);

		return BitConverter.toInt64(bufer);
	}

	public long[] getInt64Array() {
		int count = this.getInt32();//
		long[] output = new long[count];
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				output[i] = this.getInt64();
			}
			return output;
		} else {
			byte v = -1;

			v = this.getByte();

			if (v == 1) {
				return new long[0];
			} else {
				return null;
			}
		}
	}
/*
	public KeyValuePair getKeyValue() {
		String key = this.getString();
		String value = this.getString();

		return new KeyValuePair(key, value);
	}

	public KeyValuePair[] getKeyValueArray() {
		int count = this.getInt32();

		KeyValuePair[] output = new KeyValuePair[count];

		if (count > 0) {
			for (int i = 0; i < count; i++) {
				output[i] = this.getKeyValue();

			}
			return output;
		} else {
			byte v = -1;

			v = this.getByte();

			if (v == 1) {
				return new KeyValuePair[] {};
			} else {
				return null;
			}
		}
	}
*/
	public String getString() {
		int count = this.getInt32();//
		if (count > 0) {
			byte[] output = new byte[count];
			this.read(output, 0, count);
			try {
				String s=new String(output,ProtocolEnvironment.ENCODINGS[ProtocolEnvironment.DEFAULT_ENCODING]);
				output=null;
				String s1=s;
				String s2=s1;
				s=s2;
				return s;
			} catch (UnsupportedEncodingException e) {
				// throw
				// AppError.UnsupportedEncoding(ProtocolEnvironment.ENCODINGS[this.StringEncoding]);
			}
		} else {
			byte v = -1;
			v = this.getByte();
			if (v == 1) {
				return "";
			}
		}

		return null;
	}

	public String[] getStringArray() {
		int count = this.getInt32();

		String[] output = new String[count];

		if (count > 0) {
			for (int i = 0; i < count; i++) {
				output[i] = this.getString();

			}
			return output;
		} else {
			byte v = -1;

			v = this.getByte();

			if (v == 1) {
				return new String[] {};
			} else {
				return null;
			}
		}
	}

	public void write(boolean value) {
		byte[] output = BitConverter.getBytes(value);
		this.write(output, 0, output.length);
	}

	public void write(boolean[] value) {
		//
		if (value != null) {
			this.write(value.length);
			if (value.length > 0) {
				for (int i = 0; i < value.length; i++) {
					this.write(value[i]);
				}
			} else {
				this.writeByte((byte) 1);
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}

	public void write(byte value) {
		super.writeByte(value);
	}

	public void write(byte[] value) {
		if (value != null) {
//			System.out.println("free mem 3   "+Runtime.getRuntime().freeMemory());
			this.write(value.length);
			if (value.length > 0) {
				for (int i = 0; i < value.length; i++) {
					super.writeByte(value[i]);
				}
				value=null;
//				System.gc();
//				System.out.println("free mem 4   "+Runtime.getRuntime().freeMemory());
			} else {
				this.writeByte((byte) 1);
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}

	public void write(byte[] buffer, int offset, int count) {
		super.write(buffer, offset, count);
	}

	public void write(char value) {
		byte[] output = BitConverter.getBytes(value);
		this.write(output, 0, output.length);
	}

	public void write(char[] value) {
		//
		if (value != null) {
			this.write(value.length);
			if (value.length > 0) {
				for (int i = 0; i < value.length; i++) {
					this.write(value[i]);
				}
			} else {
				this.writeByte((byte) 1);
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}

	public void write(Date value) {
		if(value != null){
		long v = value.getTime();
		this.write(v);
		}
		else
		{
			this.write((long)0);
		}
	}

	public void write(Date[] value) {

		if (value != null) {
			this.write(value.length);
			if (value.length > 0) {
				for (int i = 0; i < value.length; i++) {
					this.write(value[i]);
				}
			} else {
				this.writeByte((byte) 1);
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}

	public void write(double value) {
		byte[] output = BitConverter.getBytes(value);
		this.write(output, 0, output.length);
	}

	public void write(double[] value) {
		//
		if (value != null) {
			this.write(value.length);
			if (value.length > 0) {
				for (int i = 0; i < value.length; i++) {
					this.write(value[i]);
				}
			} else {
				this.writeByte((byte) 1);
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}

	public void write(float value) {
		byte[] output = BitConverter.getBytes(value);
		this.write(output, 0, output.length);
	}

	public void write(float[] value) {
		//
		if (value != null) {
			this.write(value.length);
			if (value.length > 0) {
				for (int i = 0; i < value.length; i++) {
					this.write(value[i]);
				}
			} else {
				this.writeByte((byte) 1);
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}

	public void write(int value) {
		byte[] output = BitConverter.getBytes(value);
		this.write(output, 0, output.length);
	}

	public void write(int[] value) {
		//
		if (value != null) {
			this.write(value.length);
			if (value.length > 0) {
				for (int i = 0; i < value.length; i++) {
					this.write(value[i]);
				}
			} else {
				this.writeByte((byte) 1);
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}
/*
	public void write(KeyValuePair value) {
		this.write(value.Key);
		this.write(value.Value);
	}

	public void write(KeyValuePair[] value) {
		if (value != null) {
			this.write(value.length);
			if (value.length > 0) {
				for (int i = 0; i < value.length; i++) {
					this.write(value[i]);
				}
			} else {
				this.writeByte((byte) 1);
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}*/

	public void write(long value) {
		byte[] output = BitConverter.getBytes(value);
		this.write(output, 0, output.length);
	}

	public void write(long[] value) {
		//
		if (value != null) {
			this.write(value.length);
			if (value.length > 0) {
				for (int i = 0; i < value.length; i++) {
					this.write(value[i]);
				}
			} else {
				this.writeByte((byte) 1);
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}

	public void write(short value) {
		byte[] output = BitConverter.getBytes(value);
		this.write(output, 0, output.length);
	}

	public void write(short[] value) {
		//
		if (value != null) {
			this.write(value.length);
			if (value.length > 0) {
				for (int i = 0; i < value.length; i++) {
					this.write(value[i]);
				}
			} else {
				this.writeByte((byte) 1);
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}

	// public void Write(UUID value) {
	// String input = value.toString();
	//
	// this.Write(input);
	// }

	public void write(String value) {
		if (value != null) {
			try {
//				System.out.println("Write string...");				
				byte[] output = value
						.getBytes(ProtocolEnvironment.ENCODINGS[ProtocolEnvironment.DEFAULT_ENCODING]);
				//System.out.println("Write string1...");
				
				this.write(output);
				//System.out.println("Write string2...");
				
			} catch (UnsupportedEncodingException e) {
				// throw
				// AppError.UnsupportedEncoding(ProtocolEnvironment.ENCODINGS[this.StringEncoding]);
				System.out.println("Encoding error1");
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}

	public void write(String[] value) {
		if (value != null) {
			this.write(value.length);
			if (value.length > 0) {
				for (int i = 0; i < value.length; i++) {
					this.write(value[i]);
				}
			} else {
				this.writeByte((byte) 1);
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}

	public void write(UUID value) {

		byte[] bytes = new byte[16];
		if (value != null) {
			//
			long least = value.getLeastSignificantBits();
			long most = value.getMostSignificantBits();
			byte[] bsLeast = BitConverter.getBytes(least);
			byte[] bsMost = BitConverter.getBytes(most);
			bytes[0] = bsMost[4];
			bytes[1] = bsMost[5];
			bytes[2] = bsMost[6];
			bytes[3] = bsMost[7];
			bytes[4] = bsMost[2];
			bytes[5] = bsMost[3];
			bytes[6] = bsMost[0];
			bytes[7] = bsMost[1];

			bytes[8] = bsLeast[7];
			bytes[9] = bsLeast[6];
			bytes[10] = bsLeast[5];
			bytes[11] = bsLeast[4];
			bytes[12] = bsLeast[3];
			bytes[13] = bsLeast[2];
			bytes[14] = bsLeast[1];
			bytes[15] = bsLeast[0];
		}

		this.write(bytes, 0, bytes.length);
	}

	public void write(UUID[] value) {
		if (value != null) {
			this.write(value.length);
			if (value.length > 0) {
				for (int i = 0; i < value.length; i++) {
					this.write(value[i]);
				}
			} else {
				this.writeByte((byte) 1);
			}
		} else {
			this.write(0);
			this.writeByte((byte) 0);// null
		}
	}
	
	/**
	 * 
	 * */
	public  Object[] getObjectArray()
	{
		return null;
	}
	
	public  void write(Object[] obj) {
		
	}
}
