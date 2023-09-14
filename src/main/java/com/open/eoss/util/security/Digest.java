package com.open.eoss.util.security;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {
	/*
	 * 消息摘要MD5
	 * 麻省理工学院Ronald Rivest提出的MD5
	 * 得到32位MD5加密密码
	 */
	public static String MD5(String str, String salt) {
		if(StringUtils.isEmpty(str)){
			return null;
		}

		byte[] encodeBytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			if(StringUtils.isBlank(salt)){
				encodeBytes = md.digest(str.getBytes("UTF-8"));
			}else{
				encodeBytes = md.digest((str + salt).getBytes("UTF-8"));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return byteToHex(encodeBytes);
	}

	/*
	 * 消息摘要MD5 
	 * 麻省理工学院Ronald Rivest提出的MD5 
	 * 得到32位MD5加密密码
	 */
	public static String MD5(String str) {
		if(StringUtils.isEmpty(str)){
			return null;
		}

		byte[] encodeBytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			encodeBytes = md.digest(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return byteToHex(encodeBytes);
	}

	/*
	 * 消息摘要SHA 
	 * 美国国家标准技术研究所的SHA 
	 * 得取40位的SHA1加密密码
	 */
	public static String SHA1(String str) {
		byte[] encodeBytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			encodeBytes = md.digest(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return byteToHex(encodeBytes);
	}

	/*
	 * 二行制转字符串
	 */
	private static String byteToHex(byte[] hash) {
		if(hash == null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			if ((0xff & hash[i]) < 0x10) {
				sb.append("0" + Integer.toHexString((0xFF & hash[i])));
			} else {
				sb.append(Integer.toHexString(0xFF & hash[i]));
			}
		}
		return sb.toString();
	}
}
