package com.relurori.reflection;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final String TAG = MainActivity.class.getSimpleName();
	
	ListView deviceListView;
	ArrayAdapter mArrayAdapter;
	ArrayList<String> deviceArrayList;
	Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pre();
		
		populateStorageList();
	}

	private void pre() {
		deviceArrayList = new ArrayList<String>();
		deviceListView = (ListView) findViewById(R.id.deviceList);
		mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  
                deviceArrayList);
		
		deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				TextView tv = (TextView)findViewById(R.id.sourceDevice);
				if (tv.getText().equals("")) {
					tv.setText((String)parent.getItemAtPosition(position));
					return;
				}
				
				tv = (TextView)findViewById(R.id.destinationDevice);
				if (tv.getText().equals("")) {
					tv.setText((String)parent.getItemAtPosition(position));
					return;
				}
			}
		});
		
		button = (Button)findViewById(R.id.clearDestinationDevice);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tv = (TextView)findViewById(R.id.destinationDevice);
				tv.setText("");
			}
		});
		
		button = (Button)findViewById(R.id.clearSourceDevice);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tv = (TextView)findViewById(R.id.sourceDevice);
				tv.setText("");
			}
		});
		
		button = (Button)findViewById(R.id.buttonSync);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tv;
				tv = (TextView)findViewById(R.id.destinationDevice);
				String dstUri = tv.getText().toString();
				tv = (TextView)findViewById(R.id.sourceDevice);
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
				Context ctx = MainActivity.this;
				new CopyFilesAsync(ctx, src, dst).execute();
			}
		});
	}

	public void populateStorageList()
	{
	    File innerDir = Environment.getExternalStorageDirectory();	/* as /storage/emulated/0 */
	    File rootDir = innerDir.getParentFile();	/* as /storage/emulated */
	    rootDir = rootDir.getParentFile();			/* as /storage */
	    File firstExtSdCard = innerDir ;
	    File[] files = rootDir.listFiles();
	    for (File file : files) {
	    	if (file.listFiles() != null) {
	    		deviceArrayList.add(file.toURI().toString() /*+ "|" + file.listFiles().length */);
	    	}
	    }
	    deviceListView.setAdapter(mArrayAdapter);
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
			fileFunc = new FileCopy(src,dst);
		}

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(context);
			dialog.setMessage("Copying...");
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... voids) {

			int progress = fileFunc.getFilesToCopyCount();
			Log.d(TAG,"doInBackground|fileCount=" + progress);
			publishProgress(progress);
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
