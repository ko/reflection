package com.relurori.reflection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileCopy extends FileUtils {

	List<File> filesToCopy = new ArrayList<File>();
	List<File> filesCopied = new ArrayList<File>();
	
	public FileCopy(File source, File destination) {
		super(source, destination);

	}

	/**
	 * populate filesToCopy with files to copy
	 */
	private void setFilesToCopy() {
		
	}
	

	public void CopyDirectory() throws IOException {
		CopyDirectoryRecursive(src,dst,true);
	}
	
	public static void CopyDirectoryRecursive(File source, File destination, boolean recursive) throws IOException {

		if (FileIsDuplicate(source,destination)) {
			return;
		}
		
		if (source.isDirectory()) {
			// like -p
			if (destination.exists() == false) {
				destination.mkdirs();
			}
			
			String[] children = source.list();
			for (int i = 0; i < source.listFiles().length; i++) {
				CopyDirectoryRecursive(new File(source, children[i]), 
										new File(destination, children[i]), 
										recursive);
			}
		} else {
			// File!
			InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(destination);
			
			byte[] buf = new byte[1024];
			int len;
			
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			
			in.close();
			out.close();
		}
	}

	public int getFilesToCopyCount() {
		return filesToCopy.size();
	}
}
