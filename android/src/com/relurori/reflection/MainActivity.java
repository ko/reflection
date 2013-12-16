package com.relurori.reflection;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private final String TAG = MainActivity.class.getSimpleName();
	
	ListView deviceListView;
	ArrayAdapter mArrayAdapter;
	Button button;
	
	private PagerAdapter mPagerAdapter = null;
	
	/*
	private Fragment mTempFragment = null;
	private InstructionFragment mInstructionFragment = new InstructionFragment();
	private SelectSourceFragment mSourceFragment = new SelectSourceFragment();
	private SelectDestinationFragment mDestinationFragment = new SelectDestinationFragment();
	private SyncFragment mSyncFragment = new SyncFragment();
	*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragments);

		pre();
	}

	private void fragmentsetup() {

	}

	private void pre() {
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, InstructionFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, SelectSourceFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, SelectDestinationFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, SyncFragment.class.getName()));
		
		mPagerAdapter = new MainPagerAdapter(super.getSupportFragmentManager(), fragments);
		
		ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
		pager.setAdapter(mPagerAdapter);
	}

}
