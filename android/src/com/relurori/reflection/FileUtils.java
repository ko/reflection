package com.relurori.reflection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class FileUtils {

	private static final String TAG = FileUtils.class.getSimpleName();
	
	File src;
	File dst;
	
	public FileUtils(File source, File destination) {
		src = source;
		dst = destination;
	}
	
	public static boolean FileIsDuplicate(File source, File destination) {
		boolean result = false;
		String smd5, dmd5;
		
		if (source.isDirectory() || destination.isDirectory()) {
			return false;
		}
		
		if (destination.exists()) {
			smd5 = calculateMd5(source);
			dmd5 = calculateMd5(destination);

			if (smd5 == dmd5) {
				result = true;
			}
		}
		
		return result;
	}
	
	public static String calculateMd5(File file) {

		InputStream is;
		MessageDigest digest;
		
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG,"calculateMd5|getInstance failed");
			return null;
		}
		
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG,"calculateMd5|FileInputStream failed");
			return null;
		}
		
		byte[] buf = new byte[8192];
		int read;
		try {
			while ((read = is.read(buf)) > 0) {
				digest.update(buf, 0, read);
			}
			byte[] md5sum = digest.digest();
			BigInteger bi = new BigInteger(1, md5sum);
			String csum = bi.toString(10);
			
			csum = String.format("%32s", csum).replace(' ', '0');
			return csum;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("calculateMd5|processing failed");
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Log.d(TAG, "calculateMd5|closing file failed");
			}
		}
	}
}
