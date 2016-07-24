/*
 * @(#)MD5.java 1.0 2011-8-20 Copyright 2010 shao, Inc. All rights reserved.
 */
package com.gj.test.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5 {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static String byteArrayToHexString( byte[] b ) {
		StringBuffer resultSb = new StringBuffer();
		for( int i = 0; i < b.length; i++ ) {
			resultSb.append( byteToHexString( b[i] ) );
		}
		return resultSb.toString();
	}

	private static String byteToHexString( byte b ) {
		int n = b;
		if( n < 0 )
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	private static String MD5Encode_1( String origin ) {
		String resultString = null;
		try {
			resultString = new String( origin );
			MessageDigest md = MessageDigest.getInstance( "MD5" );
			resultString = byteArrayToHexString( md.digest( resultString.getBytes() ) );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		return resultString;
	}

	/**
	 * 两次MD5摘要
	 * @param origin 原字符串
     */
	public static String MD5Encode( String origin ) {
		String resultString = MD5Encode_1( origin );
		resultString = MD5Encode_1( resultString );
		return resultString;
	}

	/**
	 * 带盐值的MD5加密方式（by shiro）
	 *
	 * @param plainText 明文
	 * @param salt 盐值
	 * @return 密文
	 */
	public static String MD5Encode( String plainText, String salt ) {
		String encryptedPassword = new SimpleHash( "md5", plainText, ByteSource.Util.bytes( salt ), 2 ).toHex();
		return encryptedPassword;
	}

	/**
	 * 对文件全文生成MD5摘要
	 *
	 * @param file 要加密的文件
	 * @return MD5摘要码
	 */
	public static String getMD5( File file ) {
		FileInputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance( "MD5" );
			fis = new FileInputStream( file );
			byte[] buffer = new byte[2048];
			int length = -1;
			long s = System.currentTimeMillis();
			while( ( length = fis.read( buffer ) ) != -1 ) {
				md.update( buffer, 0, length );
			}
			byte[] b = md.digest();
			return byteArrayToHexString( b );
			// 16位加密
			// return buf.toString().substring(8, 24);
		} catch( Exception ex ) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				fis.close();
			} catch( IOException ex ) {
				ex.printStackTrace();
			}
		}
	}
}
