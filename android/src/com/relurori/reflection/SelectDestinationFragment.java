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

	private String TAG = SelectDestinationFragment.class.getCanonicalName();
	
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
				
				/** preLines + srcLine **/
				preDevLines = MainActivity.getPreLines();
				preDevLines.add(MainActivity.getSrcLines().get(0));
				
		        try {
					postDevLines = MountPoint.getProcMountsLines();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        newDevLines.clear();
		        for (ProcMountsLine pml : postDevLines) {
		        	newDevLines.add(new ProcMountsLine(pml.getDevNode(),pml.getMountPoint()));
		        }
		        
		        if (newDevLines.size() <= preDevLines.size()) {
		        	// should never happen
		        	Log.d(TAG,"size post is <= pre");
		        	return;
		        }
		        
		        for (int i = 0; i < newDevLines.size(); i++) {
		        	for (int j = 0; j < preDevLines.size(); j++) {

			        	// TODO compare a larger set of info (devnode, mountpoint, etc.)
			        	// for a better comparison?
		        		
			        	if (preDevLines.get(j).getMountPoint().equals(newDevLines.get(i).getMountPoint()) == true) {
			        		newDevLines.remove(i);
			        		continue;
			        	}
		        	}
		        }
		        
		        

		        if (newDevLines.isEmpty()) {
		        	Log.d(TAG,"empty dst");
		        	Toast.makeText(getActivity().getBaseContext(), "Device not found.", Toast.LENGTH_LONG).show();
		        	return;
		        } 
		        
		        if (newDevLines.size() != 1) {
		        	Log.d(TAG,"dst not 1");
		        	Toast.makeText(getActivity(), "Destination finder is confused by " + newDevLines.size() + " choices... Please reset.", Toast.LENGTH_LONG).show();
		        	return;
		        }
		        
		        MainActivity.setDstLines(newDevLines);
		        
		        pager.setCurrentItem(MainConstants.PAGER_SYNC_INDEX, true);
			}
		});
	}
	
}
