
package com.caimeng.uilibray.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


/**
 * 用来装载系统中需要使用的各类图像信息
 * @author joycode workshop
 *
 */
public class ImageBuilder {
	//public static Image INDICATOR_IMG = loadImage("/icons/arrow_right_gray.png");
	public void zoomImage(Image img_in,int w_out,int h_out ){
		int w_in=img_in.getWidth();
		int h_in=img_in.getHeight();
		int rgb_in[] =new int[w_in*h_in];
		img_in.getRGB(rgb_in, 0, w_in, 0, 0, w_in, h_in);
		int rgb_out[] =new int[w_out*h_out];
		
		for(int y_out=0;y_out<h_out;y_out++){
			int y_in=y_out*h_in/h_out;
			for(int x_out=0;x_out<w_out;x_out++){
				int x_in=x_out*w_in/w_out;
				rgb_out[(w_out*y_out)+x_out]=rgb_in[(w_in*y_in)+x_in];
			}
		}
	
//		return rgb_out;
	}
	
	
	public static byte[] getByteArray(Image image)
	{
	int raw[] = new int[image.getWidth() * image.getHeight()];
	image.getRGB(raw, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
	byte rawByte[] = new byte[image.getWidth() * image.getHeight() * 4];
	int n = 0;
	for(int i = 0; i < raw.length; i++)
	{
//	 break up into channels
	int ARGB = raw[i];
	int a = (ARGB & 0xff000000) >> 24; // alpha channel!
	int r = (ARGB & 0xff0000) >> 16; // red channel!
	int g = (ARGB & 0x0000ff00) >> 8; // green channel!
	int b = ARGB & 0x000000ff; // blue channel !

	rawByte[n] = (byte)b;
	rawByte[n + 1] = (byte)g;
	rawByte[n + 2] = (byte)r;
	rawByte[n + 3] = (byte)a;

//	 you can see these codes
//	 from "http://forum.java.sun.com/thread.jspa?forumID=76&threadID=629677"
//	 put the color back together
//	argb = ((a << 24) | (r << 16) | (g << 8) | b);

	n += 4;
	}

	raw = null;
	return rawByte;
	}
	/**
	 * load指定位置的一个图像文件
	 * @param name
	 * @return
	 */
	public static Image loadImage(String name) {
		Image image = null;
		try {
			image = Image.createImage(name);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
		return image;
	}
	
	public static void drawSmallImage(Graphics g, Image img, int x, int y, int w, int h, int x1, int y1)   //在大图中画小图
	{
		g.setClip(x, y, w, h);
		g.drawImage(img, x-x1, y-y1,0);
	}
	
	 public static byte[] HEADChunk = {
         (byte) 0x89, (byte) 0x50,
         (byte) 0x4E, (byte) 0x47,
         (byte) 0x0D, (byte) 0x0A,
         (byte) 0x1A, (byte) 0x0A,
         };

	 public static byte[] tRNSChunk = {
         (byte) 0x00, (byte) 0x00,
         (byte) 0x00, (byte) 0x01,
         (byte) 0x74, (byte) 0x52,
         (byte) 0x4E, (byte) 0x53,
         (byte) 0x00,
         (byte) 0x40, (byte) 0xE6,
         (byte) 0xD8, (byte) 0x66,
};

    public static byte[] IENDChunk = {
        //PNGIEND
        (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00,
        (byte) 0x49, (byte) 0x45,
        (byte) 0x4E, (byte) 0x44,
        (byte) 0xAE, (byte) 0x42,
        (byte) 0x60, (byte) 0x82
        };

	public static int IDATPOS;
	public static byte[] Image2Bytes(Image img) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            int offset = 0;
            byte buffer[] = new byte[(w * 4 + 1) * h + offset];
            getImageBufferForImageARGB8888(img, buffer, w, h, offset);
            img=null;
            System.gc();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(baos);
            WritePng(dout, w, h, buffer, null, false, offset);
            dout.flush();
            dout.close();
            byte[] data = baos.toByteArray();
            baos.flush();
            baos.close();
            writeCRC(data, 8); //更新IHDR CRC
            writeCRC(data, 33); //更新PLTE CRC
            writeCRC(data, IDATPOS); //更新IDAT CRC
            buffer = null;
            System.gc();
            return data;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void writeCRC(byte[] data, int chunkpos) {
        int chunklen = ((data[chunkpos] & 0xFF) << 24)
                       | ((data[chunkpos + 1] & 0xFF) << 16)
                       | ((data[chunkpos + 2] & 0xFF) << 8)
                       | (data[chunkpos + 3] & 0xFF);

        int sum = CRCChecksum(data, chunkpos + 4, 4 + chunklen) ^ 0xffffffff;
        int val = sum;
        int pos = chunkpos + 8 + chunklen;
        data[pos] = (byte) ((val & 0xFF000000) >> 24);
        data[pos + 1] = (byte) ((val & 0xFF0000) >> 16);
        data[pos + 2] = (byte) ((val & 0xFF00) >> 8);
        data[pos + 3] = (byte) (val & 0xFF);
    }

    public static int[] crc_table; //CRC 表
    public static int CRCChecksum(byte[] buf, int off, int len) {
        int c = 0xffffffff;
        int n;
        if (crc_table == null) {
            int mkc;
            int mkn, mkk;
            crc_table = new int[256];
            for (mkn = 0; mkn < 256; mkn++) {
                mkc = mkn;
                for (mkk = 0; mkk < 8; mkk++) {
                    if ((mkc & 1) == 1) {
                        mkc = 0xedb88320 ^ (mkc >>> 1);
                    } else {
                        mkc = mkc >>> 1;
                    }
                }
                crc_table[mkn] = mkc;
            }
        }
        for (n = off; n < len + off; n++) {
            c = crc_table[(c ^ buf[n]) & 0xff] ^ (c >>> 8);
        }
        return c;
    }

    public static long adler32(long adler, byte[] buf, int index, int len) {
        int BASE = 65521;
        int NMAX = 5552;
        //TODO remove this function at all
        if (buf == null) {
            return 1L;
        }

        long s1 = adler & 0xffff;
        long s2 = (adler >> 16) & 0xffff;
        int k;

        while (len > 0) {
            k = len < NMAX ? len : NMAX;
            len -= k;
            while (k >= 16) {
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                s1 += buf[index++] & 0xff;
                s2 += s1;
                k -= 16;
            }
            if (k != 0) {
                do {
                    s1 += buf[index++] & 0xff;
                    s2 += s1;
                } while (--k != 0);
            }
            s1 %= BASE;
            s2 %= BASE;
        }
        return (s2 << 16) | s1;
    }

    public static void WritePng(DataOutputStream output, int width, int height,
                                byte[] buffer, byte[] colors,
                                boolean Transparent, int offset) throws
            IOException {
        int adler = (int) adler32(1l, buffer, offset, buffer.length - offset);
        byte[] lenNlen = { //压缩块的LEN和NLEN信息
                         (byte) 0,
                         (byte) 0xfa, (byte) 0x7e,
                         (byte) 0x05, (byte) 0x81
        };
        IDATPOS = 0;
        output.write(HEADChunk);
        IDATPOS += HEADChunk.length;
        //写IHDR
        output.writeInt(13); //len
        output.writeInt(1229472850); //IHDR type code
        output.writeInt(width); //写宽度
        output.writeInt(height); //写高度
        output.writeByte(8); //1Bitdepth
        if (colors == null) {
            output.writeByte(6); //2ColorType
        } else {
            output.writeByte(3); //2ColorType
        }
        output.writeByte(0); //3CompressionMethod
        output.writeByte(0); //4Filter method
        output.writeByte(0); //5Interlace method
        output.writeInt(0); //写crc
        IDATPOS += 25;
        //写PLTE
        if (colors != null) {
            output.writeInt(colors.length); //len
            output.writeInt(1347179589); //type code
            output.write(colors); //data
            output.writeInt(0); //crc
            IDATPOS += colors.length + 12;
        }
        //写TRNS
        if (Transparent) {
            output.write(tRNSChunk);
            IDATPOS += tRNSChunk.length;
        }
        //写IDAT
        byte[] dpixels = buffer;
        int bufferlen = dpixels.length - offset;
        int blocklen = 32506;
        int blocknum = 1;
        if ((dpixels.length % blocklen) == 0) {
            blocknum = bufferlen / blocklen;
        } else {
            blocknum = (bufferlen / blocklen) + 1;
        }
        int IDATChunkLen = (bufferlen + 6 + blocknum * 5);
        output.writeInt(IDATChunkLen); //len
        output.writeInt(1229209940); //idat type code
        output.writeShort((short) 0x78da); //78da
        for (int i = 0; i < blocknum; i++) {
            int off = i * blocklen;
            int len = bufferlen - off;
            if (len >= blocklen) {
                len = blocklen;
                lenNlen[0] = (byte) 0;
            } else {
                lenNlen[0] = (byte) 1;
            }
            int msb = (len & 0xff);
            int lsb = (len >>> 8);
            lenNlen[1] = (byte) msb;
            lenNlen[2] = (byte) lsb;
            lenNlen[3] = (byte) (msb ^ 0xff);
            lenNlen[4] = (byte) (lsb ^ 0xff);
            output.write(lenNlen);
            output.write(dpixels, off + offset, len);
        }
        output.writeInt(adler); //IDAT adler
        output.writeInt(0); //IDAT crc
        output.write(IENDChunk);
    }

    public static void getImageBufferForImageARGB8888(Image img, byte[] rawByte,
            int w, int h, int off) {
        int n = off;
        int[] raw = new int[w];
        for (int j = 0; j < h; j++) {
            img.getRGB(raw, 0, w, 0, j, w, 1);
            for (int i = 0; i < raw.length; i++) {
                int ARGB = raw[i];
                int a = (ARGB & 0xff000000) >> 24;
                int r = (ARGB & 0xff0000) >> 16;
                int g = (ARGB & 0xff00) >> 8;
                int b = ARGB & 0xff;
                if (i % w == 0) {
                    n += 1;
                }
                rawByte[n] = (byte) r;
                rawByte[n + 1] = (byte) g;
                rawByte[n + 2] = (byte) b;
                rawByte[n + 3] = (byte) a;
                n += 4;
            }
        }
        raw = null;
        System.gc();
    }


}
