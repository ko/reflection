package com.relurori.reflection;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InstructionFragment extends Fragment {

	private View mView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		
		mView = inflater.inflate(R.layout.fragment_init, container, false);
		
		return mView;
	}
}
