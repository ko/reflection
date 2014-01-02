package com.relurori.reflection;
import com.relurori.reflection.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class CopyDialogFragment extends DialogFragment {

	private final String TAG = CopyDialogFragment.class.getSimpleName();
	
	View mView = null;
	
	public CopyDialogFragment() {}
	

	public static CopyDialogFragment newInstance() {
		CopyDialogFragment frag = new CopyDialogFragment();
		return frag;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

		int style;
		int theme;
		
		style = DialogFragment.STYLE_NORMAL;
		theme = android.R.style.Theme_Holo_Dialog;
		
		setStyle(style,theme);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_dialog_copy, container, false);
		
		return mView;
	}
	
	@Override
	public ProgressDialog onCreateDialog(Bundle savedInstanceState) {
		
		ProgressDialog pd = new ProgressDialog(getActivity());

		pd.setTitle("title=" + "j");
		
		pd.show();
		
		return pd;
	}
	
	public void dismissThis() {
		getDialog().dismiss();
	}
	
	public void updateProgress(double d) {
		ProgressBar p = (ProgressBar)mView.findViewById(R.id.progressBar1);
		p.setProgress((int) d);
	}
}
