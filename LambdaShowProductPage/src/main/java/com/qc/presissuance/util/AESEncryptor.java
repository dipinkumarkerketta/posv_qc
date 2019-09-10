package com.qc.presissuance.util;


public class AESEncryptor 
{
	public static String encrypt(String str)
	{
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < chars.length; i++)
		{
			sb.append(Integer.toHexString((int)chars[i]));
		}		 
		return sb.toString();
	}

	public static String decrypt(String str)
	{
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for( int i=0; i<str.length()-1; i+=2 )
		{
			String output = str.substring(i, (i + 2));
			int decimal = Integer.parseInt(output, 16);
			sb.append((char)decimal);		 
			temp.append(decimal);
		}
		return sb.toString();
	}

}
