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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SelectDestinationFragment extends Fragment {

	private String TAG = SelectDestinationFragment.class.getSimpleName();
	
	private View mView = null;
	private MyViewPager pager = null;
	private ImageButton mButton;
	List<ProcMountsLine> postDevLines = new ArrayList<ProcMountsLine>();
	List<ProcMountsLine> preDevLines = new ArrayList<ProcMountsLine>();
	List<ProcMountsLine> newDevLines = new ArrayList<ProcMountsLine>();
	
	public SelectDestinationFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		mView = inflater.inflate(R.layout.fragment_destination_device, container, false);
		
		Log.d(TAG,"1");
		pre();
		
		return mView;
	}
	

	private void pre() {
		
		pager = (MyViewPager) getActivity().findViewById(R.id.viewpager);
		
		mButton = (ImageButton)mView.findViewById(R.id.btnScan2);
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				preDevLines = MainActivity.getSrcLines();
				
		        try {
					postDevLines = MountPoint.getProcMountsLines();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        for (int i = 0; i < postDevLines.size(); i++) {
		        	// TODO compare a larger set of info (devnode, mountpoint, etc.)
		        	// for a better comparison?
		        	if (preDevLines.get(i).getMountPoint().equals(postDevLines.get(i).getMountPoint()) == false) {
		        		newDevLines.add(postDevLines.get(i));
		        	}
		        }

		        if (newDevLines.isEmpty()) {
		        	Log.d(TAG,"empty dst");
		        	Toast.makeText(getActivity().getBaseContext(), "Device not found.", Toast.LENGTH_LONG).show();
		        	return;
		        } 
		        
		        MainActivity.setDstLines(newDevLines);
		        
		        pager.setCurrentItem(MainConstants.PAGER_SYNC_INDEX, true);
			}
		});
	}
	
}
