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
import android.app.Dialog;
import android.app.Fragment;
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
	Button button;
	
	private Fragment mTempFragment = null;
	private InstructionFragment mInstructionFragment = new InstructionFragment();
	private SelectSourceFragment mSourceFragment = new SelectSourceFragment();
	private SelectDestinationFragment mDestinationFragment = new SelectDestinationFragment();
	private SyncFragment mSyncFragment = new SyncFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pre();
	}

	private void pre() {
		
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
		
		
		
		
	}

}
