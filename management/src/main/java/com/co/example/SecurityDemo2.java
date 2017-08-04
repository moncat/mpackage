package com.co.example;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import org.apache.commons.lang3.StringUtils;


public class SecurityDemo2 {

	public static void main(String[] args) throws Exception {

		Map<String, String> data = new TreeMap<>();
		data.put("channelNo", "A001");
		data.put("optType", "FT_P104");
		data.put("timestamp", "1501578336");
		data.put("randStr", "1891677027711c0455d99291957d710e");
		data.put("publicKeyNo", "0000");
		data.put("data", "InRlc3Qi");
		data.put("version", "1.0");
		data.put("lang", "zh_CN");

		/** ================ 签名过程 ================ */

		String sign = SignUtil.sign(data);
		
		sign = DigitalCertificateUtils.signDataWithPfxFilePath(sign, "EAccount123", "//D://data//临时使用//临时使用.pfx",
				SignatureConversionType.Hexadecimal);

		System.out.println("签名*************：" + sign);

		/** ================ 验签过程 ================ */

		String params = SignUtil.sign(data);
		System.out.println("params"+params);
//		sign="946d69be1ee58f7eb54e1fc548bd98d7205fcd70f54c2623d8aec6e4ed8f741b1c192a08e27c67f6c62f2a36c652945ea5a12d57ee48e809f1fb04d08da9481bed2df63af1400ef3f91a876b71730d3ec12f3ebc79de9526dab2d3a1cd359ca29c9b062774722d682ba86bc89b5a90426abc54bcffc77f9812f326d30980ef98";
		

		// 读取cer文件验签
		boolean verifyResult = DigitalCertificateUtils.verifyWithCerFilePath(params, sign,
				"//D://data//临时使用//临时使用.cer", SignatureConversionType.Hexadecimal);
		
		  System.out.println("验签" + verifyResult);
    
		

	}

	public static class DigitalCertificateUtils {

		private static String algorithm = "SHA1withRSA";

		private static String encodingType = "UTF-8";

		public static boolean verifyWithPemBytes(String data, String signedData, byte[] pem,
				SignatureConversionType signatureConversionType) throws Exception {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pem);
			InputStream pemInputStream = (InputStream) byteArrayInputStream;
			boolean verify = verifyWithPemStream(data, signedData, pemInputStream, signatureConversionType);
			pemInputStream.close();
			return verify;
		}

		public static boolean verifyWithPemStream(String data, String signedData, InputStream pem,
				SignatureConversionType signatureConversionType) throws Exception {
			byte[] bytesData = data.getBytes(DigitalCertificateUtils.encodingType);
			byte[] bytesSignedData = getConversionByteWithString(signedData, signatureConversionType);
			return verifyWithPemStream(bytesData, bytesSignedData, pem);
		}

		public static boolean verifyWithPemStream(byte[] data, byte[] signedData, InputStream pem) throws Exception {
			Signature instance = Signature.getInstance(algorithm);
			PublicKey publicKey = SecurityUtils.getPublicKeyFromPem(pem);
			instance.initVerify(publicKey);
			instance.update(data);
			return instance.verify(signedData);
		}

		private static byte[] getConversionByteWithString(String dataStr,
				SignatureConversionType signatureConversionType) throws Exception {
			switch (signatureConversionType) {
			case Base64WithUtf8:
				return Base64Util.decodeToBytes(dataStr);
			case Hexadecimal:
				return Byte2Hex.hex2byte(dataStr);
			default:
				throw new Exception("转换为byte[]类型时候,判断的枚举类型SignatureConversionType没处理,没有处理的类型是:"
						+ signatureConversionType.toString());
			}
		}

		private static String getConversionStringWithByte(byte[] dataByte,
				SignatureConversionType signatureConversionType) throws Exception {
			switch (signatureConversionType) {
			case Base64WithUtf8:
				return Base64Util.encode(dataByte);
			case Hexadecimal:
				return Byte2Hex.byte2Hex(dataByte);
			default:
				throw new Exception(
						"转换为字符串类型时候,判断的枚举类型SignatureConversionType没处理,没有处理的类型是:" + signatureConversionType.toString());
			}
		}

		public static String signDataWithPfxFilePath(String data, String password, String pfxFilePath,
				SignatureConversionType signatureConversionType) throws Exception {
			FileInputStream pfxInputStream = new FileInputStream(pfxFilePath);
			String sign = DigitalCertificateUtils.signDataWithPfxStream(data, password, pfxInputStream,
					signatureConversionType);
			pfxInputStream.close();
			return sign;
		}

		public static String signDataWithPfxStream(String data, String password, InputStream pfxStream,
				SignatureConversionType signatureConversionType) throws Exception {
			return getConversionStringWithByte(signDataWithPfxStreamToByteArray(data, password, pfxStream),
					signatureConversionType);
		}

