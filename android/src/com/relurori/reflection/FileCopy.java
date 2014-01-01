package com.relurori.reflection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.relurori.reflection.FileCopyConstants.CopyThreadStatus;
import com.relurori.reflection.FileCopyConstants.ToCopyThreadStatus;

import android.util.Log;

public class FileCopy extends FileUtils {

	private static final String TAG = FileCopy.class.getSimpleName();
	
	ToCopyThreadStatus tStatus = ToCopyThreadStatus.NOT_RUNNING;
	CopyThreadStatus cStatus = CopyThreadStatus.NOT_RUNNING;
	
	Map<File,File> filesCopied = new HashMap<File,File>();
	Map<File,File> filesToCopy = new HashMap<File,File>();
	
	public FileCopy(File source, File destination) {
		super(source, destination);
		
		tStatus = ToCopyThreadStatus.NOT_RUNNING;
		cStatus = CopyThreadStatus.NOT_RUNNING;
		
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
			
		}).start();
	}

	/**
	 * populate filesToCopy with files to copy
	 */
	private void setFilesToCopy() {
		SetFilesToCopyRecursive(src,dst);
		Log.d(TAG,"FilesToCopy.size()=" + filesToCopy.size());
		//Log.d(TAG,"DirsToCopy.size()=" + dirsToCopy.size());
	}
	
	public void SetFilesToCopyRecursive(File source, File destination) {
		if (FileIsDuplicate(source,destination)) {
			//Log.d(TAG,"SetFilesToCopyRecursive|src=" + source.toURI());
			return;
		}
		
		if (source.isDirectory()) {
			String[] children = source.list();
			
			if (children == null) {
				//Log.d(TAG,"SetFilesToCopyRecursive|source dir children empty");
				return;
			}
			
			for (int i = 0; i < source.listFiles().length; i++) {
				SetFilesToCopyRecursive(new File(source,children[i]), new File(destination,children[i]));
			}
		} else if (source.isFile()) {
			//Log.d(TAG,"SetFilesToCopyRecursive|add file=" + source.toURI());
			filesToCopy.put(source,destination);
		}
	}
	
	public void CopyAllFiles() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				cStatus = CopyThreadStatus.RUNNING;
				CopyFiles();
				cStatus = CopyThreadStatus.COMPLETE;
			}
			
		}).start();
	}

	private void CopyFiles() {
		Log.d(TAG,"CopyFiles");
		Iterator<Entry<File, File>> it = filesToCopy.entrySet().iterator();
		Log.d(TAG,"CopyFiles|filesToCopy.size=" + filesToCopy.size());
		while (it.hasNext()) {
			Map.Entry<File, File> kv = (Entry<File, File>)it.next();
			try {
				Log.d(TAG,"CopyFiles|src=" + kv.getKey());
				Log.d(TAG,"CopyFiles|dst=" + kv.getValue());
				CopyFile(kv.getKey(),kv.getValue());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO keep track of copied files? for resume, presumably
			filesCopied.put(kv.getKey(), kv.getValue());
			// TODO remove current from filesToCopy?
		}
	}

	private void CopyFile(File source, File destination) throws IOException {
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
		}
		return filesToCopy.size();
	}
	
	public int getFilesCopiedCount() {
		return filesCopied.size();
	}
	
	public int getCopyCount() {
		if (cStatus != CopyThreadStatus.COMPLETE) {
			Log.d(TAG,"getCopyCount|cStatus=" + cStatus);
		}
		return filesCopied.size();
	}
	
	public CopyThreadStatus getCopyStatus() {
		return cStatus;
	}
	
	public ToCopyThreadStatus getToCopyStatus() {
		return tStatus;
	}
}
