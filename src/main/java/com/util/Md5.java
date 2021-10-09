package com.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window - Preferences - Java - Code Style - Code
 * Templates
 */
public class Md5 {

	/*******************************************************************************************************************
	 * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
	 * 
	 * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661 MD5 ("abc") =
	 * 900150983cd24fb0d6963f7d28e17f72 MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0 MD5
	 * ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
	 ******************************************************************************************************************/
	private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public String encode(String origin) {
		String resultString = null;

		try {
			resultString = String.valueOf(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

	public String decode(String origin) {
		return null;
	}

	/**
	 * MD5方法
	 *
	 * @param text 明文
	 * @param key 密钥
	 * @return 密文
	 * @throws Exception
	 */
	public static String md5(String text, String key) throws Exception {
		//加密后的字符串
		String encodeStr= DigestUtils.md5Hex(text + key);
//        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
		return encodeStr;
	}

	public static void main(String[] args) {
		try {
			System.out.println(Md5.md5("Tf#22","lampblack"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
