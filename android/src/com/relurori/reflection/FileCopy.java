package com.relurori.reflection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.relurori.reflection.FileCopyConstants.ToCopyThreadStatus;

import android.util.Log;

public class FileCopy extends FileUtils {

	private static final String TAG = FileCopy.class.getSimpleName();
	
	ToCopyThreadStatus tStatus = ToCopyThreadStatus.NOT_RUNNING;
	
	List<File> filesToCopy = new ArrayList<File>();
	List<File> filesCopied = new ArrayList<File>();
	List<File> dirsToCopy = new ArrayList<File>();
	List<File> dirsCopied = new ArrayList<File>();
	
	public FileCopy(File source, File destination) {
		super(source, destination);
		SetFilesToCopyThread();
	}

	private void SetFilesToCopyThread() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				tStatus = ToCopyThreadStatus.RUNNING;
				Log.d(TAG,"SetFilesToCopyThread|tStatus=" + tStatus);
				setFilesToCopy();
				tStatus = ToCopyThreadStatus.COMPLETE;
				Log.d(TAG,"SetFilesToCopyThread|tStatus=" + tStatus);
			}
			
		}).run();
	}

	/**
	 * populate filesToCopy with files to copy
	 */
	private void setFilesToCopy() {
		SetFilesToCopyRecursive(src,dst);
		Log.d(TAG,"FilesToCopy.size()=" + filesToCopy.size());
		Log.d(TAG,"DirsToCopy.size()=" + dirsToCopy.size());
	}
	
	public void SetFilesToCopyRecursive(File source, File destination) {
		if (FileIsDuplicate(source,destination)) {
			return;
		}
		
		if (source.isDirectory()) {
			if (destination.exists() == false) {
				// add directory and contents
				if (dirsToCopy.contains(source)) {
					dirsToCopy.add(source);
				}
			}
			String[] children = source.list();
			
			if (children == null) {
				return;
			}
			
			for (int i = 0; i < source.listFiles().length; i++) {
				SetFilesToCopyRecursive(new File(source,children[i]), new File(destination,children[i]));
			}
		} else if (source.isFile()) {
			if (filesToCopy.contains(source)) {
				filesToCopy.add(source);
			}
		}
	}
	
	public void CopyAllFiles() {
		CopyDirectories();
		CopyFiles();
	}
	
	private void CopyDirectories() {
		for (File dir : dirsToCopy) {
			CopyDirectory(dir);
			dirsToCopy.remove(dir);
			dirsCopied.add(dir);
		}
	}

	private void CopyDirectory(File dir) {
		// TODO Auto-generated method stub
		
	}

	private void CopyFiles() {
		for (File file : filesToCopy) {
			CopyFile(file);
			filesToCopy.remove(file);
			filesCopied.add(file);
		}
	}

	private void CopyFile(File file) {
		// TODO Auto-generated method stub
		
	}

	/*
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
	*/

	public int getFilesToCopyCount() {
		if (tStatus != ToCopyThreadStatus.COMPLETE) {
			Log.d(TAG,"getFilesToCopyCount|tStatus=" + tStatus);
			filesToCopy.size();
		}
		return filesToCopy.size();
	}
}