		public static byte[] signDataWithPfxStreamToByteArray(String data, String password, InputStream pfxStream)
				throws Exception {
			Signature signature = Signature.getInstance(DigitalCertificateUtils.algorithm);
			PrivateKey privateKey = getPrivateKeyFromPfx(pfxStream, password);
			signature.initSign(privateKey);
			signature.update(data.getBytes(DigitalCertificateUtils.encodingType));
			return signature.sign();
		}

		public static PrivateKey getPrivateKeyFromPfx(InputStream inputStream, String password) throws Exception {
			KeyStore store = KeyStore.getInstance("PKCS12");
			store.load(inputStream, password.toCharArray());
			@SuppressWarnings({ "rawtypes" })
			Enumeration aliases = store.aliases();
			String alias = (String) aliases.nextElement();
			return ((PrivateKey) store.getKey(alias, password.toCharArray()));
		}

		/**
		 * 使用cer检验签名
		 */
		public static boolean verifyWithCerFilePath(String data, String signedData, String cerFilePath,
				SignatureConversionType signatureConversionType) throws Exception {
			FileInputStream cerInputStream = new FileInputStream(cerFilePath);
			boolean verify = DigitalCertificateUtils.verifyWithCerStream(data, signedData, cerInputStream,
					signatureConversionType);
			cerInputStream.close();
			return verify;
		}

		/**
		 * 使用cer检验签名
		 */
		public static boolean verifyWithCerStream(String data, String signedData, InputStream cerInputStream,
				SignatureConversionType signatureConversionType) throws Exception {
			byte[] bytesData = data.getBytes(DigitalCertificateUtils.encodingType);
			byte[] bytesSignedData = getConversionByteWithString(signedData, signatureConversionType);
			return DigitalCertificateUtils.verifyWithCerStream(bytesData, bytesSignedData, cerInputStream);
		}

