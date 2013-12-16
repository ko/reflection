package com.relurori.reflection;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SelectDestinationFragment extends Fragment {

	private View mView = null;
	private Button mButton;
	ArrayList<String> deviceArrayList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		mView = inflater.inflate(R.layout.fragment_destination_device, container, false);
		
		//pre();
		
		return mView;
	}
	

	private void pre() {
		deviceArrayList = new ArrayList<String>();
		
		
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
