package com.relurori.reflection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectSourceFragment extends Fragment {

	private static final String TAG = SelectSourceFragment.class.getSimpleName();
	
	private View mView = null;
	private MyViewPager pager = null;
	private Button mButton;
	List<String> postDevList = new ArrayList<String>();
	List<String> preDevList = new ArrayList<String>();
	List<String> newDevList = new ArrayList<String>();
	
	public SelectSourceFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		Log.d(TAG,"0");
		
		if (container == null) 
			return null;
		
		mView = inflater.inflate(R.layout.fragment_source_device, container, false);
		
		Log.d(TAG,"1");
		pre();
		
		Log.d(TAG,"2");
		populateStorageList(preDevList);
		
		return mView;
	}

	private void pre() {
		
		pager = (MyViewPager) getActivity().findViewById(R.id.viewpager);
		
		mButton = (Button)mView.findViewById(R.id.btnScan);
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
		        populateStorageList(postDevList);
		        
		        for (int i = 0; i < postDevList.size(); i++) {
		        	if (preDevList.contains(postDevList.get(i)) == false) {
		        		newDevList.add(postDevList.get(i));
		        	}
		        }
		        
		        if (newDevList.isEmpty()) {
		        	Log.d(TAG,"empty source");
		        	Toast.makeText(getActivity().getBaseContext(), "src=" + newDevList.toString(), Toast.LENGTH_LONG).show();
		        	return;
		        }
		        
		        MainActivity.setSrc(newDevList.toString());
		        MainActivity.setSrcList(postDevList);
		        
		        pager.setCurrentItem(MainConstants.PAGER_DST_INDEX, true);
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
