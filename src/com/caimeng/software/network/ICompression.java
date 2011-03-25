package com.caimeng.software.network;

public interface ICompression {
	
	/**
	 * 解压缩字节数组
	 * @param input
	 * @return
	 */
    byte[] Decompress(byte[] input);

    /**
     * 压缩字节数组
     * @param input
     * @return
     */
    byte[] Compress(byte[] input);

    /**
     * 检测字节数组是否被压缩过（基于实现的压缩算法进行检测）
     * @param input
     * @return
     */
    boolean IsCompressed(byte[] input);

    /**
     * 当向服务器提交的字节流长度大于此长度时，执行该实例提供的压缩方法
     * @return
     */
    int getCompressWhenGreaterThan();
}
