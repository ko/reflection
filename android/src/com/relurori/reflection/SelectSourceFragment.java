package com.relurori.reflection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.relurori.reflection.usbdevice.MountPoint;
import com.relurori.reflection.usbdevice.ProcMountsLine;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectSourceFragment extends Fragment {

	private final String TAG = SelectSourceFragment.class.getSimpleName();
	
	private View mView = null;
	private MyViewPager pager = null;
	private ImageButton mButton;
	
	List<ProcMountsLine> preDevLines = new ArrayList<ProcMountsLine>();
	List<ProcMountsLine> postDevLines = new ArrayList<ProcMountsLine>();
	List<ProcMountsLine> newDevLines = new ArrayList<ProcMountsLine>();
	
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
		// TODO save this somewhere
		getSetPreDevLines();
		
		Log.d(TAG,"3");
		
		return mView;
	}
	
	private void getSetPreDevLines() {
		
		try {
			preDevLines = MountPoint.getProcMountsLines();
			MainActivity.setPreLines(preDevLines);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void pre() {
		
		pager = (MyViewPager) getActivity().findViewById(R.id.viewpager);
		
		pre_debug();
		
		mButton = (ImageButton)mView.findViewById(R.id.btnScan);
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
		        try {
					postDevLines = MountPoint.getProcMountsLines();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        for (int i = 0; i < postDevLines.size(); i++) {
		        	// TODO compare a larger set of info (devnode, mountpoint, etc.)
		        	// for a better comparison?
		        	Log.d(TAG,"ISS22: preDevLines.size()=" + preDevLines.size());
		        	Log.d(TAG,"ISS22: postDevLines.size()=" + postDevLines.size());
		        	if (preDevLines.get(i).getMountPoint().equals(postDevLines.get(i).getMountPoint()) == false) {
		        		newDevLines.add(postDevLines.get(i));
		        		
		        		Log.d(TAG,"DNE=" + postDevLines.get(i));
		        		Log.d(TAG,"preMP =" + preDevLines.get(i).getMountPoint());
		        		Log.d(TAG,"postMP=" + postDevLines.get(i).getMountPoint());
		        		
		        	}
		        }
		        
		        if (newDevLines.isEmpty()) {
		        	Log.d(TAG,"empty source");
		        	Toast.makeText(getActivity().getBaseContext(), "Device not found.", Toast.LENGTH_LONG).show();
		        	return;
		        }
		        
		        MainActivity.setSrcLines(newDevLines);
		        
		        pager.setCurrentItem(MainConstants.PAGER_DST_INDEX, true);
			}
		});
	}
	
	private void pre_debug() {
		Button b = (Button)mView.findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				

				/********* TESTING **********/
				parseMountPoints();
				
				MainActivity.setSrc(Environment.getExternalStorageDirectory().toURI() + "Download/");
				MainActivity.setDst(Environment.getExternalStorageDirectory().toURI() + "zDebug/");
				pager.setCurrentItem(MainConstants.PAGER_SYNC_INDEX, true);
			}
		});
		
		

		/***** DEBUG *****/
		//b.setVisibility(View.GONE);
	}

	/********* TESTING **********/
	protected void parseMountPoints() {
		try {
			List<String> lines = MountPoint.catProcMounts();
			List<ProcMountsLine> parsed = MountPoint.parseProcMounts(lines);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
