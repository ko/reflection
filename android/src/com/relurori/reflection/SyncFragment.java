package com.relurori.reflection;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
	private ViewPager pager = null;
	
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
		
		pager = (MyViewPager) getActivity().findViewById(R.id.viewpager);
		
		tv = (TextView) mView.findViewById(R.id.textView1);
		
		tv.setText(MainActivity.getSrc() + " to " + MainActivity.getDst());
		
		
		
		/***** DEBUG *****/
		//tv.setVisibility(View.GONE);
		
		
		
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
	

	class CopyFilesAsync extends AsyncTask<Void,Integer,Integer[]> {
		
		File src, dst;
		Context context;
		FileCopy fileFunc;
		PowerManager pm;
		PowerManager.WakeLock wl;
		
		CopyDialogFragment cpDialog;
		
		
		public CopyFilesAsync(Context ctx, File sFile, File dFile) {
			src = sFile;
			dst = dFile;
			context = ctx;
		}

		@Override
		protected void onPreExecute() {
			
			pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
			wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK |
								PowerManager.ON_AFTER_RELEASE, 
								TAG);
			wl.acquire();
			

			((MainActivity)context).showProgressDialog();

			fileFunc = new FileCopy(src,dst);
		}
		
		@Override
		protected Integer[] doInBackground(Void... voids) {

			int ONE_SECOND_TO_MS = 1000;
			int copied = 0, toCopy = 0;
			int progress = 0;
			
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
			toCopy = fileFunc.getFilesToCopyCount();
			fileFunc.CopyAllFiles();
			
			while (fileFunc.getCopyStatus() != FileCopyConstants.CopyThreadStatus.COMPLETE) {
				copied = fileFunc.getFilesCopiedCount();
				progress = (int) (((double) copied / (double) toCopy) * 100);
				
				publishProgress(progress, copied, toCopy);
				
				try {
					Thread.sleep(ONE_SECOND_TO_MS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// aesthetics purely
			if (toCopy > 0) {
				publishProgress(100, toCopy, toCopy);
				try {
					Thread.sleep((int)(ONE_SECOND_TO_MS * 0.5));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			Log.d(TAG,"doInBackground|copy complete");
			
			Integer[] ret = new Integer[2];
			ret[0] = copied;
			ret[1] = toCopy;
			return ret;
		}
		
		@Override
		protected void onPostExecute(Integer... i) {

			((MainActivity)context).removeProgressDialog();
			
			wl.release();
	
			/* as long as SyncDoneFragment proceeds SyncFragment, it
			 * will have been inflated/instantiated/created.
			 */
			SyncDoneFragment.setCopied(i[0],i[1]);
			
			
			pager.setCurrentItem(MainConstants.PAGER_DONE_INDEX, true);
		}
		
		@Override
		protected void onProgressUpdate(Integer... progress) {
			
			((MainActivity)context).updateProgressDialog(progress[0], progress[1], progress[2]);
			
		}
	}
}
