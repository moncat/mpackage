package com.co.example;


import java.security.KeyStore;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Test5{
	public static void main(String[] args){
		
//		convert(args[0],args[1],args[2],args[3],args[4],args[5],
//				args[6],args[7],args[8],args[9],args[10]);
		//convert("JKS","test.jks","abc123","kp2","abc123","kp2",
		//		"PKCS12","test.p12","abc123","kp2","abc123");
		//convert("PKCS12","test.p12","abc123","kp2","abc123","kp2",
		//    	"JKS","test1.jks","abc123","kp2","abc123");
		
		convert("JKS","D:\\kkk\\share.jks","123456","share","123456","share",
						"PKCS12","share.p12","123456","share","123456");
	}
	public static void convert(String storeType1
								, String stroe1FileName
								, String store1Passwd
								, String store1KeyAlias		
								, String store1KeyPasswd
								, String store1CertChainAlias
								
								, String storeType2
								, String store2FileName
								, String store2Passwd
								, String store2KeyAlias
								, String store2KeyPasswd ){
		try{
		
			FileInputStream fis = new FileInputStream(stroe1FileName);
			KeyStore keyStore1 = KeyStore.getInstance(storeType1);
			
			keyStore1.load (fis, store1Passwd.toCharArray());
			Key key = keyStore1.getKey(store1KeyAlias ,store1KeyPasswd.toCharArray());
			KeyFactory keyfact = java.security.KeyFactory.getInstance(key.getAlgorithm());
			PrivateKey priKey = keyfact.generatePrivate(new PKCS8EncodedKeySpec(key.getEncoded()));
		
			KeyStore keystore2 = KeyStore.getInstance(storeType2);
			keystore2.load(null, null);
			keystore2.setKeyEntry(store2KeyAlias, priKey, store2KeyPasswd.toCharArray(),keyStore1.getCertificateChain(store1CertChainAlias));
			keystore2.store(new FileOutputStream(store2FileName), store2Passwd.toCharArray());
		}catch(Exception e){
			e.printStackTrace(System.out); 
		}
	}	
}
