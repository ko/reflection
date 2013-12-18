package com.relurori.reflection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SelectDestinationFragment extends Fragment {

	private String TAG = SelectDestinationFragment.class.getSimpleName();
	
	private View mView = null;
	private Button mButton;
	List<String> postDeviceArrayList = new ArrayList<String>();
	List<String> preDeviceArrayList = new ArrayList<String>();
	List<String> newDeviceArrayList = new ArrayList<String>();
	
	public SelectDestinationFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		mView = inflater.inflate(R.layout.fragment_destination_device, container, false);
		
		Log.d(TAG,"1");
		pre();

		Log.d(TAG,"2");
		populateStorageList(preDeviceArrayList);
		
		return mView;
	}
	

	private void pre() {
		
		mButton = (Button)mView.findViewById(R.id.btnScan2);
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(getActivity());
		        dialog.setContentView(R.layout.device_list);
		        
		        populateStorageList(postDeviceArrayList);
		        
		        for (int i = 0; i < postDeviceArrayList.size(); i++) {
		        	if (preDeviceArrayList.contains(postDeviceArrayList.get(i)) == false) {
		        		newDeviceArrayList.add(postDeviceArrayList.get(i));
		        	}
		        }
		        
		        Log.d(TAG,"newList=" + newDeviceArrayList.toString());
		        TextView tv = (TextView)mView.findViewById(R.id.textView12);
		        tv.setText("newList=" + newDeviceArrayList.toString());
		        
		        tv = (TextView)mView.findViewById(R.id.tvDst);
		        tv.setText(newDeviceArrayList.toString());
			}
		});
	}



	public void populateStorageList(List<String> deviceArrayList)
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
	}
	
}
