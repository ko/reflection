package com.relurori.reflection;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class MainActivity extends FragmentActivity {

	private final String TAG = MainActivity.class.getSimpleName();

	private PagerAdapter mPagerAdapter = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main_fragments);

		pre();
	}

	private void pre() {
		Log.d(TAG,"0");
		List<Fragment> fragments = new Vector<Fragment>();
		try {
			Log.d(TAG,"1");
			fragments.add(Fragment.instantiate(this, InstructionFragment.class.getName()));
			Log.d(TAG,"2");
			fragments.add(Fragment.instantiate(this, SelectSourceFragment.class.getName()));
			fragments.add(Fragment.instantiate(this, SelectDestinationFragment.class.getName()));
			fragments.add(Fragment.instantiate(this, SyncFragment.class.getName()));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		Log.d(TAG,"3");
		mPagerAdapter = new MainPagerAdapter(super.getSupportFragmentManager(), fragments);
		Log.d(TAG,"4");
		ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
		Log.d(TAG,"5");
		pager.setAdapter(mPagerAdapter);
		Log.d(TAG,"6");
	}

}
