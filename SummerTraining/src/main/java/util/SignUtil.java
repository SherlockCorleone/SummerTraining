package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


//微信签名验证
public class SignUtil {
	private static String token ="Sherlock";
	
	public static boolean checkSignature(String signature,String timeStamp,String nonce) {
		String []arr=new String[] {token,timeStamp,nonce};
		//排序
		Arrays.sort(arr);
		StringBuilder content =new StringBuilder();
		for(int i=0;i<arr.length;i++) {
			content.append(arr[i]);
		}
		MessageDigest md=null;
		String tempStr=null;
		try {
			
			md=MessageDigest.getInstance("SHA-1");
			//进行加密
			byte[] digest=md.digest(content.toString().getBytes());
			tempStr=byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		content=null;
		if(tempStr!=null) {
			return tempStr.equals(signature.toUpperCase());
		}
		else return false;
	}
	//将字节数组转换未十六进制字符串
	private static String byteToStr(byte[] digest) {
		String str="";
		for(int i=0;i<digest.length;i++) {
			str+=byteToHexStr(digest[i]);
		}
		return str;
	}
	//将字节转化未十六进制字符串
	private static String byteToHexStr(byte b) {
		char [] array= {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		char [] c=new char[2];
		c[0]=array[(b>>>4)&0X0F];
		c[1]=array[b&0X0F];
		
		return new String(c);
	}
	
}

