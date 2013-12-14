package com.relurori.reflection;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SyncFragment extends Fragment {

	private static final String TAG = SyncFragment.class.getSimpleName();
	
	private View mView = null;
	private Button mButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		mView = inflater.inflate(R.layout.fragment_sync, container, false);
		
		pre();
		
		return mView;
	}

	private void pre() {
		mButton = (Button)mView.findViewById(R.id.buttonSync);
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tv;
				tv = (TextView)mView.findViewById(R.id.destinationDevice);
				String dstUri = tv.getText().toString();
				tv = (TextView)mView.findViewById(R.id.sourceDevice);
				String srcUri = tv.getText().toString();
				File src = null;
				File dst = null;
				try {
					src = new File(new URI(srcUri));
					dst = new File(new URI(dstUri));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Context ctx = getActivity().getApplicationContext();
				
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
			
			while (fileFunc.getCopyStatus() != FileCopyConstants.CopyThreadStatus.COMPLETE) {
				copied = fileFunc.getFilesCopiedCount();
				toCopy = fileFunc.getFilesToCopyCount();
				progress = (double) copied / (double) toCopy;
				publishProgress((int) (progress * 100));

			}
			
			
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