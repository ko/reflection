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

import android.util.Log;

public class FileUtils {

	private static final String TAG = FileUtils.class.getSimpleName();
	
	public static boolean FileIsDuplicate(File src, File dst) {
		boolean result = false;
		
		if (dst.exists()) {
			if (calculateMd5(src) == calculateMd5(dst)) {
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
	
	public static void CopyDirectory(File src, File dst, boolean recursive) throws IOException {

		if (FileIsDuplicate(src,dst)) {
			return;
		}
		
		if (src.isDirectory()) {
			// like -p
			if (dst.exists() == false) {
				dst.mkdirs();
			}
			
			String[] children = src.list();
			for (int i = 0; i < src.listFiles().length; i++) {
				FileUtils.CopyDirectory(new File(src, children[i]), 
										new File(dst, children[i]), 
										recursive);
			}
		} else {
			// File!
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dst);
			
			byte[] buf = new byte[1024];
			int len;
			
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			
			in.close();
			out.close();
		}
		
		
	}

	public static int FileCountToCopy(File src, File dst) {
		int fileCount = 0;
		return fileCount;
	}
}
