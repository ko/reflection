package com.relurori.reflection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SyncDoneFragment extends Fragment {

	public static final String TAG = SyncDoneFragment.class.getSimpleName();
	
	private static View mView = null;
	
	private ViewPager pager = null;
	
	public static SyncDoneFragment newInstance() {
		SyncDoneFragment f = new SyncDoneFragment();
		return f;
	}
	
	public SyncDoneFragment() {}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		mView = inflater.inflate(R.layout.fragment_done, container, false);
		
		pre();
		
		return mView;
	}

	private void pre() {

		pager = (MyViewPager) getActivity().findViewById(R.id.viewpager);
		
		Button b = (Button)mView.findViewById(R.id.bResync);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				pager.setCurrentItem(MainConstants.PAGER_SYNC_INDEX);
			}
		});
		
		b = (Button)mView.findViewById(R.id.bRetry);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				MainActivity.resetSrcDst();
				pager.setCurrentItem(MainConstants.PAGER_SRC_INDEX);
			}
		});
	}

	public static void setCopied(int i, int j) {
		// TODO Auto-generated method stub
		TextView tv = (TextView)mView.findViewById(R.id.textView1);
		
		tv.setText(i + " of " + j + " copied");
	}
	
}