		/**
		 * 使用cer检验签名
		 */
		public static boolean verifyWithCerStream(byte[] data, byte[] signedData, InputStream cer) throws Exception {
			Signature instance = Signature.getInstance(algorithm);
			PublicKey publicKey = SecurityUtils.getPublicKeyFromCer(cer);
			instance.initVerify(publicKey);
			instance.update(data);
			return instance.verify(signedData);
		}
	}

	public static class SignUtil {

		/**
		 * 参数排序参数后合并 —> SHA1 —> 16进制 —> 小写
		 * 
		 * @param paramValues
		 * @return
		 */
		public static String sign(Map<String, String> paramValues) {
			try {
				StringBuilder sb = new StringBuilder();
				List<String> paramNames = new ArrayList<String>(paramValues.size());
				paramNames.addAll(paramValues.keySet());
				Collections.sort(paramNames);
				for (String paramName : paramNames) {
					if (StringUtils.isNotBlank(paramValues.get(paramName)))
						sb.append(paramName).append("=").append(paramValues.get(paramName)).append("&");
				}
				byte[] sha1Digest = getSHA1Digest(sb.toString().substring(0, sb.lastIndexOf("&")));
				System.out.println(sha1Digest);
				return Byte2Hex.byte2Hex(sha1Digest).toLowerCase();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * 给字符串做SHA1加密
		 * 
		 * @param data
		 * @return
		 * @throws IOException
		 */
		private static byte[] getSHA1Digest(String data) throws IOException {
			byte[] bytes = null;
			try {
				"".toUpperCase().toLowerCase();
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				bytes = md.digest(data.getBytes("UTF-8"));
			} catch (GeneralSecurityException gse) {
				throw new IOException(gse.getMessage());
			}
			return bytes;
		}

	}

	public static class SecurityUtils {
		public static PublicKey getPublicKeyFromPem(InputStream pem) throws Exception {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
			int tmpInt = 0;
			while (-1 != (tmpInt = pem.read())) {
				byteArrayOutputStream.write(tmpInt);
			}
			byte[] bytes = byteArrayOutputStream.toByteArray();
			return getPublicKeyFromPem(bytes);
		}

		public static PublicKey getPublicKeyFromPem(byte[] pem) throws Exception {
			String publicKey = new String(pem);
			publicKey = publicKey.replace("-----BEGIN PUBLIC KEY-----", "");
			publicKey = publicKey.replace("-----END PUBLIC KEY-----", "");
			byte[] encodedKey = Base64Util.decodeToBytes(publicKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
		}

		/**
		 * 获取公钥，inputStream不会被关闭，请手动关闭
		 */
		public static PublicKey getPublicKeyFromCer(InputStream inputSteam) throws Exception {
			CertificateFactory factory = CertificateFactory.getInstance("X.509");
			return (factory.generateCertificate(inputSteam).getPublicKey());
		}
	}

	public static class Base64Util {

		private static char[] codec_table = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
				'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
				'5', '6', '7', '8', '9', '+', '/' };

		private static String defaultCharset = "UTF-8";

		/**
		 * Base64解码
		 * 
		 * @param content
		 *            字符串
		 * @return String
		 */
		public static String decode(String content) {
			return decode(content, defaultCharset);
		}

		public static String decode(String content, String charset) {
			try {
				byte[] b = new BASE64Decoder().decodeBuffer(content);
				return new String(b, charset);
			} catch (IOException e) {
				return "";
			}
		}

		/**
		 * Base64解码
		 * 
		 * @param content
		 *            字符串
		 * @return byte[]
		 */
		public static byte[] decodeToBytes(String content) {
			try {
				return new BASE64Decoder().decodeBuffer(content);
			} catch (IOException e) {
				return new byte[] {};
			}
		}

		@Deprecated
		public static String encode(byte[] content) {
			try {
				String result = new BASE64Encoder().encode(content);

				result = result.replace("\r", "");
				result = result.replace("\n", "");

				return result;
				// return new BASE64Encoder().encode(content);
			} catch (Exception e) {
				return "";
			}
		}

		public static String encodeForLong(byte[] a) {
			int totalBits = a.length * 8;
			int nn = totalBits % 6;
			int curPos = 0;// process bits
			StringBuffer toReturn = new StringBuffer();
			while (curPos < totalBits) {
				int bytePos = curPos / 8;
				switch (curPos % 8) {
				case 0:
					toReturn.append(codec_table[(a[bytePos] & 0xfc) >> 2]);
					break;
				case 2:

					toReturn.append(codec_table[(a[bytePos] & 0x3f)]);
					break;
				case 4:
					if (bytePos == a.length - 1) {
						toReturn.append(codec_table[((a[bytePos] & 0x0f) << 2) & 0x3f]);
					} else {
						int pos = (((a[bytePos] & 0x0f) << 2) | ((a[bytePos + 1] & 0xc0) >> 6)) & 0x3f;
						toReturn.append(codec_table[pos]);
					}
					break;
				case 6:
					if (bytePos == a.length - 1) {
						toReturn.append(codec_table[((a[bytePos] & 0x03) << 4) & 0x3f]);
					} else {
						int pos = (((a[bytePos] & 0x03) << 4) | ((a[bytePos + 1] & 0xf0) >> 4)) & 0x3f;
						toReturn.append(codec_table[pos]);
					}
					break;
				default:
					break;
				}
				curPos += 6;
			}
			if (nn == 2) {
				toReturn.append("==");
			} else if (nn == 4) {
				toReturn.append("=");
			}
			return toReturn.toString();

		}
	}

	/**
	 * 签名转换类型(转码方式)
	 * 
	 * @author Administrator
	 *
	 */
	public enum SignatureConversionType {
		/**
		 * utf-8编码的bas64
		 */
		Base64WithUtf8,
		/**
		 * 十六进制
		 */
		Hexadecimal
	}

	public static class Byte2Hex {

		/**
		 * 
		 * 字节转换为 16 进制字符串
		 * 
		 * @param b
		 *            字节
		 * @return
		 */
		public static String byte2Hex(byte b) {
			String hex = Integer.toHexString(b);
			if (hex.length() > 2) {
				hex = hex.substring(hex.length() - 2);
			}
			while (hex.length() < 2) {
				hex = "0" + hex;
			}
			return hex;
		}

		/**
		 * 
		 * 字节数组转换为 16 进制字符串
		 * 
		 * @param bytes
		 *            字节数组
		 * @return
		 */
		public static String byte2Hex(byte[] bytes) {
			Formatter formatter = new Formatter();
			for (byte b : bytes) {
				formatter.format("%02x", b);
				System.out.println("b***"+b);
			}
			String hash = formatter.toString();
			System.out.println("hash***"+hash);
			formatter.close();
			return hash;
		}

		/**
		 * 将16进制字符串转换为字节
		 * 
		 * @param strhex
		 * @return
		 */
		public static byte[] hex2byte(String strhex) {
			if (strhex == null)
				return null;

			int l = strhex.length();
			if (l % 2 == 1)
				return null;

			byte[] b = new byte[l / 2];
			for (int i = 0; i != l / 2; ++i)
				b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);

			return b;
		}
	}

	public static class SHA1 {
		public static String ALGORITHM = "SHA1";

		public static String sha1ReturnString(String data) {
			StringBuilder sha1Str = new StringBuilder();
			try {
				MessageDigest sha1 = MessageDigest.getInstance(SHA1.ALGORITHM);
				byte[] bytes = sha1.digest(data.getBytes("UTF-8"));
				for (int i = 0; i < bytes.length; i++) {
					sha1Str.append(Byte2Hex.byte2Hex(bytes[i]));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return sha1Str.toString();
		}
	}

}
