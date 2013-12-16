package com.relurori.reflection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InstructionFragment extends Fragment {

	private String TAG = InstructionFragment.class.getSimpleName();
	
	private View mView = null;
	
	public InstructionFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		
		Log.d(TAG,"0");
		mView = inflater.inflate(R.layout.fragment_init, container, false);
		
		Log.d(TAG,"1");
		
		return mView;
	}
}
