package com.relurori.reflection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SyncDoneFragment extends Fragment {

	public static final String TAG = SyncDoneFragment.class.getSimpleName();
	
	private View mView = null;
	
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
		// TODO Auto-generated method stub
		
	}
}
