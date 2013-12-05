package com.relurori.reflection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.PendingIntent;
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
	    		deviceArrayList.add(file.toURI().toString() + "|" + file.listFiles().length);
	    	}
	    }

	    deviceListView.setAdapter(mArrayAdapter);
	}
}
