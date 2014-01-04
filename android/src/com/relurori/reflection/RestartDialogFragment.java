package com.relurori.reflection;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class RestartDialogFragment extends DialogFragment {

	public static String TAG = RestartDialogFragment.class.getSimpleName();
	
	public static RestartDialogFragment newInstance() {
		RestartDialogFragment frag = new RestartDialogFragment();
		return frag;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		return new AlertDialog.Builder(getActivity())
							.setIcon(R.drawable.ic_launcher)
							.setTitle("Instructions")
							.setMessage("Remove the source and/or destination device(s) and try again.")
							.setNeutralButton("OK", 
									new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											return;
										}
									})
							.create();
	}
	
}
