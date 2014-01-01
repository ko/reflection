package com.relurori.reflection;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SyncFragment extends Fragment {

	private static final String TAG = SyncFragment.class.getSimpleName();
	
	private View mView = null;
	private ImageButton mButton;
	
	private TextView tv;
	
	public SyncFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		Log.d(TAG,"0");
		
		if (container == null) 
			return null;
		
		mView = inflater.inflate(R.layout.fragment_sync, container, false);

		Log.d(TAG,"1");
		
		
		
		pre();
		
		return mView;
	}

	private void pre() {
		tv = (TextView) mView.findViewById(R.id.textView1);
		
		tv.setText(MainActivity.getSrc() + " to " + MainActivity.getDst());
		
		mButton = (ImageButton)mView.findViewById(R.id.btnSync);
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String dstUri = MainActivity.getDst();
				String srcUri = MainActivity.getSrc();
				
				File src = null;
				File dst = null;

				tv.setText(srcUri + " to " + dstUri);
				Log.d(TAG,"srcUri=" + srcUri);
				Log.d(TAG,"dstUri=" + dstUri);
				
				Uri uri = Uri.parse(srcUri);
				src = new File(uri.getPath());
				uri = Uri.parse(dstUri);
				dst = new File(uri.getPath());

				Log.d(TAG,"pre|srcUri=" + srcUri);
				Log.d(TAG,"pre|uri=" + uri.toString());
				Log.d(TAG,"pre|src=" + src.toURI());
				
				Context ctx = getActivity().getApplicationContext();
				ctx = getActivity();
				
				new CopyFilesAsync(ctx, src, dst).execute();
			}
		});
	}
	

	class CopyFilesAsync extends AsyncTask<Void,Integer,Void> {
		
		private ProgressDialog dialog;
		File src, dst;
		Context context;
		FileCopy fileFunc;
		
		public CopyFilesAsync(Context ctx, File sFile, File dFile) {
			src = sFile;
			dst = dFile;
			context = ctx;
		}

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(context);
			dialog.setIndeterminate(true);
			dialog.setMessage("Copying...");
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.show();

			fileFunc = new FileCopy(src,dst);
		}
		
		@Override
		protected Void doInBackground(Void... voids) {

			int ONE_SECOND_TO_MS = 1000;
			int copied = 0, toCopy = 0;
			double progress = 0;
			
			Log.d(TAG,"doInBackground|filesCopied=" + progress);
			
			while (fileFunc.getToCopyStatus() != FileCopyConstants.ToCopyThreadStatus.COMPLETE) {
				try {
					Thread.sleep(ONE_SECOND_TO_MS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			Log.d(TAG,"doInBackground|toCopy complete");
			fileFunc.CopyAllFiles();
			
			while (fileFunc.getCopyStatus() != FileCopyConstants.CopyThreadStatus.COMPLETE) {
				copied = fileFunc.getFilesCopiedCount();
				toCopy = fileFunc.getFilesToCopyCount();
				progress = (double) copied / (double) toCopy;
				publishProgress((int) (progress * 100));
				
				try {
					Thread.sleep(ONE_SECOND_TO_MS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			dialog.dismiss();
			
			/*
			try {
				int progress = FileUtils.FileCountToCopy(src, dst);
				publishProgress(progress);
				FileUtils.CopyDirectory(src, dst, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
			
			return null;
		}
		
		protected void onProgressUpdate(Integer...integers) {
			dialog.setProgress(integers[0]);
		}
	}
}
