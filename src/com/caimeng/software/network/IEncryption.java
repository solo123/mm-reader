package com.caimeng.software.network;

public interface IEncryption
{
	/**
     * 解密字节数组
     * @param input
     * @return
     */
    byte[] Decrypt ( byte[] input );

    /**
     * 加密字节数组
     * @param input
     * @return
     */
    byte[] Encrypt( byte[] input );

    /**
     * 检测字节数组是否被加密过（基于实现的加密算法进行检测）
     * @param input
     * @return
     */
    boolean IsEncrypted( byte[] input );
}
