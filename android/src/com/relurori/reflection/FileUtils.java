package com.relurori.reflection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

	public static void CopyDirectory(File src, File dst, boolean recursive) throws IOException {

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
}
