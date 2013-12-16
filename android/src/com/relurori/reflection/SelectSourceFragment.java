package com.relurori.reflection;

import java.io.File;
import java.util.ArrayList;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SelectSourceFragment extends Fragment {

	private View mView = null;
	private Button mButton;
	ArrayList<String> deviceArrayList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		mView = inflater.inflate(R.layout.fragment_source_device, container, false);
		
		pre();
		
		return mView;
	}

	private void pre() {

		deviceArrayList = new ArrayList<String>();
		
		mButton = (Button)mView.findViewById(R.id.rescanDevices);
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(getActivity());
		        dialog.setContentView(R.layout.device_list);
		        
		        populateStorageList();
		        
		        ListView list = (ListView)dialog.findViewById(R.id.devicesList);
		        dialog.setTitle("Heart attack and shock");
		        dialog.setCancelable(true);
		        dialog.setCanceledOnTouchOutside(true);
		        ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity(),android.R.layout.simple_list_item_1,deviceArrayList); 
		        list.setAdapter(adapter);

		        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						
						TextView tv = (TextView)mView.findViewById(R.id.sourceDevice);
						if (tv.getText().equals("")) {
							tv.setText((String)parent.getItemAtPosition(position));
							
							dialog.cancel();
							
							return;
						}
						
						tv = (TextView)mView.findViewById(R.id.destinationDevice);
						if (tv.getText().equals("")) {
							tv.setText((String)parent.getItemAtPosition(position));
							
							dialog.cancel();
							
							return;
						}
						
					}
				});
		        
		        dialog.show();
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
	    //deviceListView.setAdapter(mArrayAdapter);
	}
	
}
